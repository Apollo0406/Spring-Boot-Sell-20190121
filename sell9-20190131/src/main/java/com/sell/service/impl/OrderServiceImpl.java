package com.sell.service.impl;

import com.sell.convertor.OrderMaster2OrderDTOConverter;
import com.sell.dto.CarDTO;
import com.sell.dto.OrderDTO;
import com.sell.entity.OrderDetail;
import com.sell.entity.OrderMaster;
import com.sell.entity.ProductInfo;
import com.sell.enums.OrderStatusEnum;
import com.sell.enums.PayStatusEnum;
import com.sell.enums.ResultEnum;
import com.sell.exception.SellException;
import com.sell.repository.OrderDetailRepository;
import com.sell.repository.OrderMasterRepository;
import com.sell.service.OrderService;
import com.sell.service.ProductInfoService;
import com.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

/* 扣库存的未优化代码：
    List<CarDTO> carDTOList = new ArrayList<>();
*/

    //创建订单：先计算出订单的相关信息，再存入数据库，并且减去相应数量的库存
    @Override
    @Transactional   //加上事务的注解方便抛出异常就进行事务回滚
    public OrderDTO create(OrderDTO orderDTO) {

        BigDecimal orderAmount = new BigDecimal(0);

        String orderId = KeyUtil.genUniqueKey();

        //1.查询商品：数量、单价
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
           //如果没有这条信息
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //2.计算总价=单价*数量
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //3.写入数据库--详情入Detail数据表(OrderMaster OrderDetail)
            orderDetail.setOrderId(orderId);  //这里的两个ID都是使用随机生成的数
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            BeanUtils.copyProperties(productInfo,orderDetail); //把productInfo的属性全部拷贝到orderDetail里面
            orderDetailRepository.save(orderDetail);

           /* 口库存的预备代码（未优化）：
            CarDTO carDTO = new CarDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
            carDTOList.add(carDTO);*/
        }

        //写入数据库---主表入Master表
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        //4.下单成功就要扣库存
        List<CarDTO> carDTOList = orderDTO
                .getOrderDetailList()
                .stream()
                .map(e -> new CarDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.decreateStock(carDTOList);
        return  orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {

        OrderMaster orderMaster = orderMasterRepository.getOne(orderId);
        //加入查不到该订单，则抛出“订单不存在”异常
        if(orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //加入存在该订单，则查出该订单中所有的商品；返回值为List<OrderDetail>
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        //如果该订单中没有商品，则抛出“订单详情不存在”异常
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        //当订单存在，且订单中有商品信息时，进行对象转换返回结果
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
/*        orderDTO.setBuyerOpenid(orderMaster.getBuyerOpenid());
        orderDTO.setOrderId(orderMaster.getOrderId());
        orderDTO.setBuyerName(orderMaster.getBuyerName());
        orderDTO.setBuyerPhone(orderMaster.getBuyerPhone());
        orderDTO.setOrderAmount(orderMaster.getOrderAmount());
        orderDTO.setBuyerAddress(orderMaster.getBuyerAddress());
        orderDTO.setOrderDetailList(orderDetailList);*/
        return orderDTO;

        //问题1：
        //写到这里有遇见了一个no session的报错。
        //这个错是因为jpa使用了懒加载，当我们此次访问数据库的时候，连接已被关闭，
        //所以要设置spring.jpa.open-in-view=true 但是这个我已经在配置文件中设置过了
        //所以不是这个的问题，当我加了事务之后，解决问题
        //问题2：
        //还遇到了一个 could not 将 source 的值赋给 target 说是因为有字段是基本类型的，
        // 但把null值赋给这个基本字段时就会有错，但后来其实也不是字段类型的问题，
        // 很奇怪的被事务注解解决了问题
    }


    //查询订单的列表
    //订单列表不需要返回订单详情，我们拿到结果之后，需要将结果专程OrderDTO类型
    //为了方便，建了一个转换器
    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.contert(orderMasterPage.getContent());

        return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {

        //当想把订单信息修改掉，存在数据库中时，我们需要调用dao的save(),
        //但是参数不为OrderDTO，所以需要将其转为OrderMaster
        OrderMaster orderMaster = new OrderMaster();
        //BeanUtils.copyProperties(orderDTO,orderMaster);

        //首先要判断订单状态，只有特定的订单状态才能被取消
        //0:新订单  1：已完结 2：已取消
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()) ){
            log.error("[取消订单]订单状态不正确。orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态(将订单状态改成“已取消”)
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("[取消订单]更新失败。orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //返还库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[取消订单]订单中无商品详情，orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CarDTO> carDTOList = orderDTO.getOrderDetailList()
                                    .stream()
                                    .map(e -> new CarDTO(e.getProductId(),e.getProductQuantity()))
                                    .collect(Collectors.toList());
        productInfoService.increateStock(carDTOList);

        //如果已支付，需要退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS)){
            //TODO
        }
        return orderDTO;
    }

    //完结订单
    @Override
    public OrderDTO finish(OrderDTO orderDTO) {

        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[完结订单]订单信息不正确，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("[完结订单]更新失败.orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {

        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[订单支付成功]订单支付不正确，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
        }

        //判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("[订单支付不正确]订单支付状态不正确，orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //修改支付状态 将未支付改成已支付
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("[订单支付完成]更新失败.orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }
}

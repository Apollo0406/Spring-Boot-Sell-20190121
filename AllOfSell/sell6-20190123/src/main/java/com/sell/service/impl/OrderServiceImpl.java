package com.sell.service.impl;

import com.sell.dto.CarDTO;
import com.sell.dto.OrderDTO;
import com.sell.entity.OrderDetail;
import com.sell.entity.OrderMaster;
import com.sell.entity.ProductInfo;
import com.sell.enums.ResultEnum;
import com.sell.exception.SellException;
import com.sell.repository.OrderDetailRepository;
import com.sell.repository.OrderMasterRepository;
import com.sell.service.OrderService;
import com.sell.service.ProductInfoService;
import com.sell.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
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
            orderAmount = orderDetail.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //3.写入数据库--详情入Detail数据表(OrderMaster OrderDetail)
            orderDetail.setDetailId(orderId);
            orderDetail.setDetailId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepository.save(orderDetail);

           /* 口库存的预备代码（未优化）：
            CarDTO carDTO = new CarDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
            carDTOList.add(carDTO);*/
        }

        //写入数据库---主表入Master表
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        BeanUtils.copyProperties(orderDTO,orderMaster);
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
        return null;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        return null;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }
}

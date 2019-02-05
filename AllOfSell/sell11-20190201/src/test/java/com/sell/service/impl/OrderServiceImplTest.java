package com.sell.service.impl;

import com.sell.dto.OrderDTO;
import com.sell.entity.OrderDetail;
import com.sell.enums.OrderStatusEnum;
import com.sell.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID = "139";

    private final String ORDER_ID = "1548768889631889656";

    @Test
    public void create() throws Exception{

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("唐yt");
        orderDTO.setBuyerAddress("虢镇");
        orderDTO.setBuyerPhone("13985655587");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();

        //购买的商品“芒果冰”，而且把仅有的50件库存都给买光了
        //当库存已经为0的时候，再次购买则会报出“商品库存不正确”异常
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("mang");
        o1.setProductQuantity(1);


        OrderDetail o2 = new OrderDetail();
        o2.setProductId("pi");
        o2.setProductQuantity(1);

        orderDetailList.add(o1);
        orderDetailList.add(o2);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        Assert.assertNotEquals(null,result);
        /*log.info("创建订单：result{}",result);*/
    }

    @Test
    @Transactional
    public void findOne() throws Exception{
        OrderDTO result = orderService.findOne(ORDER_ID);
        log.info("查询单个订单：result = {}",result );
        Assert.assertEquals(ORDER_ID,result.getOrderId());
    }

    @Test
    public void findList() {
        PageRequest request = new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID,request);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    @Test
    @Transactional
    @Rollback(false)
    public void cancel() {
        OrderDTO orderDTO1 = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.cancel(orderDTO1);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());
    }

    @Test
    @Transactional
    @Rollback(false)
    public void finish() {
        OrderDTO orderDTO1 = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.finish(orderDTO1);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
    }

    @Test
    @Transactional
    @Rollback(false)
    public void paid() {
        OrderDTO orderDTO1 = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.paid(orderDTO1);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
    }

    //卖家端
    @Test
    public void list(){
        PageRequest request = new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(request);
        //Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
        Assert.assertTrue("查询所有的订单列表",orderDTOPage.getTotalElements() > 0);
    }
}
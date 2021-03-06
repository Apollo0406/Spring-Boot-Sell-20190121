package com.sell.repository;

import com.sell.entity.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    //这个测试的createTime记录的是第一次创建时间
    @Test
    public void save(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId("444");
        orderDetail.setDetailId("1");
        orderDetail.setProductId("6");
        orderDetail.setProductName("蛋挞");
        orderDetail.setProductPrice(new BigDecimal(2.5));
        orderDetail.setProductIcon("http:y.jpg");
        orderDetail.setProductQuantity(10);

        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotEquals(null,result);
    }
    @Test
    @Transactional
    public void findByOrderId() {
        List<OrderDetail> result = repository.findByOrderId("444");
        Assert.assertNotEquals(0,result.size());
    }
}


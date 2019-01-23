package com.sell.repository;

import com.sell.entity.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    private final String OPENID = "110";

    @Test
    public void save(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("222");
        orderMaster.setBuyerName("Apollo");
        orderMaster.setBuyerPhone("12333335556");
        orderMaster.setBuyerAddress("山西");
        orderMaster.setBuyerOpenid("110");
        orderMaster.setOrderAmount(new BigDecimal(2.5));
        OrderMaster result = repository.save(orderMaster);

        Assert.assertNotEquals(null,result);
    }

    @Test
    public void findByBuyerOpenid() throws Exception{

        PageRequest request = new PageRequest(0,1);
        Page<OrderMaster> result = repository.findByBuyerOpenid(OPENID,request);
        Assert.assertNotEquals(null,result);
    }
}
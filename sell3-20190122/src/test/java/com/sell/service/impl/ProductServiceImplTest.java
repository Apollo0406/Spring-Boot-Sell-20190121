package com.sell.service.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sell.entity.ProductInfo;
import com.sell.enums.ProductStatusEnum;
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
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findAll() throws Exception{
        PageRequest request = new PageRequest(0,2);
        Page<ProductInfo> productInfos = productService.findAll(request);
        //System.out.println(productInfos.getTotalElements());
        Assert.assertNotEquals(null,productInfos);
    }

    @Test
    public void findOne() throws Exception{
        ProductInfo productInfo = productService.findOne("12345");
        Assert.assertNotEquals(null,productInfo);
    }

    @Test
    public void findUpAll() throws Exception{
        List<ProductInfo> list = productService.findUpAll();
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void save() throws Exception{
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("皮皮虾");
        productInfo.setProductPrice(new BigDecimal(32.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("超好吃的虾");
        productInfo.setProductIcon("http://xxx.jpg");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(2);

        ProductInfo result = productService.save(productInfo);
        Assert.assertNotEquals(null,result);
    }
}
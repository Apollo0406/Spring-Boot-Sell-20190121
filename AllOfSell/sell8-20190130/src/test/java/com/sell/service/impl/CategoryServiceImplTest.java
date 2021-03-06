package com.sell.service.impl;

import com.sell.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
//测试类记得先写 @RunWith(SpringRunner.class) 和 @SpringBootTest
//还要记得要把serviceImpl注入进来

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;
    @Test
    public void findAll() throws Exception{
       List<ProductCategory> list = categoryService.findAll();
       Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void findOne() throws Exception{
        ProductCategory productCategory = categoryService.findOne(2);
        Assert.assertEquals(new Integer(2),productCategory.getCategoryId());
    }

    @Test
    public void findByCategoryTypeIn() throws Exception{
        List<ProductCategory> list = categoryService.findByCategoryTypeIn(Arrays.asList(2,3,4));
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void save() throws Exception {
        ProductCategory productCategory = new ProductCategory("男生专享",4);
        ProductCategory result = categoryService.save(productCategory);
        Assert.assertNotEquals(null,result);
    }
}
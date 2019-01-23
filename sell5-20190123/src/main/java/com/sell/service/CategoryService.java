package com.sell.service;

import com.sell.entity.ProductCategory;
import org.springframework.stereotype.Service;

import java.util.List;

//Service就是用来写清楚提供的所有服务 也就是接口
//一般来说就是 增删改查

public interface CategoryService {

     List<ProductCategory> findAll();

     ProductCategory findOne(Integer categoryId);

     List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryList);

     ProductCategory save(ProductCategory productCategory);

    // boolean delete(Integer categoryId);


}

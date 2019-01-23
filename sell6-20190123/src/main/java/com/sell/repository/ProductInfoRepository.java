package com.sell.repository;

import com.sell.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {

    //查询上架的商品
    List<ProductInfo> findByProductStatus(Integer productStatus);
}

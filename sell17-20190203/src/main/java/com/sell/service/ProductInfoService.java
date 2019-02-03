package com.sell.service;

import com.sell.dto.CarDTO;
import com.sell.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {

    //查询所有
    Page<ProductInfo> findAll(Pageable pageable);

    //查找一个
    ProductInfo findOne(String productId);

    //按照状态查找
    List<ProductInfo> findUpAll();

    //添加
    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increateStock(List<CarDTO> carDTOList);

    //减库存
    void decreateStock(List<CarDTO> carDTOList);

    //上架
    ProductInfo onSale(String productId);

    //下架
    ProductInfo offSale(String productId);

}

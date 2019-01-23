package com.sell.service.impl;

import com.sell.dto.CarDTO;
import com.sell.entity.ProductInfo;
import com.sell.enums.ProductStatusEnum;
import com.sell.enums.ResultEnum;
import com.sell.exception.SellException;
import com.sell.repository.ProductInfoRepository;
import com.sell.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo findOne(String productId) {
        return repository.getOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    //加库存
    @Override
    public void increateStock(List<CarDTO> carDTOList) {

    }

    //减库存
    @Override
    @Transactional
    public void decreateStock(List<CarDTO> carDTOList) {
       /* 遍历购物车，先查询:无商品：报错；有商品：判断所需的数量当前的数据库总库存能否满足：
        不满足：报错；能满足：扣库存、将当前订单存入数据库*/

        for(CarDTO carDTO : carDTOList){
            ProductInfo productInfo = repository.getOne(carDTO.getProductId());
            if(productInfo == null){  //如果商品不存在 就爆出异常
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //计算出剩余库存：原库存 - 订单数量
            Integer result = productInfo.getProductStock() - carDTO.getProductQuantity();
            //注意坤村不足异常的爆出
            if(result < 0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            //当库存足够的时候，就进行扣库存操作，并且将订单写入数据库
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }
}

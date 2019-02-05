package com.sell.dto;

import lombok.Data;

//购物车
@Data
public class CarDTO {

    //商品id
    private String productId;

    //数量
    private Integer productQuantity;

    public CarDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}

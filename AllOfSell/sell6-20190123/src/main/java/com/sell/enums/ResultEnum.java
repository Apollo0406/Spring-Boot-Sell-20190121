package com.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    PRODUCT_NOT_EXIST(10,"商品不存在"),

    PRODUCT_STOCK_ERROR(11,"商品库存不正确"),
    ;

    private Integer code;  //异常码

    private String message;  //异常信息

    ResultEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    }
}

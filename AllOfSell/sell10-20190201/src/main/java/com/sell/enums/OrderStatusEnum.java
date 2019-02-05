package com.sell.enums;

import lombok.Getter;
//建一个枚举去存放所有状态码和所对应的状态信息
@Getter
public enum OrderStatusEnum {
    NEW(0,"新订单"),
    FINISHED(1,"完结"),
    CANCEL(2,"已取消"),
        ;

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    }
}

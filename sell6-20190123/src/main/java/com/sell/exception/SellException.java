package com.sell.exception;

import com.sell.enums.ResultEnum;
//专门用来抛出异常的异常类
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}

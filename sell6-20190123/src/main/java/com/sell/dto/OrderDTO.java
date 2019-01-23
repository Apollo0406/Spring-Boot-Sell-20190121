package com.sell.dto;

import com.sell.entity.OrderDetail;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

//DTO在数据传输中专门用的对象
@Getter
public class OrderDTO {

    private String detailId;

    //订单id
    private String orderId;

    //商品id
    private String  productId;

    //商品名称
    private String productName;

    //商品单价
    private BigDecimal productPrice;

    //商品小图
    private String productIcon;

    //商品数量
    private Integer productQuantity;

    private List<OrderDetail> orderDetailList;
}

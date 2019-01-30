package com.sell.dto;

import com.sell.entity.OrderDetail;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

//DTO在数据传输中专门用的对象
//DTO --data transfer object
@Data
public class OrderDTO {

   //订单id
    private String orderId;

    //买家名字
    private String buyerName;

    //买家手机号
    private String buyerPhone;

    //买家地址
    private String buyerAddress;

    //买家微信OpenId
    private String buyerOpenid;

    //订单总金额
    private BigDecimal orderAmount;

    //订单状态
    private Integer orderStatus;

    //支付状态
    private Integer payStatus;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

    //订单详情列表
    List<OrderDetail> orderDetailList;


    /* private String detailId;

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

    //订单配送地址
    private String buyerAddress;

    private List<OrderDetail> orderDetailList;*/
}

package com.sell.entity;

import com.sell.enums.OrderStatusEnum;
import com.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

//订单主表
@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    //订单号
    @Id
    private String orderId;

    //买家姓名
    private String buyerName;

    //买家手机号
    private String buyerPhone;

    //买家地址
    private String buyerAddress;

    //买家微信Openid
    private String buyerOpenid;

    //订单总金额
    private BigDecimal orderAmount;

    //订单状态
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    //支付状态
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;
/*
    //OrderDetail列表
    @Transient
    private List<OrderDetail> orderDetailList;*/
}

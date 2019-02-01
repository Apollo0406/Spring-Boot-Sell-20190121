package com.sell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sell.entity.OrderDetail;
import com.sell.enums.OrderStatusEnum;
import com.sell.enums.PayStatusEnum;
import com.sell.utils.EnumUtil;
import com.sell.utils.serializer.Date2LongSerializer;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

//DTO在数据传输中专门用的对象
//DTO --data transfer object
@Data
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    //更新时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    //订单详情列表
    List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
       return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
     return EnumUtil.getByCode(payStatus,PayStatusEnum.class);
    }
}

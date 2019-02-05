package com.sell.convertor;

import com.sell.dto.OrderDTO;
import com.sell.form.OrderForm;

public class OrderForm2OrderDTOConverter {

    private static OrderDTO convert(OrderForm orderForm){
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

       return null;
    }
}

package com.sell.convertor;

import com.sell.dto.OrderDTO;
import com.sell.entity.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMaster2OrderDTOConverter {

    //将单个OrderMaster对象转为OrderDTO
    public static OrderDTO convert(OrderMaster orderMaster){

        OrderDTO orderDTO = new OrderDTO();

        BeanUtils.copyProperties(orderMaster,orderDTO);

        return orderDTO;
    }

    //将OrderMasterList转为OrderDTOList
    public static List<OrderDTO> contert(List<OrderMaster> orderMasterList){

        return orderMasterList.stream()
                .map(e->convert(e))
                .collect(Collectors.toList());

    }
}

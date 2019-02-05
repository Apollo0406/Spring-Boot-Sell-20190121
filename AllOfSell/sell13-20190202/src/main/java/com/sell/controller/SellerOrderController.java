package com.sell.controller;

import com.sell.dto.OrderDTO;
import com.sell.enums.ResultEnum;
import com.sell.exception.SellException;
import com.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/seller/order")
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue ="10" ) Integer size,
                             Map<String,Object> map){
        PageRequest request = new PageRequest(page-1,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(request);
        map.put("orderDTOPage",orderDTOPage);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("order/list",map);

    }

    @GetMapping("/cancel")
    public ModelAndView  cancel(@RequestParam(value = "orderId") String orderId,
                                Map<String,Object> map){
       try{
           OrderDTO orderDTO = orderService.findOne(orderId);

       }catch (SellException e){
               log.error("[卖家端取消订单]查询不到该订单");

               map.put("msg",e.getMessage());
               map.put("url","/sell/seller/order/list");
               return new ModelAndView("common/error",map);
       }

        //orderService.cancel(orderDTO);
        return new ModelAndView("order/cancel");
    }

}

package com.example.Ecommerce.controllers;

import com.example.Ecommerce.DTO.RequestDto.OrderRequestDto;
import com.example.Ecommerce.DTO.ResponseDto.OrderResponseDto;
import com.example.Ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @PostMapping("/place")
    public OrderResponseDto placeOrderDirectly(@RequestBody OrderRequestDto orderRequestDto) throws Exception {
        return orderService.placeOrder(orderRequestDto);
    }
    @PutMapping("/remove")
    public OrderResponseDto removeOrder(@RequestParam("customerId")int customerId,@RequestParam("OrderId")int orderId) throws Exception{
        return orderService.deleteOrder(customerId, orderId);
    }
}

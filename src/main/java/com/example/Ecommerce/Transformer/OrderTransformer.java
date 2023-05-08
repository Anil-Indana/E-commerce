package com.example.Ecommerce.Transformer;

import com.example.Ecommerce.DTO.ResponseDto.OrderResponseDto;
import com.example.Ecommerce.entities.Ordered;

public class OrderTransformer {
    public static OrderResponseDto OrderToResponse(Ordered order){
        return OrderResponseDto.builder()
                .orderNo(order.getOrderNo())
                .orderDate(order.getOrderDate())
                .cardUsed(order.getCardUsed())
                .customerName(order.getCustomer().getName())
                .totalValue(order.getTotalPrice())
                .build();
    }
}

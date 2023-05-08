package com.example.Ecommerce.Transformer;

import com.example.Ecommerce.DTO.ResponseDto.CartResponseDto;
import com.example.Ecommerce.entities.Cart;

public class CartTransformer {
    public static CartResponseDto cartToResponse(Cart cart){
        return CartResponseDto.builder()
                .cartTotal(cart.getTotalAmount())
                .customerName(cart.getCustomer().getName())
                .noOfItems(cart.getNoOfItems())
                .build();
    }
}

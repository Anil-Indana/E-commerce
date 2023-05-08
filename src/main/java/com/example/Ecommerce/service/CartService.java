package com.example.Ecommerce.service;

import com.example.Ecommerce.DTO.RequestDto.CheckOutCartRequestDto;
import com.example.Ecommerce.DTO.ResponseDto.CartResponseDto;
import com.example.Ecommerce.DTO.ResponseDto.ItemResponseDto;
import com.example.Ecommerce.DTO.ResponseDto.OrderResponseDto;
import com.example.Ecommerce.entities.Item;
import com.example.Ecommerce.exceptions.InvalidCustomerIdException;

import java.util.List;

public interface CartService {
    public CartResponseDto saveToCart(Integer customerId, Item item);
    public OrderResponseDto checkOutCart(CheckOutCartRequestDto checkOutCartRequestDto) throws Exception;
    public CartResponseDto removeFromCart(int customerId,Item item) throws Exception;
    public List<ItemResponseDto> viewAllItemsInCart(int customerId) throws InvalidCustomerIdException;
}

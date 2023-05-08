package com.example.Ecommerce.service;

import com.example.Ecommerce.DTO.RequestDto.ItemRequestDto;
import com.example.Ecommerce.DTO.ResponseDto.ItemResponseDto;
import com.example.Ecommerce.entities.Item;
import com.example.Ecommerce.exceptions.InvalidCustomerIdException;
import com.example.Ecommerce.exceptions.InvalidProductIdException;

public interface ItemService {
    public Item addItem(ItemRequestDto itemRequestDto) throws InvalidCustomerIdException, InvalidProductIdException;
    public Item removeItem(int id,int customerId) throws Exception;
}

package com.example.Ecommerce.service;

import com.example.Ecommerce.DTO.RequestDto.CardRequestDto;
import com.example.Ecommerce.DTO.ResponseDto.CardResponseDto;
import com.example.Ecommerce.enums.CardType;
import com.example.Ecommerce.exceptions.InvalidCustomerIdException;

import java.util.List;

public interface CardService {
    public CardResponseDto addCard(CardRequestDto cardRequestDto) throws InvalidCustomerIdException;
    public List<CardResponseDto> getByType(CardType cardType);
}

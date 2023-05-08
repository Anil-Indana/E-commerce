package com.example.Ecommerce.Transformer;

import com.example.Ecommerce.DTO.RequestDto.CardRequestDto;
import com.example.Ecommerce.DTO.ResponseDto.CardResponseDto;
import com.example.Ecommerce.entities.Card;

public class CardTransformer {
    public static Card cardRequestToCard(CardRequestDto cardRequestDto){
        return Card.builder()
                .cardNo(cardRequestDto.getCardNo())
                .cardType(cardRequestDto.getCardType())
                .cvv(cardRequestDto.getCvv())
                .expiryDate(cardRequestDto.getExpiryDate())
                .build();
    }
    public static CardResponseDto CardToResponse(Card card){
        return CardResponseDto.builder()
                .cardNo(card.getCardNo())
                .customerName(card.getCustomer().getName())
                .build();
    }
}

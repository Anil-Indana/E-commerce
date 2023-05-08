package com.example.Ecommerce.controllers;

import com.example.Ecommerce.DTO.RequestDto.CardRequestDto;
import com.example.Ecommerce.DTO.RequestDto.CustomerRequestDto;
import com.example.Ecommerce.DTO.ResponseDto.CardResponseDto;
import com.example.Ecommerce.entities.Card;
import com.example.Ecommerce.enums.CardType;
import com.example.Ecommerce.exceptions.InvalidCustomerIdException;
import com.example.Ecommerce.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {
    @Autowired
    CardService cardService;
    @PostMapping("/add")
    public CardResponseDto addCard(@RequestBody CardRequestDto cardRequestDto) throws InvalidCustomerIdException {
        return cardService.addCard(cardRequestDto);
    }
    @GetMapping("/getByType")
    public List<CardResponseDto> getByType(@RequestParam("cardType")CardType cardType){
        return cardService.getByType(cardType);
    }
}

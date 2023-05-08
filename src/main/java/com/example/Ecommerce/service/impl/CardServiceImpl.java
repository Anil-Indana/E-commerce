package com.example.Ecommerce.service.impl;

import com.example.Ecommerce.DTO.RequestDto.CardRequestDto;
import com.example.Ecommerce.DTO.ResponseDto.CardResponseDto;
import com.example.Ecommerce.Transformer.CardTransformer;
import com.example.Ecommerce.entities.Card;
import com.example.Ecommerce.entities.Customer;
import com.example.Ecommerce.enums.CardType;
import com.example.Ecommerce.exceptions.InvalidCustomerIdException;
import com.example.Ecommerce.repositories.CardRepository;
import com.example.Ecommerce.repositories.CustomerRepository;
import com.example.Ecommerce.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CardRepository cardRepository;
    @Override
    public CardResponseDto addCard(CardRequestDto cardRequestDto) throws InvalidCustomerIdException {
         // checking if customer with given id exists or not
        Customer customer = customerRepository.findByMobNo(cardRequestDto.getMobNo());
        if(customer == null){
            throw new InvalidCustomerIdException("Customer Doesn't exist");
        }
        Card card = CardTransformer.cardRequestToCard(cardRequestDto);
        card.setCustomer(customer);
        customer.getCards().add(card);
        customerRepository.save(customer); // saves customer and card

        return CardTransformer.CardToResponse(card);
    }

    @Override
    public List<CardResponseDto> getByType(CardType cardType) {
        List<Card> cards = cardRepository.findByCardType(cardType);
        List<CardResponseDto> list = new ArrayList<>();
        for(Card card : cards){
            list.add(CardTransformer.CardToResponse(card));
        }
        return list;
    }
}

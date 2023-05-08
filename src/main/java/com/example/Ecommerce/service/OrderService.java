package com.example.Ecommerce.service;

import com.example.Ecommerce.DTO.RequestDto.OrderRequestDto;
import com.example.Ecommerce.DTO.ResponseDto.OrderResponseDto;
import com.example.Ecommerce.entities.Card;
import com.example.Ecommerce.entities.Customer;
import com.example.Ecommerce.entities.Ordered;
import com.example.Ecommerce.exceptions.InvalidCardException;
import com.example.Ecommerce.exceptions.InvalidCustomerIdException;
import com.example.Ecommerce.exceptions.InvalidProductIdException;

import java.util.List;

public interface OrderService {
    public  Ordered placeOrder(Customer customer, Card card) throws Exception;
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) throws Exception;
    public List<OrderResponseDto> getAllOrders(int customerId) throws InvalidCustomerIdException;
    public OrderResponseDto deleteOrder(int customerId,int orderId) throws Exception;

}

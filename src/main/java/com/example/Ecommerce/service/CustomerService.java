package com.example.Ecommerce.service;

import com.example.Ecommerce.DTO.RequestDto.CustomerRequestDto;
import com.example.Ecommerce.DTO.ResponseDto.CustomerResponseDto;
import com.example.Ecommerce.exceptions.InvalidCustomerIdException;
import com.example.Ecommerce.exceptions.MobNoAlreadyExistsException;

import java.util.List;

public interface CustomerService {
    public CustomerResponseDto addCustomer(CustomerRequestDto customerRequestDto) throws MobNoAlreadyExistsException;
    public String UpdateCustomerEmail(int customerId,String emailId) throws InvalidCustomerIdException;
    public List<CustomerResponseDto> getAllCustomers();
    public List<CustomerResponseDto> getCustomersByAge(int age);
}

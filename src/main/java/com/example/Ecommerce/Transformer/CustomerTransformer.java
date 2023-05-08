package com.example.Ecommerce.Transformer;

import com.example.Ecommerce.DTO.RequestDto.CustomerRequestDto;
import com.example.Ecommerce.DTO.ResponseDto.CustomerResponseDto;
import com.example.Ecommerce.entities.Customer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomerTransformer {
    public static Customer customerRequestToCustomer(CustomerRequestDto customerRequestDto){
        return Customer.builder()
                .age(customerRequestDto.getAge())
                .mobNo(customerRequestDto.getMobNo())
                .name(customerRequestDto.getName())
                .address(customerRequestDto.getAddress())
                .emailId(customerRequestDto.getEmailId())
                .build();
    }
    public static CustomerResponseDto customerToResponse(Customer customer){
        return CustomerResponseDto.builder()
                .emailId(customer.getEmailId())
                .mobNo(customer.getMobNo())
                .name(customer.getName())
                .build();
    }
}

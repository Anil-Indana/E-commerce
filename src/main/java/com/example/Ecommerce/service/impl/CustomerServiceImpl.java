package com.example.Ecommerce.service.impl;

import com.example.Ecommerce.DTO.RequestDto.CustomerRequestDto;
import com.example.Ecommerce.DTO.ResponseDto.CustomerResponseDto;
import com.example.Ecommerce.Transformer.CustomerTransformer;
import com.example.Ecommerce.entities.Cart;
import com.example.Ecommerce.entities.Customer;
import com.example.Ecommerce.exceptions.InvalidCustomerIdException;
import com.example.Ecommerce.exceptions.MobNoAlreadyExistsException;
import com.example.Ecommerce.repositories.CustomerRepository;
import com.example.Ecommerce.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    @Override
    public CustomerResponseDto addCustomer(CustomerRequestDto customerRequestDto) throws MobNoAlreadyExistsException {
        if(customerRepository.findByMobNo(customerRequestDto.getMobNo()) != null){
            throw new MobNoAlreadyExistsException("Customer with same mobile no, exists");
        }
        Customer customer = CustomerTransformer.customerRequestToCustomer(customerRequestDto);
        Cart cart = Cart.builder()
                .customer(customer)
                .noOfItems(0)
                .totalAmount(0)
                .build();
        customer.setCart(cart);
        customerRepository.save(customer); // saves customer and cart;

        return CustomerTransformer.customerToResponse(customer);
    }

    @Override
    public String UpdateCustomerEmail(int customerId,String emailId) throws InvalidCustomerIdException {
        Customer customer = customerRepository.findById(customerId).get();
        if(customer == null) throw new InvalidCustomerIdException("Invalid customer Id");

        customer.setEmailId(emailId);
        customerRepository.save(customer);
        return "Email Updated";
    }

    @Override
    public List<CustomerResponseDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerResponseDto> list = new ArrayList<>();
        for(Customer customer : customers){
            list.add(CustomerTransformer.customerToResponse(customer));
        }
        return list;
    }

    @Override
    public List<CustomerResponseDto> getCustomersByAge(int age) {
        List<Customer> customers = customerRepository.findByAge(age);
        List<CustomerResponseDto> list = new ArrayList<>();
        for(Customer customer : customers){
            list.add(CustomerTransformer.customerToResponse(customer));
        }
        return list;
    }

}

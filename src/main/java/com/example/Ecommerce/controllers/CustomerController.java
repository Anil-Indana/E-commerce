package com.example.Ecommerce.controllers;

import com.example.Ecommerce.DTO.RequestDto.CustomerRequestDto;
import com.example.Ecommerce.DTO.ResponseDto.CustomerResponseDto;
import com.example.Ecommerce.exceptions.InvalidCustomerIdException;
import com.example.Ecommerce.exceptions.MobNoAlreadyExistsException;
import com.example.Ecommerce.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @PostMapping("/add")
    public CustomerResponseDto addCustomer(@RequestBody CustomerRequestDto customerRequestDto) throws MobNoAlreadyExistsException {
        return customerService.addCustomer(customerRequestDto);
    }
    @PutMapping("/updateEmail")
    public String updateEmailId(@RequestParam("id")int id,@RequestParam("emailId")String emailId) throws InvalidCustomerIdException {
        return customerService.UpdateCustomerEmail(id, emailId);
    }
    @GetMapping("/getAll")
    public List<CustomerResponseDto> getAllCustomers(){
        return customerService.getAllCustomers();
    }
    @GetMapping("/getByage")
    public List<CustomerResponseDto> getByAge(@RequestParam("age")int age){
        return customerService.getCustomersByAge(age);
    }
}

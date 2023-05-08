package com.example.Ecommerce.controllers;

import com.example.Ecommerce.DTO.RequestDto.ProductRequestDto;
import com.example.Ecommerce.DTO.RequestDto.SellerRequestDto;
import com.example.Ecommerce.DTO.ResponseDto.ProductResponseDto;
import com.example.Ecommerce.DTO.ResponseDto.SellerResponseDto;
import com.example.Ecommerce.exceptions.EmailAlreadyExistsException;
import com.example.Ecommerce.exceptions.InvalidSellerException;
import com.example.Ecommerce.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {
    @Autowired
    SellerService sellerService;
    @PostMapping("/add")
    public SellerResponseDto addSeller(@RequestBody SellerRequestDto sellerRequestDto) throws EmailAlreadyExistsException {
        return sellerService.addSeller(sellerRequestDto);
    }
    @GetMapping("/getById")
    public SellerResponseDto getById(@RequestParam("sellerId")int sellerId) throws InvalidSellerException{
        return sellerService.getById(sellerId);
    }
    @GetMapping("/getAll")
    public List<SellerResponseDto> getAll(){
        return sellerService.getAll();
    }
}

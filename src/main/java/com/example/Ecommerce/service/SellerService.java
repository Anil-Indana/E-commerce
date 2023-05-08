package com.example.Ecommerce.service;

import com.example.Ecommerce.DTO.RequestDto.SellerRequestDto;
import com.example.Ecommerce.DTO.ResponseDto.SellerResponseDto;
import com.example.Ecommerce.Transformer.SellerTransformer;
import com.example.Ecommerce.exceptions.EmailAlreadyExistsException;
import com.example.Ecommerce.exceptions.InvalidSellerException;

import java.util.List;

public interface SellerService {
    public SellerResponseDto addSeller(SellerRequestDto sellerRequestDto) throws EmailAlreadyExistsException;
    public SellerResponseDto getById(int sellerId) throws InvalidSellerException;
    public List<SellerResponseDto> getAll();
}

package com.example.Ecommerce.service.impl;

import com.example.Ecommerce.DTO.RequestDto.SellerRequestDto;
import com.example.Ecommerce.DTO.ResponseDto.SellerResponseDto;
import com.example.Ecommerce.Transformer.SellerTransformer;
import com.example.Ecommerce.entities.Seller;
import com.example.Ecommerce.exceptions.EmailAlreadyExistsException;
import com.example.Ecommerce.exceptions.InvalidSellerException;
import com.example.Ecommerce.repositories.SellerRepository;
import com.example.Ecommerce.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    SellerRepository sellerRepository;

    @Override
    public SellerResponseDto addSeller(SellerRequestDto sellerRequestDto) throws EmailAlreadyExistsException {
        if(sellerRepository.findByEmailId(sellerRequestDto.getEmailId()) != null){
            throw new EmailAlreadyExistsException("Seller already exists with this emailId");
        }
        Seller seller = SellerTransformer.sellerRequestToSeller(sellerRequestDto);
        sellerRepository.save(seller);
        return SellerTransformer.SellerToSellerResponseDto(seller);
    }

    @Override
    public SellerResponseDto getById(int sellerId) throws InvalidSellerException {
        Seller seller;
        try{
            seller = sellerRepository.findById(sellerId).get();
            return SellerTransformer.SellerToSellerResponseDto(seller);
        }
        catch (Exception e){
            throw new InvalidSellerException("Invalid SellerId");
        }
    }

    @Override
    public List<SellerResponseDto> getAll() {
        List<Seller> sellers = sellerRepository.findAll();
        List<SellerResponseDto> list = new ArrayList<>();
        for(Seller seller : sellers){
            list.add(SellerTransformer.SellerToSellerResponseDto(seller));
        }
        return list;
    }
}

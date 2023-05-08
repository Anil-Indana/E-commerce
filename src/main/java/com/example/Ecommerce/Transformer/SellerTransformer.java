package com.example.Ecommerce.Transformer;

import com.example.Ecommerce.DTO.RequestDto.SellerRequestDto;
import com.example.Ecommerce.DTO.ResponseDto.SellerResponseDto;
import com.example.Ecommerce.entities.Seller;

public class SellerTransformer {

    public static  Seller sellerRequestToSeller(SellerRequestDto sellerRequestDto){
//        Seller seller = new Seller();
//        seller.setName(sellerRequestDto.getName());
//        seller.setEmailId(sellerRequestDto.getEmailId());
//        seller.setMobNo(sellerRequestDto.getMobNo());
//        seller.setAge(sellerRequestDto.getAge());
        Seller seller = Seller.builder()
                .age(sellerRequestDto.getAge())
                .mobNo(sellerRequestDto.getMobNo())
                .emailId(sellerRequestDto.getEmailId())
                .name(sellerRequestDto.getName())
                .build();
        return seller;
    }
    public static SellerResponseDto SellerToSellerResponseDto(Seller seller){
        return SellerResponseDto.builder()
                .emailId(seller.getEmailId()).name(seller.getName()).build();
    }
}

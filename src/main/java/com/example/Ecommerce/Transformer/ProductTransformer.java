package com.example.Ecommerce.Transformer;

import com.example.Ecommerce.DTO.RequestDto.ProductRequestDto;
import com.example.Ecommerce.DTO.ResponseDto.ProductResponseDto;
import com.example.Ecommerce.entities.Product;
import com.example.Ecommerce.enums.ProductStatus;

public class ProductTransformer {
    public static Product productRequestToProduct(ProductRequestDto productRequestDto){
        return Product.builder()
                .productStatus(ProductStatus.AVAILABLE)
                .productCategory(productRequestDto.getProductCategory())
                .price(productRequestDto.getPrice())
                .quantity(productRequestDto.getQuantity())
                .name(productRequestDto.getName())
                .build();
    }
    public static ProductResponseDto productToResponse(Product product){
        return ProductResponseDto.builder()
                .name(product.getName())
                .productStatus(product.getProductStatus())
                .sellerName(product.getSeller().getName())
                .quantity(product.getQuantity())
                .build();
    }
}

package com.example.Ecommerce.service;

import com.example.Ecommerce.DTO.RequestDto.ProductRequestDto;
import com.example.Ecommerce.DTO.ResponseDto.ProductResponseDto;
import com.example.Ecommerce.entities.Item;
import com.example.Ecommerce.enums.ProductCategory;
import com.example.Ecommerce.exceptions.InvalidSellerException;

import java.util.List;

public interface ProductService {
    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) throws  InvalidSellerException;
    public List<ProductResponseDto> getProductsOfCategory(ProductCategory category);
    public List<ProductResponseDto> getProductsBySellerEmail(String emailID);
    public List<ProductResponseDto> outOfStockProducts();
    public String deleteProductById(Integer id);
    public List<ProductResponseDto> productsWithPriceAndCategory(int num,ProductCategory category);
    public void decreaseProductQuantity(Item item) throws Exception;
}

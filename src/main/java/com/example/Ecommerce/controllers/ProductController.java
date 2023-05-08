package com.example.Ecommerce.controllers;

import com.example.Ecommerce.DTO.RequestDto.ProductRequestDto;
import com.example.Ecommerce.DTO.ResponseDto.ProductResponseDto;
import com.example.Ecommerce.enums.ProductCategory;
import com.example.Ecommerce.exceptions.InvalidSellerException;
import com.example.Ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;
    @PostMapping("/add")
    public ProductResponseDto addProduct(@RequestBody ProductRequestDto productRequestDto) throws InvalidSellerException {
      return  productService.addProduct(productRequestDto);
    }
    @GetMapping("/get")
    public List<ProductResponseDto> getProducts(@RequestParam("category")ProductCategory category){
        return productService.getProductsOfCategory(category);
    }
    @GetMapping("/getBySellerEmail")
    public  List<ProductResponseDto> getProductsBySellerEmail(@RequestParam("emailId") String emailId){
        return productService.getProductsBySellerEmail(emailId);
    }
    @GetMapping("/outOfStock")
    public List<ProductResponseDto> outOfStock(){
        return productService.outOfStockProducts();
    }
    @DeleteMapping("/delete")
    public String deleteProduct(@RequestParam("id") Integer id){
        return productService.deleteProductById(id);
    }

    @GetMapping("/priceAndCategory")
    public List<ProductResponseDto> productsByPriceAndCategory(@RequestParam("price")int price,@RequestParam("category")ProductCategory category){
        return productService.productsWithPriceAndCategory(price,category);
    }
}

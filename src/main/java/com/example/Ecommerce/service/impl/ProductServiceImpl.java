package com.example.Ecommerce.service.impl;

import com.example.Ecommerce.DTO.RequestDto.ProductRequestDto;
import com.example.Ecommerce.DTO.ResponseDto.ProductResponseDto;
import com.example.Ecommerce.Transformer.ProductTransformer;
import com.example.Ecommerce.entities.Item;
import com.example.Ecommerce.entities.Product;
import com.example.Ecommerce.entities.Seller;
import com.example.Ecommerce.enums.ProductCategory;
import com.example.Ecommerce.enums.ProductStatus;
import com.example.Ecommerce.exceptions.InvalidSellerException;
import com.example.Ecommerce.repositories.ProductRepository;
import com.example.Ecommerce.repositories.SellerRepository;
import com.example.Ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Override
    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) throws InvalidSellerException {
        Seller seller = null;
        seller = sellerRepository.findById(productRequestDto.getSellerId()).get();
        if(seller == null){
            throw new InvalidSellerException("Seller doesn't exist");
        }
        Product product = ProductTransformer.productRequestToProduct(productRequestDto);
        product.setSeller(seller);

        seller.getProducts().add(product);
        sellerRepository.save(seller);

        return ProductTransformer.productToResponse(product);
    }
    public List<ProductResponseDto> getProductsOfCategory(ProductCategory category){
        List<Product> productList = productRepository.findByProductCategory(category);
        List<ProductResponseDto> list = new ArrayList<>();
        for(Product product : productList){
            list.add(ProductTransformer.productToResponse(product));
        }
        return list;
    }
    public List<ProductResponseDto> getProductsBySellerEmail(String emailId){
        Seller seller = sellerRepository.findByEmailId(emailId);
        List<Product> products = seller.getProducts();
        List<ProductResponseDto> list = new ArrayList<>();
        for(Product product : products){
            if(product.getSeller().getEmailId().equals(emailId)){
                list.add(ProductTransformer.productToResponse(product));
            }
        }
        return list;
    }
    @Override
    public List<ProductResponseDto> outOfStockProducts(){
        List<Product> products = productRepository.findAll();
        List<ProductResponseDto> list = new ArrayList<>();
        for(Product product : products){
            if(product.getProductStatus().equals(ProductStatus.NOT_AVAILABLE)){
                list.add(ProductTransformer.productToResponse(product));
            }
        }
        return list;
    }

    @Override
    public String deleteProductById(Integer id) {
        productRepository.deleteById(id);
        return "Product removed";
    }

    @Override
    public List<ProductResponseDto> productsWithPriceAndCategory(int num, ProductCategory category) {
        List<Product> products = productRepository.getAllProductsByPriceAndCategory(num,category);
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for(Product product : products){
            productResponseDtos.add(ProductTransformer.productToResponse(product));
        }
        return productResponseDtos;
    }

    @Override
    public void decreaseProductQuantity(Item item) throws Exception {
        Product product = item.getProduct();
        int quantity  = item.getRequiredQuantity();
        int quantityAvailable = product.getQuantity();
        if(quantity > quantityAvailable) {
            throw new Exception("Item with Required quantity is not available");
        }
        product.setQuantity(quantityAvailable-quantity);
        if(product.getQuantity() == 0){
            product.setProductStatus(ProductStatus.NOT_AVAILABLE);
        }
    }
}

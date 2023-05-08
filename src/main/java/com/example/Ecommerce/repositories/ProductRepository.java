package com.example.Ecommerce.repositories;

import com.example.Ecommerce.entities.Product;
import com.example.Ecommerce.enums.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findByProductCategory(ProductCategory productCategory);

    //nativeQuery (select * from product p where p.price > :price,p.product_category=:category) productCategory should'be string
    @Query(value = "select p from Product p where p.price > :price and p.productCategory=:category")//nonNative
    List<Product> getAllProductsByPriceAndCategory(int price, ProductCategory category);
}
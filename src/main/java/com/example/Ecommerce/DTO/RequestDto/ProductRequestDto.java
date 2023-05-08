package com.example.Ecommerce.DTO.RequestDto;

import com.example.Ecommerce.enums.ProductCategory;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequestDto {
    int sellerId;

    String name;

    Integer price;

    Integer quantity;

    ProductCategory productCategory;
}

package com.example.Ecommerce.DTO.ResponseDto;

import com.example.Ecommerce.enums.ProductStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponseDto {
    String name;
    String sellerName;
    ProductStatus productStatus;
    int quantity;
}

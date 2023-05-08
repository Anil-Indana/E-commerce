package com.example.Ecommerce.DTO.RequestDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequestDto {
    int customerId;

    int productId;

    int requiredQuantity;

    String cardNo;

    int cvv;
}

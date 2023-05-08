package com.example.Ecommerce.DTO.RequestDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckOutCartRequestDto {
    int customerId;
    String cardNo;
    int cvv;
}

package com.example.Ecommerce.DTO.RequestDto;

import com.example.Ecommerce.enums.CardType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardRequestDto {
    String mobNo;
    String cardNo;
    int cvv;
    Date expiryDate;
    CardType cardType;
}

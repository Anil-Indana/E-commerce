package com.example.Ecommerce.DTO.ResponseDto;

import com.example.Ecommerce.entities.Customer;
import com.example.Ecommerce.entities.Item;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CartResponseDto {

    Integer cartTotal;

    Integer noOfItems;

    String customerName;

    List<ItemResponseDto> items;
}

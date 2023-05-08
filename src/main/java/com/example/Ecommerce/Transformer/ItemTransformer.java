package com.example.Ecommerce.Transformer;

import com.example.Ecommerce.DTO.RequestDto.ItemRequestDto;
import com.example.Ecommerce.DTO.ResponseDto.ItemResponseDto;
import com.example.Ecommerce.entities.Item;

public class ItemTransformer {
    public static Item itemRequestDtoToItem(ItemRequestDto itemRequestDto){
        return Item.builder()
                .requiredQuantity(itemRequestDto.getReqQuantity())
                .build();
    }
    public static ItemResponseDto itemToResponse(Item item){
        return ItemResponseDto.builder()
                .itemName(item.getProduct().getName())
                .priceOfOneItem(item.getProduct().getPrice())
                .quantity(item.getRequiredQuantity())
                .totalPrice(item.getRequiredQuantity()*item.getProduct().getPrice())
                .build();
    }
}

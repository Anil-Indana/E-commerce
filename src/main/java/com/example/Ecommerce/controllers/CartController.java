package com.example.Ecommerce.controllers;

import com.example.Ecommerce.DTO.RequestDto.CheckOutCartRequestDto;
import com.example.Ecommerce.DTO.RequestDto.ItemRequestDto;
import com.example.Ecommerce.DTO.ResponseDto.CartResponseDto;
import com.example.Ecommerce.DTO.ResponseDto.ItemResponseDto;
import com.example.Ecommerce.DTO.ResponseDto.OrderResponseDto;
import com.example.Ecommerce.entities.Item;
import com.example.Ecommerce.exceptions.InvalidCustomerIdException;
import com.example.Ecommerce.service.CartService;
import com.example.Ecommerce.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;
    @Autowired
    ItemService itemService;

    @PostMapping("/addToCart")
    public ResponseEntity addToCart(@RequestBody ItemRequestDto itemRequestDto){
        try{
            Item item = itemService.addItem(itemRequestDto);
            CartResponseDto cartResponseDto = cartService.saveToCart(itemRequestDto.getCustomerId(), item);
            return new ResponseEntity(cartResponseDto, HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            return  new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/removeFromCart")
    public ResponseEntity<String> removeFromCart(@RequestParam("itemId")int itemId,@RequestParam("customerId")int customerId) throws Exception {
        try{
            Item item = itemService.removeItem(itemId,customerId);
            CartResponseDto cartResponseDto = cartService.removeFromCart(customerId,item);
            return new ResponseEntity<>("Item removed from cart",HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            return new ResponseEntity<>("Unable to remove",HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("allItems")
    public List<ItemResponseDto> getALl(@RequestParam("customerId")int customerId) throws InvalidCustomerIdException {
        return cartService.viewAllItemsInCart(customerId);
    }
    @PostMapping("/checkOut")
    public OrderResponseDto checkOut(@RequestBody CheckOutCartRequestDto checkOutCartRequestDto) throws Exception {
        return cartService.checkOutCart(checkOutCartRequestDto);
    }
}

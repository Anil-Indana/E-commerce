package com.example.Ecommerce.service.impl;

import com.example.Ecommerce.DTO.RequestDto.CheckOutCartRequestDto;
import com.example.Ecommerce.DTO.ResponseDto.CartResponseDto;
import com.example.Ecommerce.DTO.ResponseDto.ItemResponseDto;
import com.example.Ecommerce.DTO.ResponseDto.OrderResponseDto;
import com.example.Ecommerce.Transformer.CartTransformer;
import com.example.Ecommerce.Transformer.ItemTransformer;
import com.example.Ecommerce.Transformer.OrderTransformer;
import com.example.Ecommerce.entities.*;
import com.example.Ecommerce.exceptions.InvalidCardException;
import com.example.Ecommerce.exceptions.InvalidCustomerIdException;
import com.example.Ecommerce.repositories.CardRepository;
import com.example.Ecommerce.repositories.CartRepository;
import com.example.Ecommerce.repositories.CustomerRepository;
import com.example.Ecommerce.repositories.OrderRepository;
import com.example.Ecommerce.service.CartService;
import com.example.Ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    private JavaMailSender emailSender;

    @Override
    public CartResponseDto saveToCart(Integer customerId, Item item) {
        Customer customer = customerRepository.findById(customerId).get();
        Cart cart = customer.getCart();

        // setting total of cart
        int newTotal = cart.getTotalAmount() + (item.getRequiredQuantity() * item.getProduct().getPrice());
        cart.setTotalAmount(newTotal);
        cart.getItems().add(item);

        cart.setNoOfItems(cart.getItems().size());
        item.setCart(customer.getCart());
        Cart savedCart = cartRepository.save(cart);

        // response Dto
        CartResponseDto cartResponseDto = CartTransformer.cartToResponse(savedCart);
        List<ItemResponseDto> itemResponseDtoList = new ArrayList<>();
        List<Item> items = savedCart.getItems();
        for(Item item1 : items){
            itemResponseDtoList.add(ItemTransformer.itemToResponse(item1));
        }

        cartResponseDto.setItems(itemResponseDtoList);

        return cartResponseDto;
    }

    @Override
    public OrderResponseDto checkOutCart(CheckOutCartRequestDto checkOutCartRequestDto) throws Exception {
        Customer customer; // checking whether customer exists with given id
        try{
            customer = customerRepository.findById(checkOutCartRequestDto.getCustomerId()).get();
        }
        catch (Exception e){
            throw new InvalidCustomerIdException("customer doesn't exist");
        }
        // checking whether card exists and validation of card with given cardNo
        Card card = cardRepository.findByCardNo(checkOutCartRequestDto.getCardNo());
        if(card==null || card.getCvv()!=checkOutCartRequestDto.getCvv() || card.getCustomer()!=customer){
            throw new InvalidCardException("Your card is not valid!!");
        }
       // checking if cart has items in it
       Cart cart = customer.getCart();
       if(cart.getItems().size() == 0){
           throw new Exception("Cart is Empty");
       }
       try{
           // trying to place order
           Ordered order = orderService.placeOrder(customer,card);
           customer.getOrders().add(order);
           resetCart(cart); // resetting the cart after placing order
           Ordered savedOrder = orderRepository.save(order);

           // responseDto
            OrderResponseDto orderResponseDto = OrderTransformer.OrderToResponse(order);
            List<ItemResponseDto> itemResponseDtos = new ArrayList<>();
            for(Item item : savedOrder.getItems()){
                itemResponseDtos.add(ItemTransformer.itemToResponse(item));
            }
            orderResponseDto.setItems(itemResponseDtos);
           String text = "Congrats " + customer.getName() + "your order is placed Successfully and the total amount is " +
                   order.getTotalPrice();
           SimpleMailMessage message = new SimpleMailMessage();
           message.setFrom("mailfromecommerce@gmail.com");
           message.setTo(customer.getEmailId());
           message.setSubject("CheckOut Cart");
           message.setText(text);
           emailSender.send(message);
            return orderResponseDto;
       }
       catch (Exception e){
           throw new Exception(e.getMessage());
       }
    }

    @Override
    public CartResponseDto removeFromCart(int customerId,Item item) throws Exception{
        Customer customer = customerRepository.findById(customerId).get();
        Cart cart = customer.getCart();
        int newTotal = cart.getTotalAmount() - (item.getRequiredQuantity() * item.getProduct().getPrice());
        cart.setTotalAmount(newTotal);
        cart.getItems().remove(item);
        cart.setNoOfItems(cart.getItems().size());
        item.setCart(null);
        cartRepository.save(cart);

        CartResponseDto cartResponseDto = new CartResponseDto();
        CartTransformer.cartToResponse(cart);

        return cartResponseDto;
    }

    @Override
    public List<ItemResponseDto> viewAllItemsInCart(int customerId) throws InvalidCustomerIdException {
        Customer customer;
        try{
            customer = customerRepository.findById(customerId).get();
        }
        catch (Exception e){
            throw new InvalidCustomerIdException("Invalid CustomerId");
        }
        List<Item> items = customer.getCart().getItems();
        List<ItemResponseDto> list = new ArrayList<>();
        for(Item item : items){
            list.add(ItemTransformer.itemToResponse(item));
        }
        return list;
    }


    public void resetCart(Cart cart){
        cart.setTotalAmount(0);
        for(Item item: cart.getItems()){
            item.setCart(null);
        }
        cart.setNoOfItems(0);
        cart.getItems().clear();
    }
}
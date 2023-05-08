package com.example.Ecommerce.service.impl;

import com.example.Ecommerce.DTO.RequestDto.OrderRequestDto;
import com.example.Ecommerce.DTO.ResponseDto.ItemResponseDto;
import com.example.Ecommerce.DTO.ResponseDto.OrderResponseDto;
import com.example.Ecommerce.Transformer.ItemTransformer;
import com.example.Ecommerce.Transformer.OrderTransformer;
import com.example.Ecommerce.entities.*;
import com.example.Ecommerce.exceptions.InvalidCardException;
import com.example.Ecommerce.exceptions.InvalidCustomerIdException;
import com.example.Ecommerce.exceptions.InvalidProductIdException;
import com.example.Ecommerce.repositories.CardRepository;
import com.example.Ecommerce.repositories.CustomerRepository;
import com.example.Ecommerce.repositories.OrderRepository;
import com.example.Ecommerce.repositories.ProductRepository;
import com.example.Ecommerce.service.OrderService;
import com.example.Ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    ProductService productService;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    OrderRepository orderRepository;
    @Override
    public Ordered placeOrder(Customer customer, Card card) throws Exception {
        // getting the cart of customer
        Cart cart = customer.getCart();
        Ordered order = new Ordered();
        order.setOrderNo(String.valueOf(UUID.randomUUID()));

        String maskedNo = generate(card.getCardNo());
        order.setCardUsed(maskedNo);
        order.setCustomer(customer);

        List<Item> orderedItems = new ArrayList<>();
        for(Item item : cart.getItems()){
            try{
                // reducing the quantity of items which are available
                // if reqQ > available quantity it'll throw exception from productService
                productService.decreaseProductQuantity(item);
                orderedItems.add(item); // if there is no Exception item will be added to orderedItems
            }
            catch (Exception e){
                throw new Exception("Product Out of Stock");
            }
        }

        order.setItems(orderedItems);
        for(Item item : orderedItems){
            item.setOrdered(order);
        }
        order.setTotalPrice(cart.getTotalAmount());
        return order;
    }

    @Override
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) throws Exception {
        Customer customer; // checking whether customer exists with given id
        try{
            customer = customerRepository.findById(orderRequestDto.getCustomerId()).get();
        }
        catch (Exception e){
            throw new InvalidCustomerIdException("Customer doesn't exist");
        }
        Product product; // checking whether product exists with given id
        try{
            product = productRepository.findById(orderRequestDto.getProductId()).get();
        }
        catch(Exception e){
            throw new InvalidProductIdException("product doesn't exist");
        }
        // checking whether card exists and valid with given id
        Card card = cardRepository.findByCardNo(orderRequestDto.getCardNo());
        if(card == null || card.getCvv() != orderRequestDto.getCvv() || card.getCustomer() != customer){
            throw new InvalidCardException("Invalid card");
        }
        // creating item according to the info given
        Item item = Item.builder()
                .requiredQuantity(orderRequestDto.getRequiredQuantity())
                .product(product)
                .build();
        try{
            // reducing quantity of product ...if the quantity < reqQuantity it'll throw exception
            productService.decreaseProductQuantity(item);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }

        // creating order
        Ordered order = new Ordered();
        order.setOrderNo(String.valueOf(UUID.randomUUID()));
        order.setCustomer(customer);
        order.setCardUsed(generate(card.getCardNo()));
        order.setTotalPrice(item.getRequiredQuantity()*item.getProduct().getPrice());
        order.getItems().add(item);

        customer.getOrders().add(order);
        item.setOrdered(order);
        product.getItemList().add(item);

        // saving the order and item will be saved as well coz item is child of order
        Ordered savedOrder = orderRepository.save(order);

        // response dto
        OrderResponseDto orderResponseDto = OrderTransformer.OrderToResponse(savedOrder);

        List<ItemResponseDto> itemResponseDtos = new ArrayList<>();
        List<Item> items = savedOrder.getItems();
        for(Item item1 : items){
            itemResponseDtos.add(ItemTransformer.itemToResponse(item1));
        }
        orderResponseDto.setItems(itemResponseDtos);
        return orderResponseDto;
    }

    @Override
    public List<OrderResponseDto> getAllOrders(int customerId) throws InvalidCustomerIdException {
        Customer customer;
        try{
            customer = customerRepository.findById(customerId).get();
        }
        catch (Exception e){
            throw new InvalidCustomerIdException("Invalid CustomerId");
        }

        List<Ordered> orders = customer.getOrders();
        List<OrderResponseDto> list = new ArrayList<>();
        for(Ordered order : orders){
            list.add(OrderTransformer.OrderToResponse(order));
        }
        return list;
    }

    @Override
    public OrderResponseDto deleteOrder(int customerId, int orderId) throws Exception {
        Customer customer = customerRepository.findById(customerId).get();
        if(customer == null) throw new Exception("Invalid CustomerId");
        List<Ordered> orders = customer.getOrders();
        Ordered order = null;
        for(Ordered ordered : orders){
            if(ordered.getId() == orderId) {
                order = ordered;
            }
        }
        orders.remove(order);
        orderRepository.deleteById(orderId);
        customerRepository.save(customer);
        return OrderTransformer.OrderToResponse(order);
    }

    public String generate(String cardNo){
        int n = cardNo.length();
        String res = "XXXXXXXX";
        res += cardNo.substring(n-4);
        return res;
    }
}

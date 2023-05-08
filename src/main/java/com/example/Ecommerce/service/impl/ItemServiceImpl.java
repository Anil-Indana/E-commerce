package com.example.Ecommerce.service.impl;

import com.example.Ecommerce.DTO.RequestDto.ItemRequestDto;
import com.example.Ecommerce.DTO.ResponseDto.ItemResponseDto;
import com.example.Ecommerce.Transformer.ItemTransformer;
import com.example.Ecommerce.entities.Customer;
import com.example.Ecommerce.entities.Item;
import com.example.Ecommerce.entities.Product;
import com.example.Ecommerce.exceptions.InvalidCustomerIdException;
import com.example.Ecommerce.exceptions.InvalidProductIdException;
import com.example.Ecommerce.repositories.CustomerRepository;
import com.example.Ecommerce.repositories.ItemRepository;
import com.example.Ecommerce.repositories.ProductRepository;
import com.example.Ecommerce.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ItemRepository itemRepository;

    @Override
    public Item addItem(ItemRequestDto itemRequestDto) throws InvalidCustomerIdException, InvalidProductIdException {
        Customer customer;
        try{
            customer = customerRepository.findById(itemRequestDto.getCustomerId()).get();
        }
        catch (Exception e){
            throw new InvalidCustomerIdException("Invalid Customer Id");
        }
        Product product;
        try{
            product = productRepository.findById(itemRequestDto.getProductId()).get();
        }
        catch (Exception e){
            throw new InvalidProductIdException("Product with ID doesn't exist");
        }
        Item item = ItemTransformer.itemRequestDtoToItem(itemRequestDto);
//        item.setCart(customer.getCart());
        item.setProduct(product);
        item.setPrice(product.getPrice());
        item.setName(item.getProduct().getName());
        item.setCategoryType(item.getProduct().getProductCategory());
        product.getItemList().add(item);

//        productRepository.save(product);
        return itemRepository.save(item); // saving child only
    }

    @Override
    public Item removeItem(int itemId,int customerId) throws Exception {
        Item item;
        try{
            item = itemRepository.findById(itemId).get();
        }
        catch (Exception e){
            throw new Exception("Invalid Item id");
        }
        Customer customer;
        try{
            customer = customerRepository.findById(customerId).get();
        }
        catch (Exception e){
            throw new InvalidCustomerIdException("Invalid Customer Id");
        }
       if(!customer.getCart().getItems().contains(item)){
           throw new Exception("Item doesn't exist in cart");
       }
       customer.getCart().getItems().remove(item);
       customerRepository.save(customer);
       return item;
    }
}

package com.example.Ecommerce.repositories;

import com.example.Ecommerce.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    public Customer findByMobNo(String mobNo);

    @Query(value = "select c from Customer c where c.age > :age")
    public List<Customer> findByAge(int age);
}

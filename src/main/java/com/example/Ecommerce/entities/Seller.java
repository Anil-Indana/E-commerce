package com.example.Ecommerce.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "seller")
@Builder
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String name;

    String mobNo;

    @Column(unique = true)
    String emailId;

    int age;

    @OneToMany(mappedBy = "seller",cascade = CascadeType.ALL)
    List<Product> products;
}

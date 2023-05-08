package com.example.Ecommerce.repositories;

import com.example.Ecommerce.entities.Card;
import com.example.Ecommerce.entities.Customer;
import com.example.Ecommerce.enums.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card,Integer> {

    @Query(value = "select c from Card c where c.cardType = :type")
    public List<Card> findByCardType(CardType type);
    public Card findByCardNo(String cardNo);
}

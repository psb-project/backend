package com.backend.project.psb.services.utils;

import com.backend.project.psb.dto.CreditCardRequest;
import com.backend.project.psb.model.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UnitTestHelper {

    public User createBasicPrototypeUser(String email, String username) {
        User user = User.builder()
                .email(email)
                .username(username)
                .role(Role.USER)
                .build();
        user.setAccount(createBasicPrototypeAccount(user, createBasicPrototypeCreditCardList(user)));
        return user;
    }

    public Account createBasicPrototypeAccount(User user, List<CreditCard> creditCards) {
        return Account.builder()
                .creationDate(Date.from(Instant.now()))
                .accountBalance(12312.432d)
                .creditCards(creditCards)
                .user(user)
                .build();
    }

    public CreditCard createBasicPrototypeCreditCard(User user, String cvv, String cardNumber) {
        return CreditCard.builder()
                .cvv(cvv)
                .cardNumber(cardNumber)
                .expirationDate(LocalDate.of(2026, 1, 15))
                .user(user)
                .build();
    }

    public CreditCardRequest createBasicPrototypeCreditCardRequest(User user) {
        return CreditCardRequest.builder()
                .cardHolderName("testUser")
                .transactions(new ArrayList<>())
                .expirationDate(LocalDate.of(2026, 1, 15))
                .user(user)
                .cvv("123")
                .build();
    }

    public List<CreditCard> createBasicPrototypeCreditCardList(User user) {
        List<CreditCard> creditCards = new ArrayList<>();
        creditCards.add(createBasicPrototypeCreditCard(user, "123", "12345"));
        creditCards.add(createBasicPrototypeCreditCard(user, "456", "31312"));
        creditCards.add(createBasicPrototypeCreditCard(user, "541", "31568"));
        return creditCards;
    }

    public Transactions createBasicPrototypeTransactions(CreditCard card, double amount) {
        return Transactions.builder()
                .timestamp(LocalDateTime.now())
                .amount(amount)
                .creditCard(card)
                .build();
    }

    public List<Transactions> createBasicPrototypeTansactionsList(CreditCard card) {
        List<Transactions> transactions = new ArrayList<>();
        transactions.add(createBasicPrototypeTransactions(card, 41412d));
        transactions.add(createBasicPrototypeTransactions(card, 1131d));
        transactions.add(createBasicPrototypeTransactions(card, 13565731d));
        return transactions;
    }

}

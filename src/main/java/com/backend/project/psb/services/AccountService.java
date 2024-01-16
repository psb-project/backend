package com.backend.project.psb.services;


import com.backend.project.psb.model.Account;
import com.backend.project.psb.model.CreditCard;
import com.backend.project.psb.model.User;
import com.backend.project.psb.repository.AccountRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepo accountRepo;

    /**
     * @param userId The id for the user that owns the account
     * @return The account of the specified user
     * @throws Exception In case no account exists for the specified user id
     */
    public Account getAccountForUser(Long userId) throws Exception {
        return accountRepo.findAccountByUser_Id(userId)
                .orElseThrow(() -> new Exception("Account for userId " + userId + " not found."));
    }

    /**
     * Creates a new account for a given user. Used for the creation of an account for a newly registered user
     *
     * @param user - The user for which we create the account
     * @return The created account for the user
     */
    public Account createNewAccountForUser(User user) {
        Account account = Account.builder()
                .user(user)
                .accountBalance(0d)
                .creditCards(new ArrayList<>())
                .creationDate(Date.from(Instant.now()))
                .build();
        return accountRepo.saveAndFlush(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepo.findAll();
    }

    public void addCreditCardToUserAccount(Long userId, CreditCard card) throws Exception {
        Account account = getAccountForUser(userId);
        account.getCreditCards().add(card);
        accountRepo.saveAndFlush(account);
    }

    public void subtractTransactionFromBalance(Account account, double amount) {
        account.setAccountBalance(account.getAccountBalance()-amount);
        accountRepo.saveAndFlush(account);
    }

    public Account addToAccountBalance(Account account, double amount) {
        account.setAccountBalance(account.getAccountBalance()+amount);
        return accountRepo.saveAndFlush(account);
    }

    public Account getAccountById(Long accountId) throws Exception {
        return accountRepo.findById(accountId).orElseThrow(() -> new Exception("No account found with id " + accountId));
    }
}

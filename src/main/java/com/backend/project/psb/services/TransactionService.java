package com.backend.project.psb.services;

import com.backend.project.psb.model.Account;
import com.backend.project.psb.model.CreditCard;
import com.backend.project.psb.model.Transactions;
import com.backend.project.psb.repository.CreditCardRepo;
import com.backend.project.psb.repository.TransactionRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final CreditCardRepo creditCardRepo;
    private final TransactionRepo transactionRepo;
    private final AccountService accountService;


    public List<Transactions> getTransactionsByCreditCard(Long creditCardId) throws Exception {
        return transactionRepo.findByCreditCardId(creditCardId).orElseThrow(() -> new Exception("nu gasim cardul"));
    }

    public Transactions makeTransaction(Long creditCardId, double amount) throws Exception {
        Optional<CreditCard> creditCardOptional = creditCardRepo.findById(creditCardId);
        if (creditCardOptional.isPresent()) {
            CreditCard creditCard = creditCardOptional.get();
            Account account = creditCard.getUser().getAccount();
            if ((account.getAccountBalance() - amount) >= 0) {
                Transactions transaction = Transactions.builder()
                        .creditCard(creditCard)
                        .amount(amount)
                        .timestamp(LocalDateTime.now())
                        .build();
                accountService.subtractTransactionFromBalance(account, amount);
                return transactionRepo.save(transaction);
            } else throw new Exception("Insufficient balance");
        } else {
            throw new Exception("Credit card not found with id: " + creditCardId);
        }
    }

}

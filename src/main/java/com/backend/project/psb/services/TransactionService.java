package com.backend.project.psb.services;

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


    public List<Transactions> getTransactionsByCreditCard(Long creditCardId) throws Exception {
        return transactionRepo.findByCreditCardId(creditCardId).orElseThrow(() -> new Exception("nu gasim cardul"));
    }


    public Transactions makeTransaction(Long creditCardId, double amount) throws Exception {
        //de lucrat la logica tranzactiei
        Optional<CreditCard> creditCardOptional = creditCardRepo.findById(creditCardId);
        if (creditCardOptional.isPresent()) {
            CreditCard creditCard = creditCardOptional.get();
            Transactions transaction = Transactions.builder()
                    .creditCard(creditCard)
                    .amount(amount)
                    .timestamp(LocalDateTime.now())
                    .build();
            return transactionRepo.save(transaction);
        } else {
            throw new Exception("Credit card not found with id: " + creditCardId);
        }
    }

    //todo vedem ce facem cu metoda de mai jos
    private double calculateCurrentBalance(CreditCard creditCard) {
        // Implement logic to calculate the current balance based on transactions
        // For simplicity, assume no fees or interest for now
        //transaction of a certain card
        Optional<List<Transactions>> transactions = transactionRepo.findByCreditCardId(creditCard.getId());
        return transactions.map(transactionsList -> transactionsList.stream().mapToDouble(Transactions::getAmount).sum()).orElse(-1.0);
    }
}

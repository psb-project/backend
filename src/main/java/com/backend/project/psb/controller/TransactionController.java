package com.backend.project.psb.controller;

import com.backend.project.psb.dto.TransactionRequest;
import com.backend.project.psb.model.Transactions;
import com.backend.project.psb.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/{creditCardId}")
    public ResponseEntity<List<Transactions>> getTransactionsByCreditCard(@PathVariable Long creditCardId) throws Exception {
        // Get transactions for the specified credit card
        List<Transactions> transactions = transactionService.getTransactionsByCreditCard(creditCardId);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/make")
    public ResponseEntity<Transactions> makeTransaction(@RequestBody TransactionRequest transactionRequest) throws Exception {
        // Make a transaction for the specified credit card
        Transactions newTransaction = transactionService.makeTransaction(transactionRequest.getCreditCard().getId(), transactionRequest.getAmount());
        return ResponseEntity.status(HttpStatus.CREATED).body(newTransaction);
    }
}

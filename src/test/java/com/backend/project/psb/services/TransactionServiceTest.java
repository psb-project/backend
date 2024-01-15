package com.backend.project.psb.services;

import com.backend.project.psb.model.Account;
import com.backend.project.psb.model.CreditCard;
import com.backend.project.psb.model.Transactions;
import com.backend.project.psb.model.User;
import com.backend.project.psb.repository.CreditCardRepo;
import com.backend.project.psb.repository.TransactionRepo;
import com.backend.project.psb.services.utils.UnitTestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private CreditCardRepo creditCardRepo;

    @Mock
    private TransactionRepo transactionRepo;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransactionService transactionService;
    @InjectMocks
    private UnitTestHelper helper;


    @Test
    void whenGetTransactionsByCreditCard_thenReturnTransactionsList() throws Exception {
        Long creditCardId = 1L;
        User user = helper.createBasicPrototypeUser("test@email.com", "username");
        CreditCard creditCard = helper.createBasicPrototypeCreditCard(user, "123", "41231");
        List<Transactions> mockTransactions = helper.createBasicPrototypeTansactionsList(creditCard);

        when(transactionRepo.findByCreditCardId(creditCardId)).thenReturn(Optional.of(mockTransactions));

        List<Transactions> result = transactionService.getTransactionsByCreditCard(creditCardId);

        assertNotNull(result);
        assertEquals(mockTransactions, result);
    }

    @Test
    void whenGetTransactionsByNonExistingCreditCard_thenThrowException() {
        Long creditCardId = 2L;
        when(transactionRepo.findByCreditCardId(creditCardId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> transactionService.getTransactionsByCreditCard(creditCardId));

        assertTrue(exception.getMessage().contains("nu gasim cardul"));
    }

    @Test
    void whenMakeTransactionWithSufficientBalance_thenTransactionIsSaved() throws Exception {
        Long creditCardId = 1L;
        double amount = 50.0;
        User user = helper.createBasicPrototypeUser("test@email.com", "username");
        CreditCard mockCreditCard = helper.createBasicPrototypeCreditCard(user, "123", "12345");

        when(creditCardRepo.findById(creditCardId)).thenReturn(Optional.of(mockCreditCard));
        doNothing().when(accountService).subtractTransactionFromBalance(any(Account.class), anyDouble());

        Transactions savedTransaction = new Transactions(); // Assume this is a properly initialized Transactions object
        when(transactionRepo.save(any(Transactions.class))).thenReturn(savedTransaction);

        Transactions result = transactionService.makeTransaction(creditCardId, amount);

        assertNotNull(result);
        assertEquals(savedTransaction, result);
    }

    @Test
    void whenMakeTransactionWithInsufficientBalance_thenThrowException() {
        Long creditCardId = 1L;
        double amount = 1000.0;
        User user = helper.createBasicPrototypeUser("test@email.com", "username");
        CreditCard mockCreditCard = helper.createBasicPrototypeCreditCard(user, "123", "12345");
        user.getAccount().setAccountBalance(10d);

        when(creditCardRepo.findById(creditCardId)).thenReturn(Optional.of(mockCreditCard));

        Exception exception = assertThrows(Exception.class, () -> transactionService.makeTransaction(creditCardId, amount));

        assertTrue(exception.getMessage().contains("Insufficient balance"));
    }

    @Test
    void whenMakeTransactionWithNonExistingCreditCard_thenThrowException() {
        Long creditCardId = 2L;
        double amount = 50.0;
        when(creditCardRepo.findById(creditCardId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> transactionService.makeTransaction(creditCardId, amount));

        assertTrue(exception.getMessage().contains("Credit card not found with id: " + creditCardId));
    }


}

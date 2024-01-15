package com.backend.project.psb.services;

import com.backend.project.psb.model.Account;
import com.backend.project.psb.model.CreditCard;
import com.backend.project.psb.model.User;
import com.backend.project.psb.repository.AccountRepo;
import com.backend.project.psb.services.utils.UnitTestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepo accountRepo;
    @InjectMocks
    private AccountService accountService;
    @InjectMocks
    private UnitTestHelper helper;


    @Test
    void whenGetAccountForUser_thenReturnAccount() throws Exception {
        Long userId = 1L;
        User user = helper.createBasicPrototypeUser("test@mail.com", "testUser");
        Account mockAccount = user.getAccount();
        when(accountRepo.findAccountByUser_Id(userId)).thenReturn(Optional.of(mockAccount));

        Account result = accountService.getAccountForUser(userId);

        assertNotNull(result);
        assertEquals(mockAccount, result);
    }

    @Test
    void whenGetAccountForNonExistingUser_thenThrowException() {
        Long userId = 2L;
        when(accountRepo.findAccountByUser_Id(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> accountService.getAccountForUser(userId));

        assertTrue(exception.getMessage().contains("Account for userId " + userId + " not found."));
    }

    @Test
    void whenCreateNewAccountForUser_thenReturnNewAccount() {
        User user = helper.createBasicPrototypeUser("test@mail.com", "testUser");
        Account savedAccount = user.getAccount();
        when(accountRepo.saveAndFlush(Mockito.any())).thenReturn(savedAccount);
        Account result = accountService.createNewAccountForUser(user);

        assertNotNull(result);
        assertEquals(savedAccount, result);
    }

    @Test
    void whenAddCreditCardToUserAccount_thenCardIsAdded() throws Exception {
        Long userId = 1L;
        User user = helper.createBasicPrototypeUser("test@mail.com", "testUser");
        Account mockAccount = user.getAccount();
        CreditCard card = helper.createBasicPrototypeCreditCard(user, "123", "41412");
        when(accountRepo.findAccountByUser_Id(userId)).thenReturn(Optional.of(mockAccount));

        accountService.addCreditCardToUserAccount(userId, card);

        assertTrue(mockAccount.getCreditCards().contains(card));
        Mockito.verify(accountRepo).saveAndFlush(mockAccount);
    }
}
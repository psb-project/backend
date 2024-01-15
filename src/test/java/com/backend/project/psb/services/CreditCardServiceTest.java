package com.backend.project.psb.services;

import com.backend.project.psb.dto.CreditCardRequest;
import com.backend.project.psb.model.CreditCard;
import com.backend.project.psb.model.User;
import com.backend.project.psb.repository.CreditCardRepo;
import com.backend.project.psb.repository.UserRepo;
import com.backend.project.psb.services.utils.UnitTestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditCardServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private CreditCardRepo creditCardRepo;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private CreditCardService creditCardService;
    @InjectMocks
    private UnitTestHelper helper;

    @Test
    void whenGetUserCreditCards_thenReturnCreditCardsList() throws Exception {
        User mockUser = helper.createBasicPrototypeUser("test@email.com", "testUser");
        List<CreditCard> mockCreditCards = helper.createBasicPrototypeCreditCardList(mockUser);

        when(userRepo.findByEmail(Mockito.any())).thenReturn(Optional.of(mockUser));
        when(creditCardRepo.findByUserId(mockUser.getId())).thenReturn(Optional.of(mockCreditCards));

        List<CreditCard> result = creditCardService.getUserCreditCards(Mockito.any());

        assertNotNull(result);
        assertEquals(mockCreditCards, result);
    }

    @Test
    void whenGetUserCreditCardsForNonExistingUser_thenThrowException() {
        String username = "test@email.com";
        when(userRepo.findByEmail(username)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            creditCardService.getUserCreditCards(username);
        });

        assertTrue(exception.getMessage().contains("nu avem user..."));
    }

    @Test
    void whenAddCreditCard_thenCardIsAdded() throws Exception {
        String username = "test@email.com";
        User mockUser = helper.createBasicPrototypeUser("test@email.com", "testUser");
        CreditCardRequest creditCardRequest = helper.createBasicPrototypeCreditCardRequest(mockUser);
        CreditCard savedCreditCard = helper.createBasicPrototypeCreditCard(mockUser, creditCardRequest.getCvv(),
                creditCardRequest.getCardNumber());

        when(userRepo.findByEmail(username)).thenReturn(Optional.of(mockUser));
        when(creditCardRepo.save(Mockito.any())).thenReturn(savedCreditCard);

        CreditCard result = creditCardService.addCreditCard(username, creditCardRequest);

        assertNotNull(result);
        assertEquals(savedCreditCard, result);
        verify(accountService).addCreditCardToUserAccount(eq(mockUser.getId()), Mockito.any());
    }

    @Test
    void whenDeleteCreditCard_thenCardIsDeleted() {
        Long creditCardId = 1L;

        doNothing().when(creditCardRepo).deleteById(creditCardId);

        creditCardService.deleteCreditCard(creditCardId);

        verify(creditCardRepo).deleteById(creditCardId);
    }

}
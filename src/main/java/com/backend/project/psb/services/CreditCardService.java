package com.backend.project.psb.services;

import com.backend.project.psb.dto.CreditCardRequest;
import com.backend.project.psb.model.CreditCard;
import com.backend.project.psb.model.User;
import com.backend.project.psb.repository.CreditCardRepo;
import com.backend.project.psb.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreditCardService {

    private final UserRepo userRepo;
    private final CreditCardRepo creditCardRepo;


    public List<CreditCard> getUserCreditCards(String username) throws Exception {
        User user = userRepo.findByEmail(username).orElseThrow(() -> new Exception("nu avem user..."));
        return creditCardRepo.findByUserId(user.getId()).orElseThrow(() -> new Exception("nu avem card..."));
    }

    public CreditCard addCreditCard(String username, CreditCardRequest creditCard) throws Exception {

        User user = userRepo.findByEmail(username).orElseThrow(() -> new Exception("nu avem user..."));
        CreditCard creditCard1 = CreditCard.builder()
                .cardNumber(creditCard.getCardNumber())
                .cardHolderName(creditCard.getCardHolderName())
                .expirationDate(creditCard.getExpirationDate())
                .cvv(creditCard.getCvv())
                .user(user)
                .build();


        return creditCardRepo.save(creditCard1);
    }


    public void deleteCreditCard(Long creditCardId) {
        creditCardRepo.deleteById(creditCardId);
    }

}

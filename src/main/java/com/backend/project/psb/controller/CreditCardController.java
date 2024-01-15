package com.backend.project.psb.controller;

import com.backend.project.psb.dto.CreditCardRequest;
import com.backend.project.psb.model.CreditCard;
import com.backend.project.psb.services.CreditCardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/credit-card")
@RequiredArgsConstructor
@Slf4j
public class CreditCardController {

    private final CreditCardService creditCardService;
    private final UserDetailsService userDetailsService;

    // se gasesc cardurile pe baza mail-ului
    @GetMapping("/user")
    public ResponseEntity<List<CreditCard>> getUserCreditCards() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        log.info("username: " + username);
        // Fetch user credit cards based on username
        log.info("userDetails: " + userDetails.getUsername());
        List<CreditCard> creditCards = creditCardService.getUserCreditCards(userDetails.getUsername());
        return ResponseEntity.ok(creditCards);
    }

    //se adauga card pe baza mail-ului
    @PostMapping("/add")
    public ResponseEntity<CreditCard> addCreditCard(@RequestBody CreditCardRequest creditCard) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        log.info("username: " + username);
        log.info("userDetails: " + userDetails.getUsername());
        // Add credit card for the authenticated user
        CreditCard newCreditCard = creditCardService.addCreditCard(userDetails.getUsername(), creditCard);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCreditCard);
    }

    //se sterge cardul pe baza creditCardId
    @DeleteMapping("/delete/{creditCardId}")
    public ResponseEntity<?> deleteCreditCard(@PathVariable Long creditCardId) {
        // Delete credit card by id for the authenticated user
        creditCardService.deleteCreditCard(creditCardId);
        return ResponseEntity.noContent().build();
    }
}

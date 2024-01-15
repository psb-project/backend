package com.backend.project.psb.dto;

import com.backend.project.psb.model.Transactions;
import com.backend.project.psb.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardRequest {
    private Long id;

    private String cardNumber;

    private String cardHolderName;

    private LocalDate expirationDate;

    private User user;

    private String cvv;

    private List<Transactions> transactions;
}

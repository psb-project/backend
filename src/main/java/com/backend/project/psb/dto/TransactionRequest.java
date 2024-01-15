package com.backend.project.psb.dto;

import com.backend.project.psb.model.CreditCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    private LocalDateTime timestamp;

    private double amount;

    private CreditCard creditCard;
}

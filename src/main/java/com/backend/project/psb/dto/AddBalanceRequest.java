package com.backend.project.psb.dto;

import com.backend.project.psb.model.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddBalanceRequest {

    private Account account;
    private double amount;
}

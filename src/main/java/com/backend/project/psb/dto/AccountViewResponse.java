package com.backend.project.psb.dto;

import com.backend.project.psb.model.CreditCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountViewResponse {

    private Long accountId;
    private Long userId;
    private String username;
    private List<CreditCard> creditCards;
    private Double accountBalance;}

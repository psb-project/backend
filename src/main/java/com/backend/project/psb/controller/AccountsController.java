package com.backend.project.psb.controller;

import com.backend.project.psb.dto.AccountViewRequest;
import com.backend.project.psb.dto.AccountViewResponse;
import com.backend.project.psb.model.Account;
import com.backend.project.psb.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountsController {

    private final AccountService accountService;

    /**
     * Endpoint for requesting the account view of a specified user
     *
     * @param request The request body for the endpoint, contains the user id for which the request is made
     * @return ResponseEntity, either on status OK with the account view or BAD_REQUEST if any errors appears
     */
    @GetMapping("/view-account")
    public ResponseEntity<?> getAccountViewForUser(@RequestBody AccountViewRequest request) {
        try {
            Account account = accountService.getAccountForUser(request.getUserId());

            AccountViewResponse accountViewResponse = AccountViewResponse.builder()
                    .userId(account.getUser().getId())
                    .username(account.getUser().getUsername())
                    .accountId(account.getId())
                    .creditCards(account.getCreditCards())
                    .accountBalance(account.getAccountBalance())
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(accountViewResponse);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error when fetching account view for user " + request.getUserId());
        }
    }

    @GetMapping("all-accounts")
    public ResponseEntity<?> getAllAccounts() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(accountService.getAllAccounts());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(accountService.getAllAccounts());
        }
    }
}

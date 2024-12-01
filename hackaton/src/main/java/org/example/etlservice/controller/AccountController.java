package org.example.etlservice.controller;

import org.example.etlservice.model.Account;
import org.example.etlservice.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/access/{accessId}/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAccounts(@PathVariable String accessId) {
        return ResponseEntity.ok(accountService.getAccounts(accessId));
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@PathVariable String accessId, @RequestBody Account account) {
        return ResponseEntity.status(201).body(accountService.createAccount(accessId, account));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountDetails(@PathVariable String accessId, @PathVariable UUID accountId) {
        return ResponseEntity.ok(accountService.getAccountDetails(accessId, accountId));
    }

    @PostMapping("/{accountId}/check-access")
    public ResponseEntity<Boolean> checkAccess(@PathVariable String accessId, @PathVariable UUID accountId, @RequestBody String permissionName) {
        return ResponseEntity.ok(accountService.checkAccess(accessId, accountId, permissionName));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String accessId, @PathVariable UUID accountId) {
        accountService.deleteAccount(accessId, accountId);
        return ResponseEntity.noContent().build();
    }
}

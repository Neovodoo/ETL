package org.example.etlservice.controller;

import org.example.etlservice.model.Account;
import org.example.etlservice.repository.AccountRepository;
import org.example.etlservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/access/{accessId}")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleService roleService;

    // Get account details
    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<Account> getAccountById(
            @PathVariable UUID accountId) {
        return ResponseEntity.status(201).body(accountRepository.getById(accountId));
    }

    // Create a new account
    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(
            @PathVariable String accessId,
            @RequestBody Account account) {
        Account savedAccount = accountRepository.save(account);
        return ResponseEntity.status(201).body(savedAccount);
    }

    // Delete an account
    @DeleteMapping("/accounts/{accountId}")
    public ResponseEntity<Void> deleteAccount(
            @PathVariable UUID accountId) {
        accountRepository.deleteById(accountId);
        return ResponseEntity.noContent().build();
    }
}

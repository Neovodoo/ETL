package org.example.accessservice.controller;

import org.example.accessservice.model.Account;
import org.example.accessservice.model.Role;
import org.example.accessservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    // Generate token for an account
    @PostMapping("/{id}/generate-token")
    public ResponseEntity<String> generateUserToken(@PathVariable UUID id) {
        Optional<Account> account = accountService.getAccountById(id);
        if (account.isPresent()) {
            String token = accountService.generateUserToken(account.get());
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Check access for a specific permission
    @PostMapping("/{id}/check-access")
    public ResponseEntity<Boolean> checkAccess(@PathVariable UUID id, @RequestBody String permission) {
        Optional<Account> account = accountService.getAccountById(id);
        if (account.isPresent()) {
            boolean hasAccess = accountService.checkAccess(account.get(), permission);
            return ResponseEntity.ok(hasAccess);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create a new account
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account createdAccount = accountService.createAccount(account);
        return ResponseEntity.ok(createdAccount);
    }

    // Get all accounts
    @GetMapping
    public ResponseEntity<Iterable<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    // Get account by ID
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable UUID id) {
        Optional<Account> account = accountService.getAccountById(id);
        return account.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update an account
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable UUID id, @RequestBody Account updatedAccount) {
        try {
            Account account = accountService.updateAccount(id, updatedAccount);
            return ResponseEntity.ok(account);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete an account
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{id}/roles")
    public ResponseEntity<?> getRolesByAccountId(@PathVariable UUID id) {
        Optional<Account> account = accountService.getAccountById(id);
        if (account.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Extract the AccountData from Optional
        Account account_ = account.get();

        // Check if account has a role
        Role role = account_.getRole();
        if (role == null) {
            return ResponseEntity.ok("No role assigned to this account.");
        }

        // Return the role information
        return ResponseEntity.ok(role);
    }
}

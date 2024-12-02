package org.example.etlservice.controller;

import org.example.etlservice.model.Account;
import org.example.etlservice.repository.AccountRepository;
import org.example.etlservice.service.AccountService;
import org.example.etlservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;


@RestController
@RequestMapping("/access/{accessId}/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleService roleService;

    // Get account details
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account createdAccount = accountService.createAccount(account);
        return ResponseEntity.status(201).body(createdAccount);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccount(@PathVariable UUID accountId) {
        Optional<Account> accountOptional = accountService.getAccount(accountId);
        return accountOptional.map(account -> ResponseEntity.status(201).body(account)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{accountId}")
    public ResponseEntity<String> assignRoleToAccount(
            @PathVariable UUID accountId,
            @RequestBody Map<String, UUID> roleRequest) {

        UUID roleId = roleRequest.get("roleId");

        accountService.assignRoleToAccount(accountId, roleId);

        return ResponseEntity.status(HttpStatus.CREATED).body("Role assigned successfully.");
    }
    @PostMapping("/{accountId}/check-access")
    public ResponseEntity<Map<String, Boolean>> checkAccountPermission(
            @PathVariable UUID accountId,
            @RequestBody Map<String, String> permissionRequest) {

        String permissionName = permissionRequest.get("permissionName");
        boolean hasAccess = accountService.checkAccountPermission(accountId, permissionName);

        Map<String, Boolean> response = new HashMap<>();
        response.put("hasAccess", hasAccess);

        return ResponseEntity.ok(response);
    }

}

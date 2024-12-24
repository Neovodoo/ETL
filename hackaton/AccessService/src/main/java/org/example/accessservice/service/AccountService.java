package org.example.accessservice.service;

import org.example.accessservice.model.Account;
import org.example.accessservice.repository.AccountRepository;
import org.example.accessservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;

    // Generate a user token (e.g., UUID-based token for simplicity)
    public String generateUserToken(Account account) {
        String token = UUID.randomUUID().toString();
        account.setToken(token);
        accountRepository.save(account);
        return token;
    }

    // Check if an account has access to a specific permission
//    public boolean checkAccess(Account account, String permission) {
//        return account.getRole()
//                .getPermissions()
//                .stream()
//                .map(Permission::getName)
//                .anyMatch(p -> p.equalsIgnoreCase(permission));
//    }
    public boolean checkAccess(Account account, String permission) {
//        // Log the account name
//        System.out.println("Checking access for account: " + account.getName());
//
//        // Log the role name
//        if (account.getRole() != null) {
//            System.out.println("Role: " + account.getRole().getName());
//        } else {
//            System.out.println("No role assigned to this account.");
//            return false;
//        }
//
//        // Log all permissions of the role
//        System.out.println("Permissions for the role: ");
//        if (account.getRole().getPermissions() != null) {
//            account.getRole().getPermissions()
//                    .forEach(permissionObj -> System.out.println("- " + permissionObj.getName()));
//        } else {
//            System.out.println("No permissions found for this role.");
//            return false;
//        }
//
//        // Log the permission being checked
//        System.out.println("Permission being checked: " + permission);

        // Perform the check
        return account.getRole()
                .getPermissions()
                .stream()
                .map(p -> p.getName().trim())
                .anyMatch(p -> p.equalsIgnoreCase(permission.trim()));

    }

    // Create a new account
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    // Get all accounts
    public Iterable<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Get an account by ID
    public Optional<Account> getAccountById(UUID id) {
        return accountRepository.findById(id);
    }

    // Update an account
    public Account updateAccount(UUID id, Account updatedAccount) {
        return accountRepository.findById(id)
                .map(account -> {
                    account.setName(updatedAccount.getName());
                    account.setRole(updatedAccount.getRole());
                    return accountRepository.save(account);
                })
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
    }

    // Delete an account
    public void deleteAccount(UUID id) {
        accountRepository.deleteById(id);
    }
}

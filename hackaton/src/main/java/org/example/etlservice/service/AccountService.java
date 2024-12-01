package org.example.etlservice.service;

import org.example.etlservice.model.Account;
import org.example.etlservice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    // Create a new account
    public Account createAccount(Account account) {
        account.setId(UUID.randomUUID());
        return accountRepository.save(account);
    }

    // Retrieve an account by ID
    public Optional<Account> getAccountById(UUID accountId) {
        return accountRepository.findById(accountId);
    }

    // Retrieve all accounts
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Update an account
    public Account updateAccount(UUID accountId, Account updatedAccount) {
        Optional<Account> existingAccount = accountRepository.findById(accountId);

        if (existingAccount.isPresent()) {
            Account account = existingAccount.get();
            account.setName(updatedAccount.getName());
            account.setRoleId(updatedAccount.getRoleId());
            return accountRepository.save(account);
        } else {
            throw new RuntimeException("Account with ID " + accountId + " not found.");
        }
    }

    // Delete an account
    public void deleteAccount(UUID accountId) {
        if (accountRepository.existsById(accountId)) {
            accountRepository.deleteById(accountId);
        } else {
            throw new RuntimeException("Account with ID " + accountId + " not found.");
        }
    }
}

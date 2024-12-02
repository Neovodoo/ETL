package org.example.etlservice.service;

import org.example.etlservice.model.Account;
import org.example.etlservice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Creates a new account.
     *
     * @param account the account to create
     * @return the created account
     */
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    /**
     * Assigns a role to an account.
     *
     * @param account the account to update
     * @param roleId  the role ID to assign
     * @return the updated account
     */
    public Account assignRoleToAccount(Account account, UUID roleId) {
        account.setRoleId(roleId);
        return accountRepository.save(account);
    }

    /**
     * Deletes an account by its ID and access ID.
     *
     * @param accountId the ID of the account
     */
    public void deleteAccount(UUID accountId) {
        accountRepository.deleteById(accountId);
    }
}

package org.example.etlservice.service;

import org.example.etlservice.model.Account;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    List<Account> getAccounts(String accessId);

    Account createAccount(String accessId, Account account);

    Account getAccountDetails(String accessId, UUID accountId);

    boolean checkAccess(String accessId, UUID accountId, String permissionName);

    void deleteAccount(String accessId, UUID accountId);
}

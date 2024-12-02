package org.example.etlservice.service;

import org.example.etlservice.model.Account;
import org.example.etlservice.model.Role;
import org.example.etlservice.model.Permission;
import org.example.etlservice.repository.AccountRepository;
import org.example.etlservice.repository.PermissionRepository;
import org.example.etlservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account assignRoleToAccount(UUID accountID, UUID roleId) {
        Optional<Account> accountOpt = accountRepository.findById(accountID);
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            account.setRoleId(roleId);
            return accountRepository.save(account);
        } else {
            throw new RuntimeException("Account not found with ID: " + accountID);
        }
    }

    public void deleteAccount(UUID accountId) {
        accountRepository.deleteById(accountId);
    }

    public Optional<Account> getAccount(UUID accountId)
    {
        return accountRepository.findById(accountId);
    }

    public boolean checkAccountPermission(UUID accountId, String permissionName) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + accountId));

        UUID roleId = account.getRoleId();
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with ID: " + roleId));

        return role.getPermissions().stream()
                .anyMatch(permissionId -> {
                    Permission permission = permissionRepository.findById(permissionId)
                            .orElseThrow(() -> new IllegalArgumentException("Permission not found with ID: " + permissionId));
                    return permission.getName().equalsIgnoreCase(permissionName);
                });
    }
}

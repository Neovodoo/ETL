package org.example.etlservice.service;

import org.example.etlservice.model.Role;

import java.util.UUID;

public interface RoleService {
    Role createRole(String accessId, Role role);

    Role updateRole(String accessId, UUID roleId, Role role);

    void deleteRole(String accessId, UUID roleId);
}

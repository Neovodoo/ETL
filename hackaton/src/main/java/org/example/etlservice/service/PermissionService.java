package org.example.etlservice.service;

import org.example.etlservice.model.Permission;

import java.util.UUID;

public interface PermissionService {
    Permission createPermission(String accessId, Permission permission);

    Permission updatePermission(String accessId, UUID permissionId, Permission permission);

    void deletePermission(String accessId, UUID permissionId);
}

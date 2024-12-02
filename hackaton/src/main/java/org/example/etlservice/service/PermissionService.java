package org.example.etlservice.service;

import org.example.etlservice.model.ETLProcess;
import org.example.etlservice.model.Permission;
import org.example.etlservice.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    public Permission createPermission(Permission permission) {
        // Assume `accessId` is validated or set via a relationship
        permissionRepository.save(permission); // Save ETLProcess to get its ID
        return permission;
    }

    public Permission updatePermission(UUID permissionId, Permission permissionDetails) {
        Optional<Permission> permissionOpt = permissionRepository.findById(permissionId);
        if (permissionOpt.isPresent()) {
            Permission permission = permissionOpt.get();
            permission.setName(permissionDetails.getName());
            return permissionRepository.save(permission);
        } else {
            throw new RuntimeException("Permission not found with ID: " + permissionId);
        }
    }

    public void deletePermission(UUID permissionId) {
        if (permissionRepository.existsById(permissionId)) {
            permissionRepository.deleteById(permissionId);
        } else {
            throw new RuntimeException("Permission not found with ID: " + permissionId);
        }
    }
}

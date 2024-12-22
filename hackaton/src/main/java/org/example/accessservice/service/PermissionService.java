package org.example.accessservice.service;

import org.example.accessservice.model.Permission;
import org.example.accessservice.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PermissionService {
    @Autowired
    private PermissionRepository repository;

    public Permission createPermission(Permission permission) {
        return repository.save(permission);
    }

    public List<Permission> getAllPermissions() {
        return repository.findAll();
    }

    public Optional<Permission> getPermissionById(UUID id) {
        return repository.findById(id);
    }

    public Permission updatePermission(UUID id, Permission updatedPermission) {
        return repository.findById(id).map(permission -> {
            permission.setName(updatedPermission.getName());
            return repository.save(permission);
        }).orElseThrow(() -> new RuntimeException("Permission not found with id: " + id));
    }

    public void deletePermission(UUID id) {
        repository.deleteById(id);
    }
}

package org.example.etlservice.service;

import org.example.etlservice.model.Role;
import org.example.etlservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;


    // Create a new role
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    // Update an existing role
    public Optional<Role> updateRole(UUID roleId, Role updatedRole) {
        return roleRepository.findById(roleId).map(existingRole -> {
            existingRole.setName(updatedRole.getName());
            existingRole.setPermissions(updatedRole.getPermissions());
            return roleRepository.save(existingRole);
        });
    }

    // Delete a role
    public void deleteRole(UUID roleId) {
        roleRepository.deleteById(roleId);
    }
}

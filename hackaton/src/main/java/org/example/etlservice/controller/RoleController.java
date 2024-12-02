package org.example.etlservice.controller;

import org.example.etlservice.model.Role;
import org.example.etlservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/access/{accessId}")
public class RoleController {
    @Autowired
    private RoleService roleService;

    // Create a new role
    @PostMapping("/roles")
    public ResponseEntity<Role> createRole(
            @PathVariable String accessId,
            @RequestBody Role role) {
        Role createdRole = roleService.createRole(role);
        return ResponseEntity.status(201).body(createdRole);
    }

    // Update an existing role
    @PutMapping("/roles/{roleId}")
    public ResponseEntity<Role> updateRole(
            @PathVariable String accessId,
            @PathVariable UUID roleId,
            @RequestBody Role role) {
        Optional<Role> updatedRole = roleService.updateRole(roleId, role);
        return updatedRole.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Delete a role
    @DeleteMapping("/roles/{roleId}")
    public ResponseEntity<Void> deleteRole(
            @PathVariable String accessId,
            @PathVariable UUID roleId) {
        roleService.deleteRole(roleId);
        return ResponseEntity.noContent().build();
    }
}

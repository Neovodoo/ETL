package org.example.etlservice.controller;

import org.example.etlservice.model.Role;
import org.example.etlservice.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/access/{accessId}/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@PathVariable String accessId, @RequestBody Role role) {
        return ResponseEntity.status(201).body(roleService.createRole(accessId, role));
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<Role> updateRole(@PathVariable String accessId, @PathVariable UUID roleId, @RequestBody Role role) {
        return ResponseEntity.ok(roleService.updateRole(accessId, roleId, role));
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable String accessId, @PathVariable UUID roleId) {
        roleService.deleteRole(accessId, roleId);
        return ResponseEntity.noContent().build();
    }
}

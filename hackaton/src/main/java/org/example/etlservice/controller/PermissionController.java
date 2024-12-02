package org.example.etlservice.controller;

import org.example.etlservice.model.Permission;
import org.example.etlservice.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/access/{accessId}/permissions")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @PostMapping
    public ResponseEntity<Permission> createPermission(@PathVariable String accessId, @RequestBody Permission permission) {
        return ResponseEntity.status(201).body(permissionService.createPermission(permission));
    }

    @PutMapping("/{permissionId}")
    public ResponseEntity<Permission> updatePermission(@PathVariable String accessId, @PathVariable UUID permissionId, @RequestBody Permission permission) {
        return ResponseEntity.ok(permissionService.updatePermission(permissionId, permission));
    }

    @DeleteMapping("/{permissionId}")
    public ResponseEntity<Void> deletePermission(@PathVariable String accessId, @PathVariable UUID permissionId) {
        permissionService.deletePermission(permissionId);
        return ResponseEntity.noContent().build();
    }
}

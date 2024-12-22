package org.example.accessservice.controller;

import org.example.accessservice.model.Role;
import org.example.accessservice.model.Permission;
import org.example.accessservice.service.RoleService;
import org.example.accessservice.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        return ResponseEntity.ok(roleService.createRole(role));
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable UUID id) {
        return roleService.getRoleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable UUID id, @RequestBody Role updatedRole) {
        return ResponseEntity.ok(roleService.updateRole(id, updatedRole));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable UUID id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/permissions")
    public ResponseEntity<Role> addPermissionsToRole(@PathVariable UUID id, @RequestBody List<UUID> permissionIds) {
        return roleService.getRoleById(id).map(role -> {
            // Lấy danh sách permissions hiện tại
            List<Permission> existingPermissions = role.getPermissions();

            // Tạo danh sách permissions mới từ permissionIds
            List<Permission> newPermissions = permissionIds.stream()
                    .map(permissionService::getPermissionById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();

            // Hợp nhất danh sách permissions
            newPermissions.stream()
                    .filter(permission -> !existingPermissions.contains(permission))
                    .forEach(existingPermissions::add);

            role.setPermissions(existingPermissions);
            Role updatedRole = roleService.updateRole(id, role);

            return ResponseEntity.ok(updatedRole);
        }).orElse(ResponseEntity.notFound().build());
    }
}

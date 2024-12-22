package org.example.accessservice.service;

import jakarta.transaction.Transactional;
import org.example.accessservice.model.Permission;
import org.example.accessservice.model.Role;
import org.example.accessservice.repository.PermissionRepository;
import org.example.accessservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    @Transactional
    public Role createRole(Role roleData) {
        // Tải danh sách PermissionData từ database
        List<Permission> permissions = roleData.getPermissions()
                .stream()
                .map(permission -> permissionRepository.findById(permission.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Permission not found: " + permission.getId())))
                .collect(Collectors.toList());

        // Gán danh sách PermissionData vào RoleData
        roleData.setPermissions(permissions);

        // Lưu RoleData cùng với mối quan hệ ManyToMany
        return roleRepository.save(roleData);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRoleById(UUID id) {
        return roleRepository.findById(id);
    }

    public Role updateRole(UUID id, Role updatedRole) {
        return roleRepository.findById(id).map(roleData -> {
            roleData.setName(updatedRole.getName());
            roleData.setPermissions(updatedRole.getPermissions());
            return roleRepository.save(roleData);
        }).orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
    }

    public void deleteRole(UUID id) {
        roleRepository.deleteById(id);
    }
}

package org.example.accessservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;
import java.util.List;

@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "role_permission", // Tên bảng join
            joinColumns = @JoinColumn(name = "role_id"), // FK từ Role
            inverseJoinColumns = @JoinColumn(name = "permission_id") // FK từ Permission
    )
    private List<Permission> permissions;
}

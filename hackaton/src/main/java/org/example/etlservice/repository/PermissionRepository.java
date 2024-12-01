package org.example.etlservice.repository;

import org.example.etlservice.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PermissionRepository   extends JpaRepository<Permission, UUID> {
}

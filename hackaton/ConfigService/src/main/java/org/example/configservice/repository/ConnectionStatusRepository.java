package org.example.configservice.repository;

import org.example.configservice.model.ConnectionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ConnectionStatusRepository extends JpaRepository<ConnectionStatus, UUID> {
    Optional<ConnectionStatus> findByConfigurationId(UUID configurationId);
}

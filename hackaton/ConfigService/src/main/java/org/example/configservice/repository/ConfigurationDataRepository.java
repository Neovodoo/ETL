package org.example.configservice.repository;

import org.example.configservice.model.ConfigurationData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConfigurationDataRepository extends JpaRepository<ConfigurationData, UUID> {
}

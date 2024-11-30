package org.example.etlservice.repository;

import org.example.etlservice.model.ETLDescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ETLDescriptionRepository extends JpaRepository<ETLDescription, UUID> {
}

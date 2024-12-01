package org.example.etlservice.repository;

import org.example.etlservice.model.ETLProcess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ETLProcessRepository extends JpaRepository<ETLProcess, UUID> {
}
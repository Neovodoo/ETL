package org.example.etlservice.repository;

import org.example.etlservice.model.ETLProcess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ETLProcessRepository extends JpaRepository<ETLProcess, Long> {
    ETLProcess findByEtlProcessId(String etlProcessId);
}
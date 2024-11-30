package org.example.etlservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class ETLProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String etlProcessId; // Идентификатор ETL процесса
    private String status; // Статус процесса (IN_PROGRESS, COMPLETED, FAILED)
    private String description; // Описание процесса

    @ManyToOne(cascade = CascadeType.ALL)
    private ETLDescription etlDescription; // Описание процесса ETL
}
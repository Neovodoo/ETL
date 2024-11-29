package org.example.etlservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ETLProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String etlProcessId; // Идентификатор ETL процесса
    private String status; // Статус процесса (IN_PROGRESS, COMPLETED, FAILED)
    private String description; // Описание процесса

    @ManyToOne
    private ETLDescription etlDescription; // Описание процесса ETL

    // getters and setters
}
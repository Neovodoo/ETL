package org.example.etlservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class ETLDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description; // Описание процесса

    private String source; // Источник данных
    private String target; // Место назначения данных

    @OneToMany(mappedBy = "etlDescription")
    private List<ETLProcess> etlProcesses;
}

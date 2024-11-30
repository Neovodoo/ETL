package org.example.etlservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Entity
@Data
public class ETLStage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name; // e.g., "extract", "clean", "anonymize"
    private String description;


    @Transient
    private Map<String, Object> parameters; // Parameters specific to each stage

    @ManyToOne
    private ETLDescription etlDescription; // Linking to the ETLDescription
}
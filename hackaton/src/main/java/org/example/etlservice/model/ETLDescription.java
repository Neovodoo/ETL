package org.example.etlservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class ETLDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;

    @OneToMany(mappedBy = "etlDescription", cascade = CascadeType.ALL)  // Cascade saving of ETLProcess
    private List<ETLProcess> etlProcesses;

    @OneToMany(mappedBy = "etlDescription", cascade = CascadeType.ALL) // Cascade saving of ETLStage
    private List<ETLStage> stages = new ArrayList<>(); // Initialize the stages list

}

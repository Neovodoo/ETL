package org.example.etlservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Map;
import java.util.UUID;

@Data
@Entity
@ToString(exclude = "etlProcess")
public class ETLStage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String description;

    @Transient
    private Map<String, Object> parameters;

    @ManyToOne
    @JoinColumn(name = "etl_process_id", referencedColumnName = "id")
    private ETLProcess etlProcess;
}




package org.example.etlservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@ToString(exclude = "stages")
public class ETLProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String status;

    @ManyToOne(cascade = CascadeType.ALL)
    private ETLDescription etlDescription;

    @OneToMany(mappedBy = "etlProcess", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ETLStage> stages = new ArrayList<>();
}



package org.example.etlservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;
import java.util.List;


@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private List<UUID> permissions;

    // Getters and Setters
}

package org.example.etlservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;
import java.util.List;


@Entity
@Data
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ElementCollection
    private List<String> permissions;

    // Getters and Setters
}

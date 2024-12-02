package org.example.etlservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;


@Entity
@Data
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    // Getters and Setters
}

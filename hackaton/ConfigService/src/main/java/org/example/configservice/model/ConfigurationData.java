package org.example.configservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public abstract class ConfigurationData {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
}
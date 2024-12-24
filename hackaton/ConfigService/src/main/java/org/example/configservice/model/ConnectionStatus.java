package org.example.configservice.model;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;
@Data
@Entity
public class ConnectionStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String status;

    private UUID configurationId; // Store UUID instead of ConfigurationData reference

    public ConnectionStatus(UUID configurationId, String status)
    {
        this.status = status;
        this.configurationId = configurationId;
    }
}


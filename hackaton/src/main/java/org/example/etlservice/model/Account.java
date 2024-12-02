package org.example.etlservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String token;

    @Column(name = "role_id")
    private UUID roleId; // Reference to the Role entity
}

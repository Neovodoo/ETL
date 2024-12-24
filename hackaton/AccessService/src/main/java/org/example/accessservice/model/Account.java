package org.example.accessservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String token;
    private String name;
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
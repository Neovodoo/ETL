package org.example.accessservice.model;

import jakarta.persistence.*;

import lombok.Data;
import java.util.UUID;


@Data
@Entity
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
}

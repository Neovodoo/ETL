package org.example.configservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

import java.util.UUID;

@Data
@Entity
public class DatabaseConfig extends ConfigurationData {
    @ElementCollection
    private List<String> pathfordatabaseUrl;
    @ElementCollection
    private List<String> pathfordatabaseLogin;
    @ElementCollection
    private List<String> pathfordatabasePassword;
    public DatabaseConfig() {};
    public DatabaseConfig(List<String> url, List<String> login, List<String> password)
    {
        this.pathfordatabaseUrl = url;
        this.pathfordatabaseLogin = login;
        this.pathfordatabasePassword = password;
    }
}

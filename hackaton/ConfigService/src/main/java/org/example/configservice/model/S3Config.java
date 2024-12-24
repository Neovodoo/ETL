package org.example.configservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

import java.util.UUID;

@Data
@Entity
public class S3Config extends ConfigurationData {
    @ElementCollection
    private List<String> pathforS3AccessData;
    @ElementCollection
    private List<String> pathforS3User;
    @ElementCollection
    private List<String> pathforS3Password;
    public S3Config() {};
    public S3Config(List<String> acessData, List<String> user, List<String> password)
    {
        this.pathforS3AccessData = acessData;
        this.pathforS3User = user;
        this.pathforS3Password = password;
    }
}

package org.example.configservice.model;

import lombok.Data;

import java.util.Map;

@Data
public class ConfigRequest {
    private String type;
    private Map<String, Object> configFile;
}
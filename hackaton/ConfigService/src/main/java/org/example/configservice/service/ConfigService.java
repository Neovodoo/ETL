package org.example.configservice.service;

import org.example.configservice.model.*;
import org.example.configservice.repository.ConfigurationDataRepository;
import org.example.configservice.repository.ConnectionStatusRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ConfigService {

    @Autowired
    private ConfigurationDataRepository configurationDataRepository;

    @Autowired
    private ConnectionStatusRepository connectionStatusRepository;

    // Create a Configuration Entity
    public ConfigurationData createConfigEntity(String type, Map<String, Object> configFile) {
        ConfigurationData configData;

        if (type.equalsIgnoreCase("DATABASE")) {
            List<String> urls = (List<String>) configFile.get("urls");
            List<String> logins = (List<String>) configFile.get("logins");
            List<String> passwords = (List<String>) configFile.get("passwords");
            configData = new DatabaseConfig(urls, logins, passwords);

        } else if (type.equalsIgnoreCase("S3")) {
            List<String> accessKeys = (List<String>) configFile.get("accessKeys");
            List<String> secretKeys = (List<String>) configFile.get("secretKeys");
            List<String> bucketNames = (List<String>) configFile.get("bucketNames");
            configData = new S3Config(accessKeys, secretKeys, bucketNames);

        } else {
            throw new IllegalArgumentException("Unsupported config type: " + type);
        }

        return configurationDataRepository.save(configData);
    }


    // Delete a Configuration Entity
    public void deleteConfigEntity(UUID id) {
        configurationDataRepository.deleteById(id);
    }

    // Get Configuration by ID
    public ConfigurationData getConfigEntity(UUID id) {
        return configurationDataRepository.findById(id).orElse(null);
    }

    public List<ConfigurationData> getAllConfigurations() {
        return configurationDataRepository.findAll();
    }
    // Create a Connection Status
    public ConnectionStatus createConnection(UUID configurationId) {
        ConfigurationData configData = configurationDataRepository.findById(configurationId)
                .orElseThrow(() -> new IllegalArgumentException("Configuration not found for ID: " + configurationId));

        // Simulate creating a connection (this is just a placeholder for real logic)
        String status = "CONNECTED";

        ConnectionStatus connectionStatus = new ConnectionStatus(configurationId, status);
        return connectionStatusRepository.save(connectionStatus);
    }

    // Get Connection Status
    public ConnectionStatus getConnectionStatus(UUID id) {
        return connectionStatusRepository.findById(id).orElse(null);
    }

    // Return All Connection Statuses
    public List<ConnectionStatus> returnConnectionStatus() {
        return connectionStatusRepository.findAll();
    }

    // Put Connection to Service
    public void putConnectionToService(UUID configurationId) {
        ConfigurationData configData = configurationDataRepository.findById(configurationId)
                .orElseThrow(() -> new IllegalArgumentException("Configuration not found for ID: " + configurationId));

        // Simulate using the configuration for a service
        System.out.println("Using configuration with ID: " + configurationId);
    }
    public ConnectionStatus getStatusByConfigId(UUID configurationId) {
        return connectionStatusRepository.findByConfigurationId(configurationId)
                .orElseThrow(() -> new IllegalArgumentException("No connection status found for configuration ID: " + configurationId));
    }

}

package org.example.configservice.controller;

import org.example.configservice.model.ConfigRequest;
import org.example.configservice.model.ConnectionStatus;
import org.example.configservice.model.ConfigurationData;
import org.example.configservice.service.ConfigService;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/configurations")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @PostMapping
    public ResponseEntity<ConfigurationData> createConfig(@RequestBody ConfigRequest configRequest) {
        ConfigurationData configData = configService.createConfigEntity(
                configRequest.getType(),
                configRequest.getConfigFile()
        );
        return ResponseEntity.ok(configData);
    }

    @DeleteMapping("/{id}")
    public void deleteConfig(@PathVariable UUID id) {
        configService.deleteConfigEntity(id);
    }

    @GetMapping("/{id}")
    public ConfigurationData getConfig(@PathVariable UUID id) {
        return configService.getConfigEntity(id);
    }

    @PostMapping("/connect/{id}")
    public ResponseEntity<ConnectionStatus> createConnection(@PathVariable UUID id) {
        ConnectionStatus connectionStatus = configService.createConnection(id);
        return ResponseEntity.ok(connectionStatus);
    }

    @GetMapping("/status")
    public List<ConnectionStatus> getAllStatuses() {
        return configService.returnConnectionStatus();
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<ConnectionStatus> getStatusByConfigId(@PathVariable UUID id) {
        ConnectionStatus connectionStatus = configService.getStatusByConfigId(id);
        return ResponseEntity.ok(connectionStatus);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ConfigurationData>> getAllConfigurations() {
        List<ConfigurationData> configurations = configService.getAllConfigurations();
        return ResponseEntity.ok(configurations);
    }

    @PostMapping("/put-to-service/{id}")
    public ResponseEntity<Void> putConnectionToService(@PathVariable UUID id) {
        configService.putConnectionToService(id);
        return ResponseEntity.ok().build();
    }
}

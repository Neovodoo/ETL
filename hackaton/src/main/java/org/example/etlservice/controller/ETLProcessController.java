package org.example.etlservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.etlservice.model.ETLDescription;
import org.example.etlservice.service.ETLProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/etl-process")
public class ETLProcessController {

    @Autowired
    private ETLProcessService etlProcessService;

    // Запуск ETL процесса
    @PostMapping("/run")
    public ResponseEntity<String> runETLProcess(@RequestBody Map<String, Object> requestBody) {
        // Extract etl_process from the request body
        Map<String, Object> etlProcessData = (Map<String, Object>) requestBody.get("etl_process");

        // Deserialize the etl_process part into ETLDescription
        ETLDescription etlDescription = new ObjectMapper().convertValue(etlProcessData, ETLDescription.class);

        etlDescription.setDescription(requestBody.get("etl_process").toString());

        if (etlDescription == null || etlDescription.getStages() == null) {
            return ResponseEntity.badRequest().body("ETL Description or stages are missing.");
        }

        String processId = etlProcessService.runETLProcess(etlDescription);
        return ResponseEntity.status(HttpStatus.CREATED).body("ETL Process started with ID: " + processId);
    }


    // Завершение ETL процесса
    @PostMapping("/terminate/{etlProcessId}")
    public ResponseEntity<String> terminateETLProcess(@PathVariable String etlProcessId) {
        etlProcessService.terminateETLProcess(etlProcessId);
        return ResponseEntity.ok("ETL Process " + etlProcessId + " terminated.");
    }

    // Перезапуск ETL процесса
    @PostMapping("/restart/{etlProcessId}")
    public ResponseEntity<String> restartETLProcess(@PathVariable String etlProcessId, @RequestBody ETLDescription newEtlDescription) {
        String newProcessId = etlProcessService.restartETLProcess(etlProcessId, newEtlDescription);
        return ResponseEntity.ok("ETL Process " + etlProcessId + " restarted with new ID: " + newProcessId);
    }
}

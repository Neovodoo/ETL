package org.example.etlservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.etlservice.model.ETLDescription;
import org.example.etlservice.model.ETLStage;
import org.example.etlservice.service.ETLProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/etl-process")
public class ETLProcessController {

    @Autowired
    private ETLProcessService etlProcessService;

    // Запуск ETL процесса
    @PostMapping("/run")
    public ResponseEntity<String> runETLProcess(@RequestBody Map<String, Object> requestBody) {
        try {
            // Serialize the full request body to a JSON string
            ObjectMapper objectMapper = new ObjectMapper();
            String fullRequestBody = objectMapper.writeValueAsString(requestBody);

            // Extract "etl_process" object
            Map<String, Object> etlProcessData = (Map<String, Object>) requestBody.get("etl_process");

            // Create ETLDescription and set the JSON string as its description
            String description = (String) etlProcessData.get("description");
            ETLDescription etlDescription = new ETLDescription();
            etlDescription.setDescription(fullRequestBody);

            // Extract and deserialize "stages"
            List<Map<String, Object>> stagesData = (List<Map<String, Object>>) etlProcessData.get("stages");
            List<ETLStage> stages = new ArrayList<>();
            for (Map<String, Object> stageData : stagesData) {
                ETLStage stage = objectMapper.convertValue(stageData, ETLStage.class);
                stages.add(stage);
            }

            // Pass ETLDescription and stages to the service layer
            String processId = etlProcessService.runETLProcess(etlDescription, stages);
            return ResponseEntity.status(HttpStatus.CREATED).body("ETL Process started with ID: " + processId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing ETL request: " + e.getMessage());
        }
    }

    // Завершение ETL процесса
    @DeleteMapping("/terminate/{etlProcessId}")
    public ResponseEntity<String> terminateETLProcess(@PathVariable String etlProcessId) {
        etlProcessService.terminateETLProcess(etlProcessId);
        return ResponseEntity.ok("ETL Process " + etlProcessId + " terminated.");
    }

    // Перезапуск ETL процесса
    @PutMapping("etl-description/{descriptionId}/restart/{processId}")
    public ResponseEntity<String> restartETLProcess(@PathVariable UUID descriptionId, @PathVariable UUID processId) {
        try {
            String newProcessId = etlProcessService.restartETLProcess(descriptionId, processId);
            return ResponseEntity.status(HttpStatus.OK).body("ETL Process restarted successfully with ID: " + newProcessId);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

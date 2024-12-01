package org.example.etlservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;
import org.example.etlservice.model.ETLDescription;
import org.example.etlservice.model.ETLProcess;
import org.example.etlservice.model.ETLStage;
import org.example.etlservice.repository.ETLProcessRepository;
import org.example.etlservice.repository.ETLDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.*;
import java.util.*;

@Service
public class ETLProcessService {

    @Autowired
    private ETLProcessRepository etlProcessRepository;

    @Autowired
    private ETLDescriptionRepository etlDescriptionRepository;

    public String runETLProcess(ETLDescription etlDescription, List<ETLStage> descriptionStages) {
        // Persist the ETLDescription
        etlDescriptionRepository.save(etlDescription);

        // Create and persist a new ETLProcess
        ETLProcess etlProcess = new ETLProcess();
        etlProcess.setEtlDescription(etlDescription); // Link description
        etlProcess.setStatus("IN_PROGRESS");
        etlProcessRepository.save(etlProcess); // Save ETLProcess to get its ID

        // Link each stage to the ETLProcess and persist
        for (ETLStage stage : descriptionStages) {
            stage.setEtlProcess(etlProcess); // Link stage to the process
        }
        etlProcess.getStages().addAll(descriptionStages);

        // Save the process with the linked stages
        etlProcessRepository.save(etlProcess);

        // Execute stages
        List<Map<String, Object>> data = null; // Initialize as null for the first stage
        for (ETLStage stage : descriptionStages) {
            if (etlProcess.getStatus().equals("IN_PROGRESS")) data = executeStage(stage, data);
        }

        // Update process status
        etlProcess.setStatus("COMPLETED");
        etlProcessRepository.save(etlProcess);

        return etlProcess.getId().toString();
    }


    // Execute a single stage
    public List<Map<String, Object>> executeStage(ETLStage stage, List<Map<String, Object>> inputData) {
        List<Map<String, Object>> data = inputData;
        System.out.println("EXECUTING " + stage);
        switch (stage.getName().toLowerCase()) {
            case "extract":
                data = handleExtract(stage);
                break;
            case "clean":
                handleClean(stage);
                break;
            case "anonymize":
                handleAnonymize(stage);
                break;
            case "transform":
                data = handleTransform(data, stage);
                break;
            case "merge":
                handleMerge(stage);
                break;
            case "output":
                handleOutput(data, stage);
                break;
            default:
                throw new UnsupportedOperationException("Unknown stage: " + stage.getName());
        }

        return data;
    }

    // Implement the 'extract' logic
    public List<Map<String, Object>> handleExtract(ETLStage stage) {
        Map<String, Object> params = stage.getParameters();
        String sourceType = (String) params.get("source_type");

        List<Map<String, Object>> extractedData = new ArrayList<>();

        if ("database".equals(sourceType)) {
            Map<String, String> sourceDetails = (Map<String, String>) params.get("source_details");
            String dbUrl = sourceDetails.get("db_url");
            String username = sourceDetails.get("username");
            String password = sourceDetails.get("password");
            String query = sourceDetails.get("query");

            try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    ResultSetMetaData metaData = rs.getMetaData();
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        row.put(metaData.getColumnName(i), rs.getObject(i));
                    }
                    extractedData.add(row);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Error extracting data from the database", e);
            }
        }

        return extractedData;
    }

    // Implement the 'transform' logic
    public List<Map<String, Object>> handleTransform(List<Map<String, Object>> data, ETLStage stage) {
        Map<String, Object> params = stage.getParameters();
        List<Map<String, Object>> transformedData = new ArrayList<>(data);

        List<Map<String, Object>> transformations = (List<Map<String, Object>>) params.get("transformations");
        for (Map<String, Object> transformation : transformations) {
            String column = (String) transformation.get("column");
            String operation = (String) transformation.get("operation");

            for (Map<String, Object> row : transformedData) {
                if ("uppercase".equals(operation)) {
                    Object value = row.get(column);
                    if (value != null) {
                        row.put(column, value.toString().toUpperCase());
                    }
                }
            }
        }

        return transformedData;
    }

    public void handleOutput(List<Map<String, Object>> data, ETLStage stage) {
        Map<String, Object> params = stage.getParameters();
        String outputType = (String) params.get("output_type");

        if ("csv".equals(outputType)) {
            String outputLocation = (String) params.get("output_location");
            boolean overwriteExisting = (Boolean) params.get("overwrite_existing");

            File outputFile = new File(outputLocation);
            if (outputFile.exists() && !overwriteExisting) {
                throw new RuntimeException("Output file already exists and 'overwrite_existing' is false.");
            }

            try (CSVWriter writer = new CSVWriter(new FileWriter(outputFile))) {
                if (!data.isEmpty()) {
                    Set<String> headers = data.get(0).keySet();
                    writer.writeNext(headers.toArray(new String[0]));

                    for (Map<String, Object> row : data) {
                        String[] rowData = headers.stream().map(header -> String.valueOf(row.get(header))).toArray(String[]::new);
                        writer.writeNext(rowData);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error writing output to CSV file", e);
            }
        }
    }

    // Implement the 'clean' logic (to be completed as per business rules)
    private void handleClean(ETLStage stage) {
        // Implement cleaning logic based on the provided parameters
    }

    // Implement the 'anonymize' logic (to be completed as per business rules)
    private void handleAnonymize(ETLStage stage) {
        // Implement anonymizing logic based on the provided parameters
    }

    // Implement the 'merge' logic (to be completed as per business rules)
    private void handleMerge(ETLStage stage) {
        // Implement merging logic based on the provided parameters
    }

    // Terminate the ETL process
    public void terminateETLProcess(String etlProcessId) {
        // Find the ETLProcess by ID
        Optional<ETLProcess> optionalETLProcess = etlProcessRepository.findById(UUID.fromString(etlProcessId));

        if (optionalETLProcess.isPresent()) {
            ETLProcess etlProcess = optionalETLProcess.get();

            // Check if the status is not "COMPLETED"
            if (!"COMPLETED".equalsIgnoreCase(etlProcess.getStatus())) {
                // Update the status to "TERMINATED"
                etlProcess.setStatus("TERMINATED");
                etlProcessRepository.save(etlProcess);
                System.out.println("ETL Process with ID " + etlProcessId + " has been terminated.");
            } else {
                System.out.println("ETL Process with ID " + etlProcessId + " is already completed. Cannot terminate.");
            }
        } else {
            throw new RuntimeException("ETL Process with ID " + etlProcessId + " not found.");
        }
    }

//     Restart ETL process
public String restartETLProcess(UUID etlDescriptionId, UUID processId) {
    // Step 1: Terminate the existing process
    Optional<ETLProcess> optionalProcess = etlProcessRepository.findById(processId);

    if (optionalProcess.isPresent()) {
        ETLProcess processToTerminate = optionalProcess.get();
        if (!"COMPLETED".equalsIgnoreCase(processToTerminate.getStatus())) {
            processToTerminate.setStatus("TERMINATED");
            etlProcessRepository.save(processToTerminate);
            System.out.println("Process was TERMINATED");
        }
    } else {
        throw new RuntimeException("ETL Process with ID " + processId + " not found.");
    }

    // Step 2: Fetch the description by ID
    Optional<ETLDescription> optionalDescription = etlDescriptionRepository.findById(etlDescriptionId);
    if (!optionalDescription.isPresent()) {
        throw new RuntimeException("ETL Description with ID " + etlDescriptionId + " not found.");
    }
    ETLDescription etlDescription = optionalDescription.get();

    // Step 3: Recreate stages from the description
    List<ETLStage> stagesFromDescription = extractStagesFromDescription(etlDescription); // Implement this method

    if (stagesFromDescription.isEmpty()) {
        throw new RuntimeException("No stages found for ETL Description with ID " + etlDescriptionId);
    }

    // Step 4: Create and run a new process
    ETLProcess newProcess = new ETLProcess();
    newProcess.setEtlDescription(etlDescription);
    newProcess.setStatus("IN_PROGRESS");
    etlProcessRepository.save(newProcess);

    // Link the stages to the new process
    for (ETLStage stage : stagesFromDescription) {
        stage.setEtlProcess(newProcess); // Link stage to the new process
    }
    newProcess.getStages().addAll(stagesFromDescription);
    etlProcessRepository.save(newProcess); // Save the updated process with stages

    // Step 5: Run the process stages
    List<Map<String, Object>> data = null; // Initialize input data
    List<ETLStage> stages = new ArrayList<>(stagesFromDescription);
    System.out.println("CURRENT STAGES: " + stages);
    for (ETLStage stage : stages) {
        data = executeStage(stage, data); // Pass the output of one stage to the next
    }

    // Step 6: Mark the new process as completed
    newProcess.setStatus("COMPLETED");
    etlProcessRepository.save(newProcess);

    return newProcess.getId().toString();
}

    private List<ETLStage> extractStagesFromDescription(ETLDescription etlDescription) {
        // Assume the description contains a JSON body with stage information
        List<ETLStage> stages = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> descriptionMap = objectMapper.readValue(etlDescription.getDescription(), Map.class);
            List<Map<String, Object>> stagesData = (List<Map<String, Object>>) ((Map<String, Object>) descriptionMap.get("etl_process")).get("stages");

            for (Map<String, Object> stageData : stagesData) {
                ETLStage stage = objectMapper.convertValue(stageData, ETLStage.class);
                stages.add(stage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error extracting stages from ETL description: " + e.getMessage());
        }
        return stages;
    }

}

package org.example.etlservice.service;

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

    // Run ETL process
    public String runETLProcess(ETLDescription etlDescription) {
        ETLProcess etlProcess = new ETLProcess();
        etlProcess.setEtlDescription(etlDescription);
        etlProcess.setStatus("IN_PROGRESS");
        etlProcess.setEtlProcessId(generateProcessId());
        etlProcessRepository.save(etlProcess);

        System.out.println(etlDescription.toString());

        // Loop through each stage and execute
        List<ETLStage> stages = etlDescription.getStages();
        System.out.println(etlDescription.getStages().toString());
        List<Map<String, Object>> data = null;

        for (ETLStage stage : stages) {
            data = executeStage(stage, data);
        }

        etlProcess.setStatus("COMPLETED");
        etlProcessRepository.save(etlProcess);

        return etlProcess.getEtlProcessId();
    }

    // Execute a single stage
    public List<Map<String, Object>> executeStage(ETLStage stage, List<Map<String, Object>> inputData) {
        List<Map<String, Object>> data = inputData;

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

            // Establish JDBC connection and retrieve data
            try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                // Process result set into a list of maps
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    // Assuming columns are named as per the result set
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
    public List<Map<String, Object>> handleTransform(List<Map<String, Object>> extractedData, ETLStage stage) {
        Map<String, Object> params = stage.getParameters();
        List<Map<String, Object>> transformedData = new ArrayList<>(extractedData);

        List<Map<String, Object>> transformations = (List<Map<String, Object>>) params.get("transformations");
        for (Map<String, Object> transformation : transformations) {
            String column = (String) transformation.get("column");
            String operation = (String) transformation.get("operation");

            for (Map<String, Object> row : transformedData) {
                if ("uppercase".equals(operation)) {
                    Object value = row.get(column);
                    System.out.println(value.toString());
                    if (value != null) {
                        row.put(column, value.toString().toUpperCase());
                    }
                }
                // Handle other transformations (e.g., 'age_calculation', etc.)
            }
        }

        return transformedData;
    }

    public void handleOutput(List<Map<String, Object>> transformedData, ETLStage stage) {
        Map<String, Object> params = stage.getParameters();
        System.out.println("HERE WE ARE TRY TO CREATE FILE ");

        String outputType = (String) params.get("output_type");
        if ("csv".equals(outputType)) {
            String outputLocation = (String) params.get("output_location");
            boolean overwriteExisting = (Boolean) params.get("overwrite_existing");

            // Create the output file
            File outputFile = new File(outputLocation);
            if (outputFile.exists() && !overwriteExisting) {
                throw new RuntimeException("Output file already exists and 'overwrite_existing' is false.");
            }

            try (CSVWriter writer = new CSVWriter(new FileWriter(outputFile))) {
                if (!transformedData.isEmpty()) {
                    // Get headers from the first row
                    Set<String> headers = transformedData.get(0).keySet();
                    String[] headerArray = headers.toArray(new String[0]);

                    // Write header row to the CSV file
                    writer.writeNext(headerArray);

                    // Write data rows to the CSV file
                    for (Map<String, Object> row : transformedData) {
                        String[] rowData = new String[headerArray.length];
                        int i = 0;
                        for (String header : headerArray) {
                            rowData[i++] = String.valueOf(row.get(header));
                        }
                        writer.writeNext(rowData);
                    }

                    // Log the written CSV data to the console
                    logCsvToConsole(outputFile);

                    System.out.println("CSV file written to: " + outputLocation);
                } else {
                    System.out.println("No data to write.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error writing output to CSV file", e);
            }
        }
    }

    // Helper method to log the contents of the CSV file to the console
    private void logCsvToConsole(File outputFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            String line;
            System.out.println("CSV File Content:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);  // Print each line of the CSV file to the console
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading the CSV file for logging", e);
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

    // Generate unique process ID
    private String generateProcessId() {
        return "ETL-" + System.currentTimeMillis();
    }

    // Terminate the ETL process
    public void terminateETLProcess(String etlProcessId) {
        ETLProcess etlProcess = etlProcessRepository.findByEtlProcessId(etlProcessId);
        if (etlProcess != null) {
            etlProcess.setStatus("COMPLETED");
            etlProcessRepository.save(etlProcess);
        }
    }

    // Restart ETL process
    public String restartETLProcess(String etlProcessId, ETLDescription newEtlDescription) {
        ETLProcess oldProcess = etlProcessRepository.findByEtlProcessId(etlProcessId);
        if (oldProcess != null) {
            ETLProcess newProcess = new ETLProcess();
            newProcess.setEtlDescription(newEtlDescription);
            newProcess.setStatus("IN_PROGRESS");
            newProcess.setEtlProcessId(generateProcessId());
            etlProcessRepository.save(newProcess);

            // Execute stages of the new process
            List<ETLStage> stages = newEtlDescription.getStages();
            for (ETLStage stage : stages) {
                executeStage(stage, null); // Pass null initially, data will be fetched in first stages
            }

            newProcess.setStatus("COMPLETED");
            etlProcessRepository.save(newProcess);

            return newProcess.getEtlProcessId();
        }
        return null;
    }
}

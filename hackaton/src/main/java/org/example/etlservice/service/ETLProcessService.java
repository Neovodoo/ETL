package org.example.etlservice.service;

import org.example.etlservice.model.ETLDescription;
import org.example.etlservice.model.ETLProcess;
import org.example.etlservice.repository.ETLProcessRepository;
import org.example.etlservice.repository.ETLDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ETLProcessService {

    @Autowired
    private ETLProcessRepository etlProcessRepository;

    @Autowired
    private ETLDescriptionRepository etlDescriptionRepository;

    // Запуск ETL процесса
    public String runETLProcess(ETLDescription etlDescription) {
        ETLProcess etlProcess = new ETLProcess();
        etlProcess.setEtlDescription(etlDescription);
        etlProcess.setStatus("IN_PROGRESS");
        etlProcess.setEtlProcessId(generateProcessId());
        etlProcessRepository.save(etlProcess);

        // Логика для запуска ETL процесса
        // Например: экстракция, трансформация, загрузка данных

        etlProcess.setStatus("COMPLETED");
        etlProcessRepository.save(etlProcess);

        return etlProcess.getEtlProcessId();
    }

    // Завершение ETL процесса
    public void terminateETLProcess(String etlProcessId) {
        ETLProcess etlProcess = etlProcessRepository.findByEtlProcessId(etlProcessId);
        if (etlProcess != null) {
            etlProcess.setStatus("COMPLETED");
            etlProcessRepository.save(etlProcess);
        }
    }

    // Перезапуск ETL процесса с новым описанием
    public String restartETLProcess(String etlProcessId, ETLDescription newEtlDescription) {
        ETLProcess oldProcess = etlProcessRepository.findByEtlProcessId(etlProcessId);
        if (oldProcess != null) {
            ETLProcess newProcess = new ETLProcess();
            newProcess.setEtlDescription(newEtlDescription);
            newProcess.setStatus("IN_PROGRESS");
            newProcess.setEtlProcessId(generateProcessId());
            etlProcessRepository.save(newProcess);

            // Логика для перезапуска процесса

            newProcess.setStatus("COMPLETED");
            etlProcessRepository.save(newProcess);

            return newProcess.getEtlProcessId();
        }
        return null;
    }

    // Генерация уникального ID для ETL процесса
    private String generateProcessId() {
        return "ETL-" + System.currentTimeMillis();
    }
}

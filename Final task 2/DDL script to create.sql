-- Создание таблицы EtlDescription
CREATE TABLE EtlDescription (
    idEtlDescription SERIAL PRIMARY KEY,
    extractionParameters JSON,
    cleanseParameters JSON,
    anonymizationParameters JSON,
    transformationParameters JSON,
    mergeParameters JSON,
    outputParameters JSON
);

-- Создание таблицы EtlProcess
CREATE TABLE EtlProcess (
    idEtlProcess SERIAL PRIMARY KEY,
    name VARCHAR(2000) NOT NULL,
    EtlDescription_idEtlDescription INT,
    Data_idData INT,
    FOREIGN KEY (EtlDescription_idEtlDescription) REFERENCES EtlDescription (idEtlDescription) ON DELETE SET NULL,
    FOREIGN KEY (Data_idData) REFERENCES Data (idData) ON DELETE SET NULL
);

-- Создание таблицы Data
CREATE TABLE Data (
    idData SERIAL PRIMARY KEY,
    extractedData JSON,
    cleansedData JSON,
    anonymizedData JSON,
    transformedData JSON,
    mergedData JSON
);

-- Создание таблицы EtlStages
CREATE TABLE EtlStages (
    idEtlStages SERIAL PRIMARY KEY,
    name VARCHAR(4500) NOT NULL,
    EtlProcess_idEtlProcess INT,
    Data_idData INT,
    FOREIGN KEY (EtlProcess_idEtlProcess) REFERENCES EtlProcess (idEtlProcess) ON DELETE CASCADE,
    FOREIGN KEY (Data_idData) REFERENCES Data (idData) ON DELETE CASCADE
);

-- Создание таблицы run
CREATE TABLE run (
    idrun SERIAL PRIMARY KEY,
    extractionStageInfo TEXT,
    cleanseStageInfo TEXT,
    anonymizationStageInfo TEXT,
    transformationStageInfo TEXT,
    mergeStageInfo TEXT,
    outputStageInfo TEXT
);

-- Создание таблицы Monitoring
CREATE TABLE Monitoring (
    idMonitoring SERIAL PRIMARY KEY,
    actor INT NOT NULL,
    logs TEXT,
    run_idrun INT,
    FOREIGN KEY (run_idrun) REFERENCES run (idrun) ON DELETE CASCADE
);

-- Создание таблицы Report
CREATE TABLE Report (
    idReport SERIAL PRIMARY KEY,
    Detailed_Report_idDetailed_Report INT,
    Overall_Report_idOverall_Report INT,
    Monitoring_idMonitoring INT,
    FOREIGN KEY (Detailed_Report_idDetailed_Report) REFERENCES Detailed_Report (idDetailed_Report) ON DELETE SET NULL,
    FOREIGN KEY (Overall_Report_idOverall_Report) REFERENCES Overall_Report (idOverall_Report) ON DELETE SET NULL,
    FOREIGN KEY (Monitoring_idMonitoring) REFERENCES Monitoring (idMonitoring) ON DELETE CASCADE
);

-- Создание таблицы Detailed_Report
CREATE TABLE Detailed_Report (
    idDetailed_Report SERIAL PRIMARY KEY,
    extractionReport TEXT,
    cleanseReport TEXT,
    anonymizationReport TEXT,
    TransformationReport TEXT,
    mergeReport TEXT,
    outputReport TEXT
);

-- Создание таблицы Overall_Report
CREATE TABLE Overall_Report (
    idOverall_Report SERIAL PRIMARY KEY,
    Report TEXT
);

-- Создание таблицы account_data
CREATE TABLE account_data (
    idAccount SERIAL PRIMARY KEY,
    token VARCHAR(450) NOT NULL,
    name VARCHAR(450) NOT NULL,
    Role_Data_idRole INT,
    Access_idAccess INT
);

-- Создание таблицы role_data
CREATE TABLE role_data (
    idRole SERIAL PRIMARY KEY,
    name VARCHAR(450) NOT NULL,
    Access_idAccess INT
);

-- Создание таблицы permission_data
CREATE TABLE permission_data (
    idPermission SERIAL PRIMARY KEY,
    name VARCHAR(450) NOT NULL,
    Access_idAccess INT
);

-- Создание таблицы role_has_permissions
CREATE TABLE role_has_permissions (
    Role_Data_idRole INT NOT NULL,
    Permission_Data_idPermission INT NOT NULL,
    PRIMARY KEY (Role_Data_idRole, Permission_Data_idPermission),
    FOREIGN KEY (Role_Data_idRole) REFERENCES role_data (idRole) ON DELETE CASCADE,
    FOREIGN KEY (Permission_Data_idPermission) REFERENCES permission_data (idPermission) ON DELETE CASCADE
);

-- Добавление внешних ключей для account_data
ALTER TABLE account_data
ADD CONSTRAINT fk_role_data FOREIGN KEY (Role_Data_idRole)
REFERENCES role_data (idRole) ON DELETE SET NULL;

ALTER TABLE account_data
ADD CONSTRAINT fk_access_data FOREIGN KEY (Access_idAccess)
REFERENCES permission_data (idPermission) ON DELETE SET NULL;

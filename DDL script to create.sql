-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema etlService
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema etlService
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `etlService` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema test
-- -----------------------------------------------------
USE `etlService` ;

-- -----------------------------------------------------
-- Table `etlService`.`EtlDescription`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `etlService`.`EtlDescription` (
  `idEtlDescription` INT NOT NULL,
  `extractionParameters` JSON NOT NULL,
  `cleanseParameters` JSON NULL,
  `anonymizationParameters` JSON NULL,
  `transformationParameters` JSON NULL,
  `mergeParameters` JSON NULL,
  `outputParameters` JSON NOT NULL,
  PRIMARY KEY (`idEtlDescription`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `etlService`.`Data`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `etlService`.`Data` (
  `idData` INT NOT NULL,
  `extractededData` JSON NOT NULL,
  `cleansedData` JSON NULL,
  `anonymizedData` JSON NULL,
  `transformedData` JSON NULL,
  `mergedData` JSON NULL,
  PRIMARY KEY (`idData`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `etlService`.`EtlProcess`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `etlService`.`EtlProcess` (
  `idEtlProcess` INT NOT NULL,
  `name` VARCHAR(2000) NOT NULL,
  `EtlDescription_idEtlDescription` INT NOT NULL,
  `Data_idData` INT NOT NULL,
  PRIMARY KEY (`idEtlProcess`),
  INDEX `fk_EtlProcess_EtlDescription_idx` (`EtlDescription_idEtlDescription` ASC) VISIBLE,
  INDEX `fk_EtlProcess_Data1_idx` (`Data_idData` ASC) VISIBLE,
  CONSTRAINT `fk_EtlProcess_EtlDescription`
    FOREIGN KEY (`EtlDescription_idEtlDescription`)
    REFERENCES `etlService`.`EtlDescription` (`idEtlDescription`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_EtlProcess_Data1`
    FOREIGN KEY (`Data_idData`)
    REFERENCES `etlService`.`Data` (`idData`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `etlService`.`EtlStages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `etlService`.`EtlStages` (
  `idEtlStages` INT NOT NULL,
  `name` VARCHAR(4500) NOT NULL,
  `EtlProcess_idEtlProcess` INT NOT NULL,
  `Data_idData` INT NOT NULL,
  PRIMARY KEY (`idEtlStages`),
  INDEX `fk_EtlStages_EtlProcess1_idx` (`EtlProcess_idEtlProcess` ASC) VISIBLE,
  INDEX `fk_EtlStages_Data1_idx` (`Data_idData` ASC) VISIBLE,
  CONSTRAINT `fk_EtlStages_EtlProcess1`
    FOREIGN KEY (`EtlProcess_idEtlProcess`)
    REFERENCES `etlService`.`EtlProcess` (`idEtlProcess`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_EtlStages_Data1`
    FOREIGN KEY (`Data_idData`)
    REFERENCES `etlService`.`Data` (`idData`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `etlService`.`run`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `etlService`.`run` (
  `idrun` INT NOT NULL,
  `extractionStageInfo` TEXT(40000) NOT NULL,
  `cleranseStageInfo` TEXT(40000) NULL,
  `anonymizationStageInfo` TEXT(40000) NULL,
  `transformationStageInfo` TEXT(40000) NULL,
  `mergeStageInfo` TEXT(40000) NULL,
  `outputStageInfo` TEXT(40000) NULL,
  PRIMARY KEY (`idrun`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `etlService`.`Detailed Report`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `etlService`.`Detailed Report` (
  `idDetailed Report` INT NOT NULL,
  `extractionReport` TEXT(40000) NOT NULL,
  `cleanseReport` TEXT(40000) NULL,
  `anonymizationReport` TEXT(40000) NULL,
  `TransformationReport` TEXT(40000) NULL,
  `mergeReport` TEXT(40000) NULL,
  `outputReport` TEXT(40000) NULL,
  PRIMARY KEY (`idDetailed Report`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `etlService`.`Overall Report`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `etlService`.`Overall Report` (
  `idOverall Report` INT NOT NULL,
  `Report` TEXT(40000) NOT NULL,
  PRIMARY KEY (`idOverall Report`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `etlService`.`Monitoring`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `etlService`.`Monitoring` (
  `idMonitoring` INT NOT NULL,
  `actor` INT NOT NULL,
  `logs` TEXT(40000) NOT NULL,
  `run_idrun` INT NOT NULL,
  PRIMARY KEY (`idMonitoring`),
  INDEX `fk_Monitoring_run1_idx` (`run_idrun` ASC) VISIBLE,
  CONSTRAINT `fk_Monitoring_run1`
    FOREIGN KEY (`run_idrun`)
    REFERENCES `etlService`.`run` (`idrun`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `etlService`.`Report`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `etlService`.`Report` (
  `idReport` INT NOT NULL,
  `Detailed Report_idDetailed Report` INT NOT NULL,
  `Overall Report_idOverall Report` INT NOT NULL,
  `Monitoring_idMonitoring` INT NOT NULL,
  PRIMARY KEY (`idReport`),
  INDEX `fk_Report_Detailed Report1_idx` (`Detailed Report_idDetailed Report` ASC) VISIBLE,
  INDEX `fk_Report_Overall Report1_idx` (`Overall Report_idOverall Report` ASC) VISIBLE,
  INDEX `fk_Report_Monitoring1_idx` (`Monitoring_idMonitoring` ASC) VISIBLE,
  CONSTRAINT `fk_Report_Detailed Report1`
    FOREIGN KEY (`Detailed Report_idDetailed Report`)
    REFERENCES `etlService`.`Detailed Report` (`idDetailed Report`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Report_Overall Report1`
    FOREIGN KEY (`Overall Report_idOverall Report`)
    REFERENCES `etlService`.`Overall Report` (`idOverall Report`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Report_Monitoring1`
    FOREIGN KEY (`Monitoring_idMonitoring`)
    REFERENCES `etlService`.`Monitoring` (`idMonitoring`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `etlService`.`Overall Report`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `etlService`.`Overall Report` (
  `idOverall Report` INT NOT NULL,
  `Report` TEXT(40000) NOT NULL,
  PRIMARY KEY (`idOverall Report`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `etlService`.`Access`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `etlService`.`Access` (
  `idAccess` INT NOT NULL,
  `actor` INT NOT NULL,
  PRIMARY KEY (`idAccess`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `etlService`.`Role Data`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `etlService`.`Role Data` (
  `idRole Data` INT NOT NULL,
  `name` VARCHAR(450) NOT NULL,
  `Access_idAccess` INT NOT NULL,
  PRIMARY KEY (`idRole Data`),
  INDEX `fk_Role Data_Access1_idx` (`Access_idAccess` ASC) VISIBLE,
  CONSTRAINT `fk_Role Data_Access1`
    FOREIGN KEY (`Access_idAccess`)
    REFERENCES `etlService`.`Access` (`idAccess`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `etlService`.`Account Data`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `etlService`.`Account Data` (
  `idAccount Data` INT NOT NULL,
  `token` VARCHAR(450) NOT NULL,
  `name` VARCHAR(450) NOT NULL,
  `Role Data_idRole Data` INT NOT NULL,
  `Access_idAccess` INT NOT NULL,
  PRIMARY KEY (`idAccount Data`),
  INDEX `fk_Account Data_Role Data1_idx` (`Role Data_idRole Data` ASC) VISIBLE,
  INDEX `fk_Account Data_Access1_idx` (`Access_idAccess` ASC) VISIBLE,
  CONSTRAINT `fk_Account Data_Role Data1`
    FOREIGN KEY (`Role Data_idRole Data`)
    REFERENCES `etlService`.`Role Data` (`idRole Data`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Account Data_Access1`
    FOREIGN KEY (`Access_idAccess`)
    REFERENCES `etlService`.`Access` (`idAccess`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `etlService`.`Permission Data`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `etlService`.`Permission Data` (
  `idPermission Data` INT NOT NULL,
  `name` VARCHAR(450) NOT NULL,
  `Access_idAccess` INT NOT NULL,
  PRIMARY KEY (`idPermission Data`),
  INDEX `fk_Permission Data_Access1_idx` (`Access_idAccess` ASC) VISIBLE,
  CONSTRAINT `fk_Permission Data_Access1`
    FOREIGN KEY (`Access_idAccess`)
    REFERENCES `etlService`.`Access` (`idAccess`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `etlService`.`Role_has_Permissions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `etlService`.`Role_has_Permissions` (
  `Role Data_idRole Data` INT NOT NULL,
  `Permission Data_idPermission Data` INT NOT NULL,
  PRIMARY KEY (`Role Data_idRole Data`, `Permission Data_idPermission Data`),
  INDEX `fk_Role Data_has_Permission Data_Permission Data1_idx` (`Permission Data_idPermission Data` ASC) VISIBLE,
  INDEX `fk_Role Data_has_Permission Data_Role Data1_idx` (`Role Data_idRole Data` ASC) VISIBLE,
  CONSTRAINT `fk_Role Data_has_Permission Data_Role Data1`
    FOREIGN KEY (`Role Data_idRole Data`)
    REFERENCES `etlService`.`Role Data` (`idRole Data`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Role Data_has_Permission Data_Permission Data1`
    FOREIGN KEY (`Permission Data_idPermission Data`)
    REFERENCES `etlService`.`Permission Data` (`idPermission Data`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

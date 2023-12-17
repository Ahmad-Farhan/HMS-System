-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema serenlife
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema serenlife
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `serenlife` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `serenlife` ;

-- -----------------------------------------------------
-- Table `serenlife`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `serenlife`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NULL DEFAULT NULL,
  `last_name` VARCHAR(45) NULL DEFAULT NULL,
  `contact` VARCHAR(15) NULL DEFAULT NULL,
  `password` VARCHAR(16) NOT NULL,
  `dob` VARCHAR(10) NULL DEFAULT NULL,
  `ssn` VARCHAR(15) NULL DEFAULT NULL,
  `username` VARCHAR(45) NULL DEFAULT NULL,
  `type` VARCHAR(15) NULL DEFAULT NULL, 
  `gender` VARCHAR(15) NULL DEFAULT NULL,
  `specialization` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 30
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `serenlife`.`timeinterval`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `serenlife`.`timeinterval` (
  `interval_id` INT NOT NULL AUTO_INCREMENT,
  `start_time` TIME NULL DEFAULT NULL,
  `duration` SMALLINT NULL DEFAULT NULL,
  `day` VARCHAR(10) NULL DEFAULT NULL,
  PRIMARY KEY (`interval_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 34
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `serenlife`.`appointment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `serenlife`.`appointment` (
  `appointment_id` INT NOT NULL AUTO_INCREMENT,
  `doctor_id` INT NULL DEFAULT NULL,
  `patient_id` INT NULL DEFAULT NULL,
  `interval_id` INT NULL DEFAULT NULL,
  `date` DATE NULL DEFAULT NULL,
  `status` VARCHAR(15) NULL DEFAULT NULL,
  PRIMARY KEY (`appointment_id`),
  INDEX `FK_doctor_idx` (`doctor_id` ASC) VISIBLE,
  INDEX `FK_patient_idx` (`patient_id` ASC) VISIBLE,
  INDEX `FK_interval_idx` (`interval_id` ASC) VISIBLE,
  CONSTRAINT `FK_appointment_doctor`
    FOREIGN KEY (`doctor_id`)
    REFERENCES `serenlife`.`user` (`user_id`),
  CONSTRAINT `FK_appointment_interval`
    FOREIGN KEY (`interval_id`)
    REFERENCES `serenlife`.`timeinterval` (`interval_id`),
  CONSTRAINT `FK_appointment_patient`
    FOREIGN KEY (`patient_id`)
    REFERENCES `serenlife`.`user` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 41
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `serenlife`.`availability`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `serenlife`.`availability` (
  `availability_id` INT NOT NULL AUTO_INCREMENT,
  `doctor_id` INT NULL DEFAULT NULL,
  `interval_id` INT NOT NULL,
  PRIMARY KEY (`availability_id`),
  INDEX `FK_doctor_idx` (`doctor_id` ASC) VISIBLE,
  INDEX `FK_interval_idx` (`interval_id` ASC) VISIBLE,
  CONSTRAINT `FK_availabily_doctor`
    FOREIGN KEY (`doctor_id`)
    REFERENCES `serenlife`.`user` (`user_id`),
  CONSTRAINT `FK_availabily_interval`
    FOREIGN KEY (`interval_id`)
    REFERENCES `serenlife`.`timeinterval` (`interval_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 26
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `serenlife`.`bill`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `serenlife`.`bill` (
  `bill_id` INT NOT NULL AUTO_INCREMENT,
  `appointment_id` INT NULL DEFAULT NULL,
  `amount` DECIMAL(10,0) NULL DEFAULT NULL,
  `status` VARCHAR(8) NULL DEFAULT NULL,
  `generated_on` DATETIME NULL DEFAULT now(),
  `paid_on` DATETIME NULL,
  PRIMARY KEY (`bill_id`),
  INDEX `FK_appointment_idx` (`appointment_id` ASC) VISIBLE,
  CONSTRAINT `FK_bill_appointment`
    FOREIGN KEY (`appointment_id`)
    REFERENCES `serenlife`.`appointment` (`appointment_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `serenlife`.`healthreport`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `serenlife`.`healthreport` (
  `healthreport_id` INT NOT NULL AUTO_INCREMENT,
  `appointment_id` INT NULL DEFAULT NULL,
  `doctor_id` INT NULL DEFAULT NULL,
  `patient_id` INT NULL DEFAULT NULL,
  `remarks` VARCHAR(200) NULL DEFAULT NULL,
  PRIMARY KEY (`healthreport_id`),
  INDEX `FK_doctor_idx` (`doctor_id` ASC) VISIBLE,
  INDEX `FK_patient_idx` (`patient_id` ASC) VISIBLE,
  INDEX `FK_appointment_idx` (`appointment_id` ASC) VISIBLE,
  CONSTRAINT `FK_healthrepor_appointment`
    FOREIGN KEY (`appointment_id`)
    REFERENCES `serenlife`.`appointment` (`appointment_id`),
  CONSTRAINT `FK_healthrepor_patient`
    FOREIGN KEY (`patient_id`)
    REFERENCES `serenlife`.`user` (`user_id`),
  CONSTRAINT `FK_healthreport_doctor`
    FOREIGN KEY (`doctor_id`)
    REFERENCES `serenlife`.`user` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 20
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `serenlife`.`symptom`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `serenlife`.`symptom` (
  `symptom_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`symptom_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `serenlife`.`identifiedsymptom`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `serenlife`.`identifiedsymptom` (
  `complaint_id` INT NOT NULL AUTO_INCREMENT,
  `healthreport_id` INT NULL DEFAULT NULL,
  `symptom_id` INT NULL DEFAULT NULL,
  `severity` TINYINT NULL DEFAULT NULL,
  PRIMARY KEY (`complaint_id`),
  INDEX `FK_healthreport_idx` (`healthreport_id` ASC) VISIBLE,
  INDEX `FK_symptom_idx` (`symptom_id` ASC) VISIBLE,
  CONSTRAINT `FK_identifiedsymptom_healthreport`
    FOREIGN KEY (`healthreport_id`)
    REFERENCES `serenlife`.`healthreport` (`healthreport_id`),
  CONSTRAINT `FK_identifiedsymptom_symptom`
    FOREIGN KEY (`symptom_id`)
    REFERENCES `serenlife`.`symptom` (`symptom_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 39
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `serenlife`.`medicine`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `serenlife`.`medicine` (
  `medicine_id` INT NOT NULL AUTO_INCREMENT,
  `scientificname` VARCHAR(45) NULL DEFAULT NULL,
  `medicinename` VARCHAR(45) NULL DEFAULT NULL,
  `dosage` VARCHAR(10) NULL DEFAULT NULL,
  `price` DECIMAL(10,0) NULL DEFAULT NULL,
  `type` VARCHAR(10) NULL DEFAULT 'tablet',
  PRIMARY KEY (`medicine_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 31
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `serenlife`.`prescription`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `serenlife`.`prescription` (
  `prescription_id` INT NOT NULL AUTO_INCREMENT,
  `healthreport_id` INT NULL DEFAULT NULL,
  `medicine_id` INT NULL DEFAULT NULL,
  `no_of_days` TINYINT NULL DEFAULT NULL,
  `timings` VARCHAR(40) NULL DEFAULT NULL,
  `amount` TINYINT NULL DEFAULT '1',
  `instruction` VARCHAR(15) NULL,
  PRIMARY KEY (`prescription_id`),
  INDEX `FK_healthreport_idx` (`healthreport_id` ASC) VISIBLE,
  INDEX `FK_medicine_idx` (`medicine_id` ASC) VISIBLE,
  CONSTRAINT `FK_prescription_healthreport`
    FOREIGN KEY (`healthreport_id`)
    REFERENCES `serenlife`.`healthreport` (`healthreport_id`),
  CONSTRAINT `FK_prescription_medicine`
    FOREIGN KEY (`medicine_id`)
    REFERENCES `serenlife`.`medicine` (`medicine_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 36
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `serenlife`.`test`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `serenlife`.`test` (
  `test_id` INT NOT NULL AUTO_INCREMENT,
  `testname` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`test_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 31
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `serenlife`.`recommendation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `serenlife`.`recommendation` (
  `recommendation_id` INT NOT NULL AUTO_INCREMENT,
  `healthreport_id` INT NULL DEFAULT NULL,
  `test_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`recommendation_id`),
  INDEX `FK_healthreport_idx` (`healthreport_id` ASC) VISIBLE,
  INDEX `FK_test_idx` (`test_id` ASC) VISIBLE,
  CONSTRAINT `FK_recommendation_healthreport`
    FOREIGN KEY (`healthreport_id`)
    REFERENCES `serenlife`.`healthreport` (`healthreport_id`),
  CONSTRAINT `FK_recommendation_test`
    FOREIGN KEY (`test_id`)
    REFERENCES `serenlife`.`test` (`test_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 24
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `serenlife`.`recommendation`
-- -----------------------------------------------------
CREATE TABLE `serenlife`.`appointmentprice` (
  `specialization` VARCHAR(30) NOT NULL,
  `cost` DECIMAL(10,0) NULL,
  PRIMARY KEY (`specialization`));


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

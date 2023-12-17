LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/HMS/HMS - user.csv' 
INTO TABLE user FIELDS TERMINATED BY ',' LINES TERMINATED BY '\r\n' IGNORE 1 ROWS;

LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/HMS/HMS - test.csv' 
INTO TABLE test FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 1 ROWS;

LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/HMS/HMS - symptom.csv' 
INTO TABLE symptom FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 1 ROWS;

LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/HMS/HMS - medicine.csv' 
INTO TABLE medicine FIELDS TERMINATED BY ',' LINES TERMINATED BY '\r\n' IGNORE 1 ROWS;

LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/HMS/HMS - timeinterval.csv' 
INTO TABLE timeinterval FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 1 ROWS;

LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/HMS/HMS - availability.csv' 
INTO TABLE availability FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 1 ROWS;

LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/HMS/HMS - appointment.csv' 
INTO TABLE appointment FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 1 ROWS;

LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/HMS/HMS - healthreport.csv' 
INTO TABLE healthreport FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 1 ROWS;

-- LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/HMS/HMS - bill.csv' 
-- INTO TABLE bill FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 1 ROWS;

LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/HMS/HMS - identifiedsymptoms.csv' 
INTO TABLE identifiedsymptom FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 1 ROWS;

LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/HMS/HMS - recommendation.csv' 
INTO TABLE recommendation FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 1 ROWS;

LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/HMS/HMS - prescription.csv' 
INTO TABLE prescription FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 1 ROWS;

INSERT INTO `serenlife`.`appointmentprice` (`specialization`, `cost`)
VALUES
('Cardiology', 1200),
('Orthopedics', 1000),
('Neurology', 1100),
('Pediatrics', 600),
('Anesthesiology', 1300),
('Dermatology', 900);
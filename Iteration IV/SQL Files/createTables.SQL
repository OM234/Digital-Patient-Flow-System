use digihealth;

CREATE TABLE IF NOT EXISTS Patient(

    patientID VARCHAR(30) PRIMARY KEY,
    firstName VARCHAR(30),
    lastName VARCHAR(30),
    height INTEGER,
    weight DOUBLE,
    BMI DOUBLE,
    DOB DATE,
    Gender CHAR
);

CREATE TABLE IF NOT EXISTS contactInfo(

    patientID VARCHAR(100) PRIMARY KEY,
    streetNumber VARCHAR(100),
    postalCode VARCHAR(6),
    city VARCHAR(100),
    province VARCHAR(100),
    country VARCHAR(100),
    phoneNumber VARCHAR(10),
    email VARCHAR(100),
    FOREIGN KEY (patientID) REFERENCES Patient(patientID)
);

CREATE TABLE IF NOT EXISTS user (

    userID VARCHAR(30) PRIMARY KEY,
    password VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS medication_names (

    name VARCHAR(100) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS medication_routes (

    route VARCHAR(100) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS medication_frequency (

    frequency VARCHAR(100) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS medication_units (

    units VARCHAR(100) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS medication_prescriber (

    prescriberID VARCHAR(100) PRIMARY KEY,

    CONSTRAINT FOREIGN KEY(prescriberID) REFERENCES user(userID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS patient_medication (

    patientID VARCHAR(100),
    prescriberID VARCHAR(100),
    medName VARCHAR(100),
    route VARCHAR(100),
    frequency VARCHAR(100),
    units VARCHAR(100),
    prescribed DATE,
    expires DATE,
    PRIMARY KEY(patientID, prescriberID, medName, route, frequency, units, prescribed, expires),
    FOREIGN KEY(patientID) references patient(patientID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(prescriberID) references medication_prescriber(prescriberID) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE INDEX patient_medication__index
    on patient_medication (patientID);

CREATE TABLE medical_note (

    patientID VARCHAR(100),
    
)
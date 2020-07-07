package model;

import javafx.beans.property.SimpleStringProperty;

public class Patient {

    private DigiSystem digiSystem;
    private SimpleStringProperty patientID;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private char gender;

    public Patient(String patientID, String firstName, String lastName, char gender) {

        digiSystem = DigiSystem.getInstance();

        if(digiSystem.hasPatient(patientID)) {
            throw new IllegalArgumentException("Patient already exists");
        }

        this.patientID = new SimpleStringProperty(patientID);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.gender = gender;

        digiSystem.addPatient(this);
    }

    public String getPatientID() {
        return patientID.get();
    }

    public void setPatientID(String patientID) {
        this.patientID.set(patientID);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }
}

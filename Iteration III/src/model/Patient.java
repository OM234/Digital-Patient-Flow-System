package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;

public class Patient {

    private DigiSystem digiSystem;
    private SimpleStringProperty patientID;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleIntegerProperty height;
    private SimpleDoubleProperty weight;
    private SimpleDoubleProperty BMI;
    private LocalDate DOB;
    private ContactInformation contactInformation;
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
        this.height = new SimpleIntegerProperty();
        this.weight = new SimpleDoubleProperty();
        this.BMI = new SimpleDoubleProperty();
        this.DOB = LocalDate.of(0,1,1);
        this.contactInformation = new ContactInformation();

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

    public SimpleStringProperty firstNameProperty() {

        return firstName;
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

    public int getHeight() {

        return height.get();
    }

    public void setHeight(int height) {

        this.height.set(height);
    }

    public double getWeight() {

        return weight.get();
    }

    public void setWeight(double weight) {

        this.weight.set(weight);
    }

    public double getBMI() {

        BMI.set(this.getWeight() / Math.pow((this.getHeight()/100.0),2));

        return BMI.get();
    }

    public LocalDate getDOB() {

        return DOB;
    }

    public void setDOB(LocalDate DOB) {

        this.DOB = DOB;
    }

    public int getAge() {

        return Period.between(this.getDOB(), LocalDate.now()).getYears();
    }

    public ContactInformation getContactInformation() {

        return contactInformation;
    }

    public void setContactInformation(ContactInformation contactInformation) {

        this.contactInformation = contactInformation;
    }
}

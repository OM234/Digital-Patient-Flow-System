package model;

import services.DigiServices;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Patient {

    private DigiServices digiServices;
    private DigiSystem digiSystem;
    //private SimpleStringProperty patientID;
    private String patientID;
    private String firstName;
    private String lastName;
    private int height;
    private double weight;
    private double BMI;
    private LocalDate DOB;
    private ContactInformation contactInformation;
    private char gender;
    private List<MedicalNote> medicalNotes;
    private List<Medication> medications;

    public Patient(String patientID) throws SQLException {

        digiServices = DigiServices.getInstance();
        this.patientID = patientID;
    }

    public Patient(String patientID, String firstName, String lastName, char gender) {

        digiSystem = DigiSystem.getInstance();

        if(digiSystem.hasPatient(patientID)) {
            throw new IllegalArgumentException("Patient already exists");
        }

        this.patientID = patientID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.DOB = LocalDate.of(0,1,1);
        this.contactInformation = new ContactInformation();
        this.medicalNotes = new ArrayList<>();
        this.medications = new ArrayList<>();

        digiSystem.addPatient(this);
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getBMI() {
        return BMI;
    }

    public void setBMI(double BMI) {
        this.BMI = BMI;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
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

    public void addMedicalNote(MedicalNote medicalNote){

        medicalNotes.add(medicalNote);
    }

    public List<MedicalNote> getMedicalNotes(){

        return medicalNotes;
    }

    public int getNextMedicalNoteID(){

        return medicalNotes.size() + 1;
    }

    public List<Medication> getMedications() {

        return medications;
    }

    public void addMedication(Medication medication) {

        medications.add(medication);
    }

    public void removeMedication(Medication medication) {

        medications.remove(medication);
    }
}

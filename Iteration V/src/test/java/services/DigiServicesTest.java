package services;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DigiServicesTest {

    @Test
    void testGetInstanceWhenNotYetInstantiated() {
        try {
            DigiServices.getInstance();
        } catch (IllegalStateException e) {
            return;
        }
        fail("Test should have caught IllegalStateException");
    }

    @Test
    void testGetInstance() throws SQLException {
        DigiServices digiServices = DigiServices.getInstance(null, null, null,
                null, null, null, null,null);

        assertNotNull(digiServices);
    }

    @Test
    void addUser() {
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void setCurrentUser() {
    }

    @Test
    void getCurrentUser() {
    }

    @Test
    void passChecker() {
    }

    @Test
    void getPatient() {
    }

    @Test
    void getAllPatients() {
    }

    @Test
    void addPatient() {
    }

    @Test
    void removePatient() {
    }

    @Test
    void hasPatient() {
    }

    @Test
    void testHasPatient() {
    }

    @Test
    void updatePatient() {
    }

    @Test
    void getAllUnits() {
    }

    @Test
    void getUnit() {
    }

    @Test
    void addUnit() {
    }

    @Test
    void removeUnit() {
    }

    @Test
    void hasUnit() {
    }

    @Test
    void testHasUnit() {
    }

    @Test
    void addPatToUnit() {
    }

    @Test
    void testAddPatToUnit() {
    }

    @Test
    void getPatientsOnUnit() {
    }

    @Test
    void testGetPatientsOnUnit() {
    }

    @Test
    void removePatientFromUnit() {
    }

    @Test
    void patientOnUnit() {
    }

    @Test
    void addMedicalNote() {
    }

    @Test
    void getMedicalNotes() {
    }

    @Test
    void getNextMedicalNoteID() {
    }

    @Test
    void setMedicalNoteDeleted() {
    }

    @Test
    void addContactInfo() {
    }

    @Test
    void getContactInfo() {
    }

    @Test
    void updateContactInfo() {
    }

    @Test
    void addMedication() {
    }

    @Test
    void getMedicationNames() {
    }

    @Test
    void removeMedication() {
    }

    @Test
    void getPatientMedications() {
    }
}
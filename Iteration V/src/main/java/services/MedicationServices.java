package services;

import bean.Medication;
import persistence.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicationServices {

    private final DAO<Medication> medicationDAO;
    private final DAO<String> medicationNameDAO;
    private final PatientServices patientServices;

    public MedicationServices(DAO<Medication> medicationDAO, DAO<String> medicationNameDAO, PatientServices patientServices) {
        this.medicationDAO = medicationDAO;
        this.medicationNameDAO = medicationNameDAO;
        this.patientServices = patientServices;
    }

    public boolean addMedication(Medication medication) throws SQLException {
        if(!patientServices.hasPatient(medication.getPatientID())) {
            return false;
        } else {
            medicationDAO.save(medication);
            return true;
        }
    }

    public List<String> getMedicationNames() throws SQLException {
        return medicationNameDAO.getAll();
    }

    public boolean removeMedication(Medication medication) throws SQLException {
        medicationDAO.delete(medication);
        return true;
    }

    public List<Medication> getPatientMedications(String patientID) throws SQLException {
        if(!patientServices.hasPatient(patientID)) {
            return new ArrayList<Medication>();
        } else {
            return medicationDAO.get(patientID);
        }
    }
}

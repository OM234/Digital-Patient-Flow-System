package services;

import bean.MedicalNote;
import persistence.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicalNoteServices {

    private final DAO<MedicalNote> medicalNoteDAO;
    private final PatientServices patientServices;

    public MedicalNoteServices(DAO<MedicalNote> medicalNoteDAO, PatientServices patientServices) {
        this.medicalNoteDAO = medicalNoteDAO;
        this.patientServices = patientServices;
    }

    public boolean addMedicalNote(MedicalNote medicalNote) throws SQLException {

        if(!patientServices.hasPatient(medicalNote.getPatientID())) {
            return false;
        } else {
            medicalNote.setTemp(Math.round(medicalNote.getTemp()*10.0)/10.0);
            medicalNoteDAO.save(medicalNote);
            return true;
        }
    }

    public List<MedicalNote> getMedicalNotes(String patientID) throws SQLException {
        if(!patientServices.hasPatient(patientID)) {
            return new ArrayList<MedicalNote>();
        } else {
            return medicalNoteDAO.get(patientID);
        }
    }

    public int getNextMedicalNoteID(String patientID) throws SQLException {
        return medicalNoteDAO.get(patientID).size();
    }

    public boolean setMedicalNoteDeleted(MedicalNote medicalNote) throws SQLException {
        medicalNoteDAO.update(medicalNote);
        return true;
    }
}

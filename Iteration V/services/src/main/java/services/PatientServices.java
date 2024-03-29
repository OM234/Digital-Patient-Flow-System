package services;

import bean.ContactInfo;
import bean.Patient;
import persistence.DAO;

import java.sql.SQLException;
import java.util.List;

public class PatientServices {

    private final DAO<Patient> patientDAO;
    private final DAO<ContactInfo> contactInfoDAO;

    public PatientServices(DAO<Patient> patientDAO, DAO<ContactInfo> contactInfoDAO) {
        this.patientDAO = patientDAO;
        this.contactInfoDAO = contactInfoDAO;
    }

    public Patient getPatient(String patientID) throws SQLException {
        if(!hasPatient(patientID)) {
            return null;
        } else {
            return patientDAO.get(patientID).get(0);
        }

    }

    public List<Patient> getAllPatients() throws SQLException {
        return patientDAO.getAll();
    }

    public boolean addPatient(Patient patient) throws SQLException {
        if(hasPatient(patient)) {
            return false;
        } else {
            patientDAO.save(patient);
            ContactInfo contactInfo = new ContactInfo();
            contactInfo.setPatientID(patient.getID());
            addContactInfo(contactInfo);
            return true;
        }
    }

    public boolean removePatient(Patient patient) throws SQLException {
        if(!hasPatient(patient)) {
            return false;
        } else {
            patientDAO.delete(patient);
            return true;
        }
    }

    public boolean hasPatient(Patient patient) throws SQLException {
        if(!patientDAO.get(patient.getID()).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasPatient(String patientID) throws SQLException {
        if(!patientDAO.get(patientID).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updatePatient(Patient patient) throws SQLException {
        if(!patientDAO.get(patient.getID()).isEmpty()) {
            patientDAO.update(patient);
            return true;
        } else {
            return false;
        }
    }

    public boolean addContactInfo(ContactInfo contactInfo) throws SQLException {
        if(!hasPatient(contactInfo.getPatientID())) {
            return false;
        } else {
            contactInfoDAO.save(contactInfo);
            return true;
        }
    }

    public ContactInfo getContactInfo(Patient patient) throws SQLException {
        return contactInfoDAO.get(patient.getID()).get(0);
    }

    public boolean updateContactInfo(ContactInfo contactInfo) throws SQLException {
        contactInfoDAO.update(contactInfo);
        return true;
    }
}

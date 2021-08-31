package services;

import bean.ContactInfo;
import bean.Patient;
import persistence.DAO;

import java.sql.SQLException;

public class ContactInfoServices {

    private final DAO<ContactInfo> contactInfoDAO;
    private final PatientServices patientServices;

    public ContactInfoServices(DAO<ContactInfo> contactInfoDAO, PatientServices patientServices) {
        this.contactInfoDAO = contactInfoDAO;
        this.patientServices = patientServices;
    }

    public boolean addContactInfo(ContactInfo contactInfo) throws SQLException {
        if(!patientServices.hasPatient(contactInfo.getPatientID())) {
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

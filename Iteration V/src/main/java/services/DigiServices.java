package services;

import bean.*;
import persistence.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DigiServices {

    private static DigiServices digiServices = null;
    private DAO<User> userDAO;
    private DAO<Patient> patientDAO;
    private DAO<Unit> unitDAO;
    private DAO<PatientOnUnit> patOnUnitDAO;
    private DAO<MedicalNote> medicalNoteDAO;
    private DAO<ContactInfo> contactInfoDAO;
    private DAO<Medication> medicationDAO;
    private DAO<String> medicationNameDAO;
    private User currentUser;

    private DigiServices(DAO<User> userDAO, DAO<Patient> patientDAO, DAO<Unit> unitDAO, DAO<PatientOnUnit> patOnUnitDAO,
                         DAO<MedicalNote> medicalNoteDAO, DAO<ContactInfo> contactInfoDAO,
                         DAO<Medication> medicationDAO, DAO<String> medicationNameDAO) throws SQLException {
        this.currentUser = null;
        this.userDAO = userDAO;
        this.patientDAO = patientDAO;
        this.unitDAO = unitDAO;
        this.patOnUnitDAO = patOnUnitDAO;
        this.medicalNoteDAO = medicalNoteDAO;
        this.contactInfoDAO = contactInfoDAO;
        this.medicationDAO = medicationDAO;
        this.medicationNameDAO = medicationNameDAO;
    }

    public static DigiServices getInstance() {
        if(digiServices == null) {
            throw new IllegalStateException("DigiServices not yet instantiated");
        }
        return digiServices;
    }

    public static DigiServices getInstance(DAO<User> userDAO, DAO<Patient> patientDAO, DAO<Unit> unitDAO,
                                           DAO<PatientOnUnit> patOnUnitDAO, DAO<MedicalNote> medicalNoteDAO,
                                           DAO<ContactInfo> contactInfoDAO, DAO<Medication> medicationDAO,
                                           DAO<String> medicationNameDAO) throws SQLException {
        if(digiServices == null) {
            digiServices = new DigiServices(userDAO, patientDAO, unitDAO, patOnUnitDAO, medicalNoteDAO, contactInfoDAO,
                    medicationDAO, medicationNameDAO);
        }
        return digiServices;
    }
    /*
     ******************** User Services ********************
     */

    public boolean addUser(User user) throws SQLException {

        if(user == null) return false;

        String userID = user.getID();

        if(!userDAO.get(user.getID()).isEmpty()) {

            return false;

        } else {

            userDAO.save(user);
            return true;
        }
    }

    public List<User> getAllUsers() throws SQLException {

        return userDAO.getAll();
    }

    public void setCurrentUser(User user) throws SQLException {

        if(userDAO.get(user.getID()).isEmpty()) {

            try {
                throw new Exception("User does not exist");

            } catch(Exception e) {

                System.out.println("user does not exist");
                System.exit(0);
            }
        }

        currentUser = userDAO.get(user.getID()).get(0);
    }

    public User getCurrentUser() {

        return currentUser;
    }

    public boolean passChecker(String userID, String password) throws SQLException{

        if(!userDAO.get(userID).isEmpty() && userDAO.get(userID).get(0).getPassword().equals(password)) {

            setCurrentUser(userDAO.get(userID).get(0));

            return true;

        } else {

            return false;
        }

    }

    /*
     ******************** Patient Services ********************
     */

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

    /*
     ******************** Unit Services ********************
     */

    public List<Unit> getAllUnits() throws SQLException {

        return unitDAO.getAll();
    }

    public Unit getUnit(String unitID) throws SQLException {

        if(!hasUnit(unitID)) {

            return null;

        } else {

            return unitDAO.get(unitID).get(0);
        }
    }

    public boolean addUnit(Unit unit) throws SQLException {

        if(hasUnit(unit)) {

            return false;

        } else {

            unitDAO.save(unit);

            return true;
        }
    }

    public boolean removeUnit(Unit unit) throws SQLException {

        if(!hasUnit(unit)) {

            return false;

        } else {

            unitDAO.delete(unit);

            return true;
        }
    }

    public boolean hasUnit(Unit unit) throws SQLException {

        if(!unitDAO.get(unit.getID()).isEmpty()) {

            return true;

        } else {

            return false;
        }
    }

    public boolean hasUnit(String unitID) throws SQLException {

        if(!unitDAO.get(unitID).isEmpty()) {

            return true;

        } else {

            return false;
        }
    }

    /*
     ******************** PatOnUnit Services ********************
     */


    public boolean addPatToUnit(Unit unit, Patient patient) throws SQLException {

        if(hasUnit(unit) && hasPatient(patient)) {

            PatientOnUnit patientOnUnit = new PatientOnUnit();
            patientOnUnit.setUnitID(unit.getID());
            patientOnUnit.setPatientID(patient.getID());
            patOnUnitDAO.save(patientOnUnit);
            return true;

        } else {

            return false;
        }
    }

    public boolean addPatToUnit(String unitID, String patientID) throws SQLException {

        if(hasUnit(unitID) && hasPatient(patientID)) {

            PatientOnUnit patientOnUnit = new PatientOnUnit();
            patientOnUnit.setUnitID(unitID);
            patientOnUnit.setPatientID(patientID);
            patOnUnitDAO.save(patientOnUnit);
            return true;

        } else {

            return false;
        }
    }

    public List<Patient> getPatientsOnUnit(Unit unit) throws SQLException {

        List<PatientOnUnit> patientsOnUnit = patOnUnitDAO.get(unit.getID());
        List<Patient> patientList = new ArrayList<>();

        patientsOnUnit.forEach(e -> {

            try {
                patientList.add(getPatient(e.getPatientID()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        return patientList;
    }

    public List<Patient> getPatientsOnUnit(String unitID) throws SQLException {

        List<PatientOnUnit> patientsOnUnit = patOnUnitDAO.get(unitID);
        List<Patient> patientList = new ArrayList<>();

        patientsOnUnit.forEach(e -> {

            try {
                patientList.add(getPatient(e.getPatientID()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        return patientList;
    }

    public boolean removePatientFromUnit(Patient patient, Unit unit) throws SQLException {

        PatientOnUnit patientOnUnit = new PatientOnUnit();
        patientOnUnit.setPatientID(patient.getID());
        patientOnUnit.setUnitID(unit.getID());
        patOnUnitDAO.delete(patientOnUnit);

        return true;
    }

    public boolean patientOnUnit(String unitID, String patientID) throws SQLException {

        List<Patient> patientList = getPatientsOnUnit(unitID);
        return patientList.stream().anyMatch(e -> e.getID().equals(patientID));
    }

    /*
     ******************** MedicalNote Services ********************
     */

    public boolean addMedicalNote(MedicalNote medicalNote) throws SQLException {

        if(!hasPatient(medicalNote.getPatientID())) {

            return false;

        } else {

            medicalNote.setTemp(Math.round(medicalNote.getTemp()*10.0)/10.0);
            medicalNoteDAO.save(medicalNote);
            return true;
        }
    }

    public List<MedicalNote> getMedicalNotes(String patientID) throws SQLException {

        if(!hasPatient(patientID)) {

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

    /*
     ******************** ContactInfo Services ********************
     */

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

    /*
     ******************** Medication Services ********************
     */

    public boolean addMedication(Medication medication) throws SQLException {

        if(!hasPatient(medication.getPatientID())) {

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

        if(!hasPatient(patientID)) {

            return new ArrayList<Medication>();

        } else {

            return medicationDAO.get(patientID);
        }
    }
}

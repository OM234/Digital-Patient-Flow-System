package services;

import bean.*;
import persistence.*;

import java.sql.SQLException;
import java.util.List;

public class DigiServices {

    private static DigiServices digiServices = null;
    private UserDAO userDAO;
    private PatientDAO patientDAO;
    private UnitDAO unitDAO;
    private PatOnUnitDAO patOnUnitDAO;
    private MedicalNoteDAO medicalNoteDAO;
    private ContactInfoDAO contactInfoDAO;
    private MedicationDAO medicationDAO;
    private User currentUser;

    private DigiServices() throws SQLException {

        currentUser = null;
        userDAO = new UserDAO();
        patientDAO = new PatientDAO();
        unitDAO = new UnitDAO();
        patOnUnitDAO = new PatOnUnitDAO();
        medicalNoteDAO = new MedicalNoteDAO();
        contactInfoDAO = new ContactInfoDAO();
        medicationDAO = new MedicationDAO();
    }

    public static DigiServices getInstance() throws SQLException {

        if(digiServices == null) {

            digiServices = new DigiServices();
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

    public List<Patient> getAllPatients() throws SQLException {

        return patientDAO.getAll();
    }

    public boolean addPatient(Patient patient) throws SQLException {

        if(hasPatient(patient)) {

            return false;

        } else {

            patientDAO.save(patient);

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

    public boolean addUnit(Unit unit) throws SQLException {

        if(hasUnit(unit)) {

            return false;

        } else {

            unitDAO.save(unit);

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

    /*
     ******************** MedicalNote Services ********************
     */

    public boolean addMedicalNote(MedicalNote medicalNote) throws SQLException {

        if(!hasPatient(medicalNote.getPatientID())) {

            return false;

        } else {

            medicalNoteDAO.save(medicalNote);
            return true;
        }
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


}

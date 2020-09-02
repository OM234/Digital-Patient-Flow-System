package services;

import bean.Patient;
import bean.Unit;
import model.Unit2;
import bean.User;
import persistence.PatientDAO;
import persistence.UnitDAO;
import persistence.UserDAO;
import java.sql.SQLException;
import java.util.List;

public class DigiServices {

    private static DigiServices digiServices = null;
    private UserDAO userDAO;
    private PatientDAO patientDAO;
    private UnitDAO unitDAO;
    private User currentUser;

    private DigiServices() throws SQLException {

        currentUser = null;
        userDAO = new UserDAO();
        patientDAO = new PatientDAO();
        unitDAO = new UnitDAO();
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

//    public boolean setFirstName(String patientID, String name) throws SQLException{
//
//        if(!patientDAO.get(patientID).isEmpty()) {
//
//            patientDAO.update(new Patient(patientID), "firstName", name);
//            return true;
//
//        } else {
//
//            return false;
//        }
//    }

    public boolean updatePatient(Patient patient) throws SQLException {

        if(!patientDAO.get(patient.getID()).isEmpty()) {

            patientDAO.update(patient);
            return true;

        } else {

            return false;
        }
    }

//    public boolean setLastName(String patientID, String name) throws SQLException {
//
//        if(!patientDAO.get(patientID).isEmpty()) {
//
//            patientDAO.update(new Patient(patientID), "lastName", name);
//            return true;
//
//        } else {
//
//            return false;
//        }
//    }
//
//    public boolean setGender(String patientID, char gender) throws SQLException {
//
//
//        if(!patientDAO.get(patientID).isEmpty()) {
//
//            patientDAO.update(new Patient(patientID), "gender", Character.toString(gender));
//            return true;
//
//        } else {
//
//            return false;
//        }
//    }

    /*
     ******************** Unit Services ********************
     */

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

    public boolean addPatToUnit(Unit unitID, Patient patient) throws SQLException {

//        if(hasUnit(unitID) && hasPatient(patientID)) {
//
//            return true;
//        } else {
//
//            return false;
//        }
        return false;
    }
}

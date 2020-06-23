package model;

import java.util.Map;
import java.util.HashMap;

public class System {

    private static System singleInstance = null; // Singleton design pattern
    private Map<String, User> listOfUsers;
    private Map<String, Unit> listOfUnits;
    private Map<String, Patient> listOfPatients;

    private System() {
        listOfUsers = new HashMap<>();
        listOfUnits = new HashMap<>();
        listOfPatients = new HashMap<>();
    }

    public static System getInstance() { // Singleton design pattern

        if(singleInstance == null) {
            singleInstance = new System();
        }
        return singleInstance;
    }

    public boolean addUser(String userID, User user) {

        if(listOfUsers.containsKey(userID)) {
            return false;
        } else {
            listOfUsers.put(userID, user);
            return true;
        }
    }

    public boolean removeUser(String userID) {

        if(listOfUsers.containsKey(userID)) {
            listOfUsers.remove(userID);
            return true;
        } else {
            return false;
        }
    }

    public boolean addUnit(String unitID, Unit unit) {

        if(listOfUnits.containsKey(unitID)) {
            return false;
        } else {
            listOfUnits.put(unitID, unit);
            return true;
        }
    }

    public boolean hasUnit(String unitID) {

        return(listOfUnits.containsKey(unitID));
    }

    public Unit getUnit(String unitID) {

        return listOfUnits.getOrDefault(unitID, null);
    }

    public boolean removeUnit(String unitID) {

        if(listOfUnits.containsKey(unitID)) {
            listOfUnits.remove(unitID);
            return true;
        } else {
            return false;
        }
    }

    public boolean addPatient(String patientID, Patient patient) {

        if(listOfPatients.containsKey(patientID)) {
            return false;
        } else {
            listOfPatients.put(patientID, patient);
            return true;
        }
    }

    public boolean removePatient(String patientID) {

        if(listOfPatients.containsKey(patientID)) {
            listOfPatients.remove(patientID);
            return true;
        } else {
            return false;
        }
    }

}

package model;

import java.util.HashMap;
import java.util.Map;
//TODO: make all objects private, tighten up encapsulation
public class DigiSystem {

    private static DigiSystem singleInstance = null; // Singleton design pattern
    private Map<String, User> listOfUsers;
    private Map<String, Unit2> listOfUnits;
    private Map<String, Patient> listOfPatients;
    private User currentUser;

    private DigiSystem() {

        listOfUsers = new HashMap<>();
        listOfUnits = new HashMap<>();
        listOfPatients = new HashMap<>();
        currentUser = null;
    }

    public static DigiSystem getInstance() { // Singleton design pattern

        if(singleInstance == null) {
            singleInstance = new DigiSystem();
        }
        return singleInstance;
    }

    public boolean addUser(User user) {

        String userID = user.getUserID();

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

    public boolean hasUser(String userID) {

        return listOfUsers.containsKey(userID);
    }

    public void setCurrentUser(String userID){

        if(!this.listOfUsers.containsKey(userID)) {

            try {
                throw new Exception("User does not exist");

                } catch(Exception e) {

                    System.out.println("user does not exist");
                    System.exit(0);
                }
            }

        currentUser = this.listOfUsers.get(userID);
    }

    public User getCurrentUser() {

        return currentUser;
    }

    public boolean addUnit(Unit2 unit) {

        String unitID = unit.getUnitID();

        if(listOfUnits.containsKey(unitID)) {
            return false;
        } else {
            listOfUnits.put(unitID, unit);
            return true;
        }
    }

    public boolean removeUnit(String unitID) {

        if(listOfUnits.containsKey(unitID)) {

            for(String userID : listOfUsers.keySet()) {

                User user = listOfUsers.get(userID);
                if(user.hasUnitInList(unitID)) {

                    user.removeUnitFromUser(unitID);
                }
            }

            listOfUnits.remove(unitID);

            return true;

        } else {

            return false;
        }
    }

    public boolean hasUnit(String unitID) {

        return(listOfUnits.containsKey(unitID));
    }

    public Unit2 getUnit(String unitID) {

        return listOfUnits.getOrDefault(unitID, null);
    }

    public String getUnitName(String unitID) {

        if(hasUnit(unitID)) {

            return(getUnit(unitID).getUnitName());

        } else {

            return ("");
        }
    }

    public boolean addPatient(Patient patient) {

        String patientID = patient.getPatientID();

        if(listOfPatients.containsKey(patientID)) {

            return false;

        } else {

            listOfPatients.put(patientID, patient);

            return true;
        }
    }

    public boolean removePatient(String patientID) {

        if(listOfPatients.containsKey(patientID)) {

            for(String unitID : listOfUnits.keySet()) {

                Unit2 unit = listOfUnits.get(unitID);
                if(unit.hasPatient(patientID)) {

                    unit.removePatientFromUnit(patientID);
                }
            }

            listOfPatients.remove(patientID);

            return true;

        } else {

            return false;
        }
    }

    public boolean removePatientFromUnit(String patientID, String unitID) {

        Unit2 unit;

        if(hasUnit(unitID)) {

            unit = getUnit(unitID);

        } else {

            return false;
        }

        if(unit.hasPatient(patientID)) {

            return unit.removePatientFromUnit(patientID);

        } else {

            return false;
        }
    }

    public boolean addPatientToUnit(String patientID, String unitID) {

        if(hasUnit(unitID) && hasPatient(patientID)) {

            Unit2 unit = getUnit(unitID);

            return unit.addPatientToUnit(patientID);

        } else {

            return false;
        }
    }

    public boolean patientOnUnit(String patientID, String unitID) {

        if(hasUnit(unitID) && hasPatient(patientID)) {

            Unit2 unit = getUnit(unitID);

            return unit.hasPatient(patientID);

        } else {

            return false;
        }
    }

    public boolean hasPatient(String patientID) {

        return(listOfPatients.containsKey(patientID));
    }

    public String getPatientFirstName(String patientID) {

        if(hasPatient(patientID)) {

            return(getPatient(patientID).getFirstName());

        } else {

            return ("");
        }
    }

    public String getPatientLastName(String patientID) {

        if(hasPatient(patientID)) {

            return(getPatient(patientID).getLastName());

        } else {

            return ("");
        }
    }

    public Patient getPatient(String patientID) {

        return listOfPatients.getOrDefault(patientID, null);
    }

    public boolean passChecker(String userID, String password) {

        if(listOfUsers.containsKey(userID) && listOfUsers.get(userID).getPassword().equals(password)) {

            return true;

        } else {

            return false;
        }
    }

    public Map<String, Unit2> getMapOfUnits(){return listOfUnits;} //TODO: Delete?
    public Map<String, Patient> getMapOfPatients(){return listOfPatients;} //TODO: Delete?
    public Map<String, User> getMapOfUsers(){return listOfUsers;} //TODO: Delete?
}

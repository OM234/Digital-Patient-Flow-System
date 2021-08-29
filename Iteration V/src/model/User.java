package model;

import java.util.HashMap;
import java.util.Map;

public abstract class User {

    private String userID;
    private String password;
    private DigiSystem digiSystem;
    private Map<String, Unit2> unitsOfUser;

    public User(String userID, String password) {

//        this.digiSystem = DigiSystem.getInstance();
//
//        if(digiSystem.hasUser(userID)){
//            throw new IllegalArgumentException("User already exists");
//        }

        this.userID = userID;
        this.password = password;
        this.unitsOfUser = new HashMap<>();

//        digiSystem.addUser(this);
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean createUnit(String unitID, String unitName) {

        if(!digiSystem.hasUnit(unitID)) {

            Unit2 unitToAdd = new Unit2(unitID, unitName);

            unitsOfUser.put(unitID, unitToAdd);

            return true;
        } else {

            return false;
        }
    }

    public boolean deleteUnit(String unitID) {

        if(digiSystem.hasUnit(unitID)) {

            digiSystem.removeUnit(unitID);

            return true;

        } else {

            return false;
        }
    }

    public boolean addUnitToUser(String unitID) {

        if(digiSystem.hasUnit(unitID)){

            Unit2 unitToAdd = digiSystem.getUnit(unitID);

            unitsOfUser.put(unitID, unitToAdd);

            return true;

        } else {

            return false;
        }
    }

    public boolean removeUnitFromUser(String unitID) {

        if(unitsOfUser.containsKey(unitID)){

            unitsOfUser.remove(unitID);

            return true;

        } else {

            return false;
        }
    }

    public boolean hasUnitInList(String unitID) {

        return unitsOfUser.containsKey(unitID);
    }

    //public boolean createPatient(){}; //TODO

    public boolean deletePatient(String patientID) {

        if(digiSystem.hasPatient(patientID)) {

            return digiSystem.removePatient(patientID);

        } else {
            return false;
        }
    }
}

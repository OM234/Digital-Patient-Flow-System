package model;

import java.util.HashMap;
import java.util.Map;

public abstract class User {

    private String userID;
    private String passWord;
    private System system;
    private Map<String, Unit> unitsOfUser;

    public User(String userID, String passWord) {

        this.system = System.getInstance();

        if(system.hasUser(userID)){
            throw new IllegalArgumentException("User already exists");
        }

        this.userID = userID;
        this.passWord = passWord;
        this.unitsOfUser = new HashMap<>();
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public boolean createUnit(String unitID, String unitName) {

        if(!system.hasUnit(unitID)) {

            Unit unitToAdd = new AUnit(unitID, unitName);

            unitsOfUser.put(unitID, unitToAdd);

            return true;
        } else {

            return false;
        }
    }

    public boolean deleteUnit(String unitID) {

        if(system.hasUnit(unitID)) {

            system.removeUnit(unitID);

            return true;

        } else {

            return false;
        }
    }

    public boolean addUnitToUser(String unitID) {

        if(system.hasUnit(unitID)){

            Unit unitToAdd = system.getUnit(unitID);

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

        if(system.hasPatient(patientID)) {

            return system.removePatient(patientID);

        } else {
            return false;
        }
    }
}

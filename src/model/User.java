package model;

import java.util.HashMap;
import java.util.Map;

public abstract class User {

    String userID;
    String passWord;
    System system;
    Map<String, Unit> unitsOfUser;

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
}

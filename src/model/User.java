package model;

import java.util.HashMap;
import java.util.Map;

public abstract class User {

    String userName;
    String passWord;
    System system;
    Map<String, Unit> unitsOfUser;

    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
        this.unitsOfUser = new HashMap<>();
        this.system = System.getInstance();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

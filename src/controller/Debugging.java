package controller;

import model.*;

public class Debugging {

    DigiSystem digiSystem =  DigiSystem.getInstance();
    public void debug() {

        AUser user = new AUser("user", "pass");
        digiSystem.addUser(user);
        AUser user2 = new AUser("admin", "password");
        digiSystem.addUser(user2);
    }
}

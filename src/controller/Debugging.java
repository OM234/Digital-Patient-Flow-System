package controller;

import model.*;

public class Debugging {

    DigiSystem digiSystem = DigiSystem.getInstance();

    public void debug() {

        AUser user = new AUser("", "");
        digiSystem.addUser(user);
        AUser user2 = new AUser("admin", "password");
        digiSystem.addUser(user2);

        new Unit2("123", "ER");
        new Unit2("234", "ICU");
        new Unit2("435", "Medicine");
        new Unit2("448", "Medicine2");
        new Unit2("501", "Cardiology");
        new Unit2("132", "Surgery");
        new Unit2("134", "Surgery2");
        new Unit2("934", "Ophthalmology");
    }
}

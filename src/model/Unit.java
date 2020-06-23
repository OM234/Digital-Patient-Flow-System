package model;

import java.util.HashMap;
import java.util.Map;

public abstract class Unit {

    String unitID;
    String unitName;
    System system;
    Map<String, Patient> patientsOnUnit;

    public Unit(String unitID, String unitName){

        this.system = System.getInstance();

        if(system.hasUnit(unitID)){
            throw new IllegalArgumentException("Unit already exists");
        }

        this.unitID = unitID;
        this.unitName = unitName;
        patientsOnUnit = new HashMap<>();
    }
}

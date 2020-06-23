package model;

import java.util.HashMap;
import java.util.Map;

public abstract class Unit {

    private String unitID;
    private String unitName;
    private System system;
    private Map<String, Patient> patientsOnUnit;

    public Unit(String unitID, String unitName) {

        this.system = System.getInstance();

        if(system.hasUnit(unitID)){
            throw new IllegalArgumentException("Unit already exists");
        }

        this.unitID = unitID;
        this.unitName = unitName;
        patientsOnUnit = new HashMap<>();
    }

    public String getUnitID() {
        return unitID;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public boolean addPatientToUnit(String patientID){

        if(system.hasPatient(patientID) && !patientsOnUnit.containsKey(patientID)) {

            Patient patientToAdd = system.getPatient(patientID);
            patientsOnUnit.put(patientID, patientToAdd);

            return true;

        } else {

            return false;
        }
    }

    public boolean removePatientFromUnit(String patientID) {

        if(patientsOnUnit.containsKey(patientID)){

            patientsOnUnit.remove(patientID);

            return true;

        } else {

            return false;
        }
    }

    public boolean hasPatient(String patientID) {

        return patientsOnUnit.containsKey(patientID);
    }
}

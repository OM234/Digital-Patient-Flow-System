package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public /*abstract*/ class Unit2 {

    private  SimpleStringProperty unitID;
    private SimpleStringProperty unitName;
    private SimpleMapProperty<String, Patient> patientsOnUnit;
    private DigiSystem digiSystem;

    public Unit2(String unitID, String unitName) {

        this.digiSystem = DigiSystem.getInstance();

        if(digiSystem.hasUnit(unitID)){
            throw new IllegalArgumentException("Unit already exists");
        }

        this.unitID = new SimpleStringProperty(unitID);
        this.unitName = new SimpleStringProperty(unitName);
        patientsOnUnit = new SimpleMapProperty<>(FXCollections.observableHashMap());

        digiSystem.addUnit(this);
    }

    public String getUnitID() {
        return unitID.get();
    }

    public void setUnitID(String unitID) {
        this.unitID.set(unitID);
    }

    public String getUnitName() {
        return unitName.get();
    }

    public void setUnitName(String unitName) {
        this.unitName.set(unitName);
    }

    public Set<String> getUnitPatientIDs() {

        return patientsOnUnit.keySet();
    }

    public boolean addPatientToUnit(String patientID){

        if(digiSystem.hasPatient(patientID) && !patientsOnUnit.containsKey(patientID)) {

            Patient patientToAdd = digiSystem.getPatient(patientID);
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

    public int getNumPatients() {

        return patientsOnUnit.size();
    }
}


package bean;

import services.DigiServices;

import java.sql.SQLException;

public class Unit {

    private String ID;
    private String name;

    public Unit() {}

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumPatients() throws SQLException {

        DigiServices digiServices = DigiServices.getInstance();
        return digiServices.getPatientsOnUnit(this).size();
    }
}

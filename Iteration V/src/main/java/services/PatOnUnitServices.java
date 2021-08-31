package services;

import bean.Patient;
import bean.PatientOnUnit;
import bean.Unit;
import persistence.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatOnUnitServices {

    private final DAO<PatientOnUnit> patOnUnitDAO;
    private final UnitServices unitServices;
    private final PatientServices patientServices;

    public PatOnUnitServices(DAO<PatientOnUnit> patOnUnitDAO, UnitServices unitServices, PatientServices patientServices) {
        this.patOnUnitDAO = patOnUnitDAO;
        this.unitServices = unitServices;
        this.patientServices = patientServices;
    }

    public boolean addPatToUnit(Unit unit, Patient patient) throws SQLException {
        if(unitServices.hasUnit(unit) && patientServices.hasPatient(patient)) {
            PatientOnUnit patientOnUnit = new PatientOnUnit();
            patientOnUnit.setUnitID(unit.getID());
            patientOnUnit.setPatientID(patient.getID());
            patOnUnitDAO.save(patientOnUnit);
            return true;
        } else {
            return false;
        }
    }

    public boolean addPatToUnit(String unitID, String patientID) throws SQLException {

        if(unitServices.hasUnit(unitID) && patientServices.hasPatient(patientID)) {
            PatientOnUnit patientOnUnit = new PatientOnUnit();
            patientOnUnit.setUnitID(unitID);
            patientOnUnit.setPatientID(patientID);
            patOnUnitDAO.save(patientOnUnit);
            return true;
        } else {
            return false;
        }
    }

    public List<Patient> getPatientsOnUnit(Unit unit) throws SQLException {
        List<PatientOnUnit> patientsOnUnit = patOnUnitDAO.get(unit.getID());
        List<Patient> patientList = new ArrayList<>();

        patientsOnUnit.forEach(e -> {
            try {
                patientList.add(patientServices.getPatient(e.getPatientID()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        return patientList;
    }

    public List<Patient> getPatientsOnUnit(String unitID) throws SQLException {
        List<PatientOnUnit> patientsOnUnit = patOnUnitDAO.get(unitID);
        List<Patient> patientList = new ArrayList<>();

        patientsOnUnit.forEach(e -> {

            try {
                patientList.add(patientServices.getPatient(e.getPatientID()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        return patientList;
    }

    public boolean removePatientFromUnit(Patient patient, Unit unit) throws SQLException {
        PatientOnUnit patientOnUnit = new PatientOnUnit();
        patientOnUnit.setPatientID(patient.getID());
        patientOnUnit.setUnitID(unit.getID());
        patOnUnitDAO.delete(patientOnUnit);
        return true;
    }

    public boolean patientOnUnit(String unitID, String patientID) throws SQLException {
        List<Patient> patientList = getPatientsOnUnit(unitID);
        return patientList.stream().anyMatch(e -> e.getID().equals(patientID));
    }
}

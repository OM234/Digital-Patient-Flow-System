package persistence;

import bean.Patient;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO implements DAO<Patient>{

    java.sql.Connection conn;
    private static final String INSERT_SQL = "insert into patient values (?,?,?,?,?,?,?,?);";
    private static final String UPDATE_SQL = "update patient set patientID = ?, firstName = ?, lastName = ?, " +
            "height = ?, weight = ?,  BMI = ?, DOB = ?, Gender = ? where patientID = ?;";

    public PatientDAO() throws SQLException {

        conn = Connection.getInstance().getConnection();
    }

    @Override
    public List<Patient> get(String ID) throws SQLException {

        List<Patient> patients = new ArrayList<>();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from patient where patientID = \"" + ID + "\"");

        return getPatientList(patients, statement, resultSet);
    }

    @Override
    public List<Patient> getAll() throws SQLException {

        List<Patient> patients = new ArrayList<>();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from patient");


        return getPatientList(patients, statement, resultSet);
    }

    private List<Patient> getPatientList(List<Patient> patients, Statement statement, ResultSet resultSet) throws SQLException {

        while(resultSet.next()) {

            Patient patient = new Patient();
            patient.setID(resultSet.getString("patientID"));
            patient.setFirstName(resultSet.getString("firstName"));
            patient.setLastName(resultSet.getString("lastName"));
            patient.setHeight(Integer.parseInt(resultSet.getString("height")));
            patient.setWeight(Double.parseDouble(resultSet.getString("weight")));
            patient.setBMI(Double.parseDouble(resultSet.getString("BMI")));
            //patient.setDOB(resultSet.getString(""));
            patient.setGender(resultSet.getString("gender").charAt(0));
            patients.add(patient);
        }

        statement.close();
        resultSet.close();

        return patients;
    }

    @Override
    public void save(Patient patient) throws SQLException {

        PreparedStatement statement = conn.prepareStatement(INSERT_SQL);
        statement.setString(1, patient.getID());
        statement.setString(2, patient.getFirstName());
        statement.setString(3, patient.getLastName());
        statement.setDouble(4, patient.getHeight());
        statement.setDouble(5, patient.getWeight());
        statement.setDouble(6, patient.getBMI());
        statement.setString(7, null);
        statement.setString(8, Character.toString(patient.getGender()));

        statement.executeUpdate();

    }

    @Override
    public void update(Patient patient) throws SQLException {

        PreparedStatement statement = conn.prepareStatement(UPDATE_SQL);

        statement.setString(1, patient.getID());
        statement.setString(2, patient.getFirstName());
        statement.setString(3, patient.getLastName());
        statement.setDouble(4, patient.getHeight());
        statement.setDouble(5, patient.getWeight());
        statement.setDouble(6, patient.getBMI());
        statement.setString(7, null);
        statement.setString(8, Character.toString(patient.getGender()));
        statement.setString(9, patient.getID());

        statement.executeUpdate();
    }

    @Override
    public void delete(Patient patient) throws SQLException {

    }
}

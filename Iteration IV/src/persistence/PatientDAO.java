package persistence;

import model.AUser;
import model.Patient;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO implements DAO<Patient>{

    java.sql.Connection conn;

    public PatientDAO() throws SQLException {

        conn = Connection.getInstance().getConnection();
    }

    @Override
    public List<Patient> get(String ID) throws SQLException {
        return null;
    }

    @Override
    public List<Patient> getAll() throws SQLException {

        List<Patient> patients = new ArrayList<>();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from patient");

        //return getUserList(users, statement, resultSet);
        return null;
    }

    private List<User> getPatientList(List<Patient> patients, Statement statement, ResultSet resultSet) throws SQLException {

//        while(resultSet.next()) {
//
//            String userId = resultSet.getString("userID");
//            String password = resultSet.getString("password");
//            User user = new AUser(userId, password);
//            users.add(user);
//        }
//
//        statement.close();
//        resultSet.close();
//
//        return users;
        return null;
    }

    @Override
    public void save(Patient patient) throws SQLException {

    }

    @Override
    public void update(Patient patient, String[] params) {

    }

    @Override
    public void delete(Patient patient) throws SQLException {

    }
}

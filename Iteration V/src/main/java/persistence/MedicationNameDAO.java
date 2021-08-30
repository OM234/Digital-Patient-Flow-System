package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicationNameDAO implements DAO<String>{

    java.sql.Connection conn;
    private static final String GET_ALL_SQL = "select * from medication_names";

    public MedicationNameDAO() throws SQLException {

        conn = Connection.getInstance().getConnection();
    }

    @Override
    public List<String> get(String ID) throws SQLException {
        return null;
    }

    @Override
    public List<String> getAll() throws SQLException {

        List<String> medicationNames = new ArrayList<>();

        PreparedStatement statement = conn.prepareStatement(GET_ALL_SQL);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {

            medicationNames.add(resultSet.getString(1));
        }

        return medicationNames;
    }

    @Override
    public void save(String s) throws SQLException {

    }

    @Override
    public void update(String s) throws SQLException {

    }

    @Override
    public void delete(String s) throws SQLException {

    }
}

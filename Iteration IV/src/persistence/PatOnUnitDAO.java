package persistence;

import bean.PatientOnUnit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class PatOnUnitDAO implements DAO<PatientOnUnit> {

    java.sql.Connection conn;
    private static final String GET_SQL = "select * from patonunit where unitID = ?";
    private static final String GETALL_SQL = "select * from patonunit";
    private static final String INSERT_SQL = "insert into patonunit values (?,?);";
    private static final String DELETE_SQL = "delete from patonunit where patientID = ? AND unitID = ?";
    //private static final String UPDATE_SQL = "update unit set unitID = ?, name = ? where unitID = ?;";

    public PatOnUnitDAO() throws SQLException {

        conn = Connection.getInstance().getConnection();
    }

    @Override
    public List<PatientOnUnit> get(String ID) throws SQLException {

        PreparedStatement statement = conn.prepareStatement(GET_SQL);
        statement.setString(1, ID);
        ResultSet resultSet = statement.executeQuery();

        return getPatOnUnitList(resultSet);
    }

    @Override
    public List<PatientOnUnit> getAll() throws SQLException {

        PreparedStatement statement = conn.prepareStatement(GETALL_SQL);
        ResultSet resultSet = statement.executeQuery();

        return getPatOnUnitList(resultSet);
    }

    private List<PatientOnUnit> getPatOnUnitList(ResultSet resultSet) throws SQLException {

        List<PatientOnUnit> patientOnUnits = new ArrayList<>();

        while(resultSet.next()) {

            String unitID = resultSet.getString("unitID");
            String patientID = resultSet.getString("patientID");
            PatientOnUnit patientOnUnit = new PatientOnUnit();
            patientOnUnit.setUnitID(unitID);
            patientOnUnit.setPatientID(patientID);
            patientOnUnits.add(patientOnUnit);
        }

        resultSet.close();

        return patientOnUnits;
    }

    @Override
    public void save(PatientOnUnit patientOnUnit) throws SQLException {

        PreparedStatement statement = conn.prepareStatement(INSERT_SQL);

        statement.setString(1, patientOnUnit.getUnitID());
        statement.setString(2, patientOnUnit.getPatientID());

        try {

            statement.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException ex ) {

            System.out.println("Patient already on unit");
        }
    }

    @Override
    public void update(PatientOnUnit patientOnUnit) throws SQLException {

    }

    @Override
    public void delete(PatientOnUnit patientOnUnit) throws SQLException {

        PreparedStatement statement = conn.prepareStatement(DELETE_SQL);

        statement.setString(1, patientOnUnit.getPatientID());
        statement.setString(2, patientOnUnit.getUnitID());

        statement.executeUpdate();
    }
}

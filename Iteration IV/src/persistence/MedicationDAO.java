package persistence;

import bean.Medication;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicationDAO implements DAO<Medication> {

    java.sql.Connection conn;
    private static final String GET_SQL = "select * from medication where patientID = ?";
    private static final String GETALL_SQL = "select * from medication";
    private static final String INSERT_SQL = "insert into medication values (?,?,?,?,?,?,?,?,?);";
    private static final String DELETE_SQL = "delete from medication where patientID = ? AND prescriberID = ? AND medName = ?" +
            "AND route = ? AND dose = ? AND frequency = ? AND units = ? and prescribed = ? AND expires = ?";

    public MedicationDAO() throws SQLException {

        conn = Connection.getInstance().getConnection();
    }

    @Override
    public List<Medication> get(String ID) throws SQLException {

        PreparedStatement statement = conn.prepareStatement(GET_SQL);
        statement.setString(1, ID);
        ResultSet resultSet = statement.executeQuery();

        return getMedicationList(resultSet);
    }

    @Override
    public List<Medication> getAll() throws SQLException {

        PreparedStatement statement = conn.prepareStatement(GETALL_SQL);
        ResultSet resultSet = statement.executeQuery();

        return getMedicationList(resultSet);
    }

    private List<Medication> getMedicationList (ResultSet resultSet) throws SQLException {

        List<Medication> medications = new ArrayList<>();

        while(resultSet.next()) {

            Medication medication = new Medication();

            medication.setPatientID(resultSet.getString("patientID"));
            medication.setPrescriberID(resultSet.getString("prescriberID"));
            medication.setMedName(resultSet.getString("medName"));
            medication.setRoute(resultSet.getString("route"));
            medication.setDose(resultSet.getInt("dose"));
            medication.setFrequency(resultSet.getString("frequency"));
            medication.setUnits(resultSet.getString("units"));
//            medication.setPrescribed(resultSet.getString("prescribed"));
//            medication.setExpires(resultSet.getString("expires"));

            medications.add(medication);
        }

        resultSet.close();

        return medications;
    }

    @Override
    public void save(Medication medication) throws SQLException {

        PreparedStatement statement = conn.prepareStatement(INSERT_SQL);

        statement.setString(1, medication.getPatientID());
        statement.setString(2, medication.getPrescriberID());
        statement.setString(3, medication.getMedName());
        statement.setString(4, medication.getRoute());
        statement.setDouble(5, medication.getDose());
        statement.setString(6, medication.getFrequency());
        statement.setString(7, medication.getUnits());
        statement.setDate(8, Date.valueOf(medication.getPrescribed().toString()));
        statement.setDate(9, Date.valueOf(medication.getExpires().toString()));

        statement.executeUpdate();
    }

    @Override
    public void update(Medication medication) throws SQLException {

    }

    @Override
    public void delete(Medication medication) throws SQLException {

        PreparedStatement statement = conn.prepareStatement(DELETE_SQL);

        statement.setString(1, medication.getPatientID());
        statement.setString(2, medication.getPrescriberID());
        statement.setString(3, medication.getMedName());
        statement.setString(4, medication.getRoute());
        statement.setDouble(5, medication.getDose());
        statement.setString(6, medication.getFrequency());
        statement.setString(7, medication.getUnits());
        statement.setDate(8, null);
        statement.setDate(9, null);

        statement.executeUpdate();
    }
}

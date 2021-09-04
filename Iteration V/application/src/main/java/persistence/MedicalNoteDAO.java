package persistence;

import bean.MedicalNote;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MedicalNoteDAO implements DAO<MedicalNote> {

    private static final String GET_SQL = "select * from medical_note where patientID = ?";
    private static final String GETALL_SQL = "select * from medical_note";
    private static final String INSERT_SQL = "insert into medical_note values (?,?,?,?,?,?,?,?,?,?,?);";
    private static final String UPDATE_SQL = "update medical_note set deleted = ? where patientID = ? AND noteID = ?";
    java.sql.Connection conn;

    public MedicalNoteDAO() {
        try {
            conn = Connection.getInstance().getConnection();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public List<MedicalNote> get(String ID) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(GET_SQL);
        statement.setString(1, ID);
        ResultSet resultSet = statement.executeQuery();
        return getMedicalNoteList(resultSet);
    }

    @Override
    public List<MedicalNote> getAll() throws SQLException {
        PreparedStatement statement = conn.prepareStatement(GETALL_SQL);
        ResultSet resultSet = statement.executeQuery();
        return getMedicalNoteList(resultSet);
    }

    private List<MedicalNote> getMedicalNoteList(ResultSet resultSet) throws SQLException {
        List<MedicalNote> medicalNotes = new ArrayList<>();
        while (resultSet.next()) {
            MedicalNote medicalNote = new MedicalNote();
            medicalNote.setPatientID(resultSet.getString("patientID"));
            medicalNote.setWriterID(resultSet.getString("writerID"));
            medicalNote.setPulse(resultSet.getInt("pulse"));
            medicalNote.setNoteID(resultSet.getInt("noteID"));
            medicalNote.setO2Sat(resultSet.getInt("o2sat"));
            medicalNote.setSbp(resultSet.getInt("sbp"));
            medicalNote.setDbp(resultSet.getInt("dbp"));
            medicalNote.setTemp(resultSet.getDouble("temp"));
            medicalNote.setDeleted(resultSet.getBoolean("deleted"));
            medicalNote.setNote(resultSet.getString("note"));
            medicalNote.setDate(LocalDate.parse(resultSet.getString("date")));
            medicalNotes.add(medicalNote);
        }
        resultSet.close();
        return medicalNotes;
    }

    @Override
    public void save(MedicalNote medicalNote) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(INSERT_SQL);
        statement.setString(1, medicalNote.getPatientID());
        statement.setString(2, medicalNote.getWriterID());
        statement.setInt(3, medicalNote.getPulse());
        statement.setInt(4, medicalNote.getNoteID());
        statement.setInt(5, medicalNote.getO2Sat());
        statement.setInt(6, medicalNote.getSbp());
        statement.setInt(7, medicalNote.getDbp());
        statement.setDouble(8, medicalNote.getTemp());
        statement.setBoolean(9, medicalNote.isDeleted());
        statement.setString(10, medicalNote.getNote());
        statement.setDate(11, Date.valueOf(medicalNote.getDate()));
        statement.executeUpdate();
    }

    @Override
    public void update(MedicalNote medicalNote) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(UPDATE_SQL);
        statement.setBoolean(1, medicalNote.isDeleted());
        statement.setString(2, medicalNote.getPatientID());
        statement.setInt(3, medicalNote.getNoteID());
        statement.executeUpdate();
    }

    @Override
    public void delete(MedicalNote medicalNote) throws SQLException {
    }
}

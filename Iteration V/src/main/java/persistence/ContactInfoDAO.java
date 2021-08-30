package persistence;

import bean.ContactInfo;
import bean.PatientOnUnit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactInfoDAO implements DAO<ContactInfo>{

    java.sql.Connection conn;
    private static final String GET_SQL = "select * from contact_info where patientID = ?";
    private static final String GETALL_SQL = "select * from contact_info";
    private static final String INSERT_SQL = "insert into contact_info values (?,?,?,?,?,?,?,?,?);";
    private static final String DELETE_SQL = "delete from contact_info where patientID = ?";
    private static final String UPDATE_SQL = "update contact_info set patientID = ?, streetNumber = ?, streetName = ?,postalCode = ?," +
            "city = ?, province = ?, country = ?, phoneNumber = ?, email = ? where patientID = ?";

    public ContactInfoDAO() throws SQLException {

        conn = Connection.getInstance().getConnection();
    }

    @Override
    public List<ContactInfo> get(String ID) throws SQLException {

        PreparedStatement statement = conn.prepareStatement(GET_SQL);
        statement.setString(1, ID);
        ResultSet resultSet = statement.executeQuery();

        return getContactInfoList(resultSet);
    }

    @Override
    public List<ContactInfo> getAll() throws SQLException {

        PreparedStatement statement = conn.prepareStatement(GETALL_SQL);
        ResultSet resultSet = statement.executeQuery();

        return getContactInfoList(resultSet);
    }

    private List<ContactInfo> getContactInfoList(ResultSet resultSet) throws SQLException {

        List<ContactInfo> contactInfos = new ArrayList<>();

        while(resultSet.next()) {

            ContactInfo contactInfo = new ContactInfo();

            contactInfo.setPatientID(resultSet.getString("patientID"));
            contactInfo.setStreetNumber(resultSet.getString("streetNumber"));
            contactInfo.setStreetName(resultSet.getString("streetName"));
            contactInfo.setPostalCode(resultSet.getString("postalCode"));
            contactInfo.setCity(resultSet.getString("city"));
            contactInfo.setProvince(resultSet.getString("province"));
            contactInfo.setCountry(resultSet.getString("country"));
            contactInfo.setPhoneNumber(resultSet.getString("phoneNumber"));
            contactInfo.setEmail(resultSet.getString("email"));

            contactInfos.add(contactInfo);
        }

        resultSet.close();

        return contactInfos;
    }

    @Override
    public void save(ContactInfo contactInfo) throws SQLException {

        PreparedStatement statement = conn.prepareStatement(INSERT_SQL);

        statement.setString(1, contactInfo.getPatientID());
        statement.setString(2, contactInfo.getStreetNumber());
        statement.setString(3, contactInfo.getStreetName());
        statement.setString(4, contactInfo.getPostalCode());
        statement.setString(5, contactInfo.getCity());
        statement.setString(6, contactInfo.getProvince());
        statement.setString(7, contactInfo.getCountry());
        statement.setString(8, contactInfo.getPhoneNumber());
        statement.setString(9, contactInfo.getEmail());

        statement.executeUpdate();
    }

    @Override
    public void update(ContactInfo contactInfo) throws SQLException {

        PreparedStatement statement = conn.prepareStatement(UPDATE_SQL);

        statement.setString(1, contactInfo.getPatientID());
        statement.setString(2, contactInfo.getStreetNumber());
        statement.setString(3, contactInfo.getStreetName());
        statement.setString(4, contactInfo.getPostalCode());
        statement.setString(5, contactInfo.getCity());
        statement.setString(6, contactInfo.getProvince());
        statement.setString(7, contactInfo.getCountry());
        statement.setString(8, contactInfo.getPhoneNumber());
        statement.setString(9, contactInfo.getEmail());
        statement.setString(10, contactInfo.getPatientID());

        statement.executeUpdate();
    }

    @Override
    public void delete(ContactInfo contactInfo) throws SQLException {

        PreparedStatement statement = conn.prepareStatement(DELETE_SQL);

        statement.executeUpdate();
    }
}

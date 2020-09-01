package persistence;

import model.AUser;
import model.User;

import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements DAO<User> {


    java.sql.Connection conn;

    public UserDAO() throws SQLException {

        conn = Connection.getInstance().getConnection();
    }

    @Override
    public List<User> get(String ID) throws SQLException {

        List<User> users = new ArrayList<>();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from user where userID = \"" + ID + "\";");

        while(resultSet.next()) {

            String userID = resultSet.getString("userID");
            String password = resultSet.getString("password");
            User user = new AUser(userID, password);
            users.add(user);
        }

        statement.close();
        resultSet.close();

        return users;
    }

    @Override
    public List<User> getAll() throws SQLException {

        List<User> allUsers = new ArrayList<>();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from user");

        while(resultSet.next()) {
            String userId = resultSet.getString("userID");
            String password = resultSet.getString("password");
            User user = new AUser(userId, password);
            allUsers.add(user);
        }

        statement.close();
        resultSet.close();

        return allUsers;
    }

    @Override
    public void save(User user) throws SQLException {

        Statement statement = conn.createStatement();
        statement.execute("insert into user values (\""+ user.getUserID() +"\", \""+ user.getPassword() +"\");");

        statement.close();
    }

    @Override
    public void update(User user, String[] params) {

    }

    @Override
    public void delete(User user) {

    }
}

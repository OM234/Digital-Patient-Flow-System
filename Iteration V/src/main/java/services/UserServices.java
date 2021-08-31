package services;

import bean.User;
import persistence.DAO;

import java.sql.SQLException;
import java.util.List;

public class UserServices {

    private final DAO<User> userDAO;
    private User currentUser;

    public UserServices(DAO<User> userDAO) {
        this.userDAO = userDAO;
    }

    public boolean addUser(User user) throws SQLException {
        if(user == null) return false;

        String userID = user.getID();

        if(!userDAO.get(user.getID()).isEmpty()) {
            return false;
        } else {
            userDAO.save(user);
            return true;
        }
    }

    public List<User> getAllUsers() throws SQLException {
        return userDAO.getAll();
    }

    public void setCurrentUser(User user) throws SQLException {
        if(userDAO.get(user.getID()).isEmpty()) {
            try {
                throw new Exception("User does not exist");
            } catch(Exception e) {
                System.out.println("user does not exist");
                System.exit(0);
            }
        }
        currentUser = userDAO.get(user.getID()).get(0);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean passChecker(String userID, String password) throws SQLException{
        if(!userDAO.get(userID).isEmpty() && userDAO.get(userID).get(0).getPassword().equals(password)) {
            setCurrentUser(userDAO.get(userID).get(0));
            return true;
        } else {
            return false;
        }
    }
}

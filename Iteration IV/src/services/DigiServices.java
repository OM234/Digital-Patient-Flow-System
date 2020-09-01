package services;

import model.User;
import org.jetbrains.annotations.NotNull;
import persistence.UserDAO;
import java.sql.SQLException;

public class DigiServices {

    private static DigiServices digiServices = null;
    private UserDAO userDAO;
    private User currentUser;

    private DigiServices() throws SQLException {

        currentUser = null;
        userDAO = new UserDAO();
    }

    public static DigiServices getInstance() throws SQLException {

        if(digiServices == null) {

            digiServices = new DigiServices();
        }

        return digiServices;
    }

    public boolean addUser(User user) throws SQLException {

        if(user == null) return false;

        String userID = user.getUserID();

        if(!userDAO.get(user.getUserID()).isEmpty()) {

            return false;

        } else {

            userDAO.save(user);
            return true;
        }
    }

    public void setCurrentUser(User user) throws SQLException {

        if(userDAO.get(user.getUserID()).isEmpty()) {

            try {
                throw new Exception("User does not exist");

            } catch(Exception e) {

                System.out.println("user does not exist");
                System.exit(0);
            }
        }

        currentUser = userDAO.get(user.getUserID()).get(0);
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

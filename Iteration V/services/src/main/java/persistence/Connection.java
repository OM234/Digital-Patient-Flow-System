package persistence;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

class Connection {

    private static Connection singleInstance = null;
    private static java.sql.Connection conn;
    String userName = "user";
    String password = "password";
    String dbms = "mysql";
    String serverName = "localhost";
    String portNumber = "3307";

    private Connection() {
        conn = null;
    }

    public static Connection getInstance() throws SQLException {
        if(singleInstance == null) {
            singleInstance = new Connection();
            singleInstance.getConnection();
        }
        return singleInstance;
    }

    public java.sql.Connection getConnection() throws SQLException {
        if(conn != null) {
            return conn;
        }

        Properties connectionProps = new Properties();
        connectionProps.put("user", this.userName);
        connectionProps.put("password", this.password);

        conn = DriverManager.getConnection(
                "jdbc:" + this.dbms + "://" +
                        this.serverName +
                        ":" + this.portNumber + "/digihealth",
                connectionProps);

        System.out.println("Connected to database");
        return conn;
    }
}

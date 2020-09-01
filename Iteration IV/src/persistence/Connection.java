package persistence;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connection {

    String userName = "root";
    String password = "MyNewPass";
    String dbms = "mysql";
    String serverName = "localhost";
    String portNumber = "3306";

    public java.sql.Connection getConnection() throws SQLException, ClassNotFoundException {


//        Class.forName("oracle.jdbc.driver.OracleDriver");

        java.sql.Connection conn = null;
//        Properties connectionProps = new Properties();
//        connectionProps.put("user", this.userName);
//        connectionProps.put("password", this.password);
//
//        conn = DriverManager.getConnection(
//                "jdbc:" + this.dbms + "://" +
//                        this.serverName +
//                        ":" + this.portNumber + "/",
//                connectionProps);
//
//        System.out.println("Connected to database");
        return conn;
    }
}

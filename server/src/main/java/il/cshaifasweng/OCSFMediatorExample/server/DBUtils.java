package il.cshaifasweng.OCSFMediatorExample.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    private static final String URL = "jdbc:mysql://localhost:3306/flowershop?serverTimezone=UTC";
    private static final String USER = "root";      //DB user
    private static final String PASSWORD = "IamALegend1!";  // DB Pass

    // basic get method just connects to the DB
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

package jm.task.core.jdbc.util;

import com.mysql.cj.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/dbtest";
    private static final String USERNAME = "root";
    private static final String PASSWORD = ",e2??wQ1:ss";

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }
}

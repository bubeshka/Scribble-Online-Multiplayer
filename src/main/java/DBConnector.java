package main.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DBConnector {
    protected Connection connection;

    public DBConnector(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    DatabaseCredentials.url,
                    DatabaseCredentials.user,
                    DatabaseCredentials.password);
            Statement useDbStm = connection.createStatement();
            useDbStm.executeQuery("USE SCRIBBLE;");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * Closes connection to the database
     */
    public void closeConnection() throws SQLException {
        connection.close();
    }
}

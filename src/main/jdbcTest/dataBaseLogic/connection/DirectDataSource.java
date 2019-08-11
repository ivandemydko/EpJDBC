package main.jdbcTest.dataBaseLogic.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;


public class DirectDataSource implements main.jdbcTest.dataBaseLogic.connection.DataSource {

    private Connection connection;

    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EPAMdb?serverTimezone=UTC",
                    "root", "root");
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeConnection() {
        try {
            if (!connection.isClosed()) {
                connection.close();
            } else {
                System.err.println("connection is closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeConnection(Connection connection) {
        closeConnection();
    }
}

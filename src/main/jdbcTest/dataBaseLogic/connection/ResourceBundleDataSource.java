package main.jdbcTest.dataBaseLogic.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ResourceBundleDataSource implements main.jdbcTest.dataBaseLogic.connection.DataSource {
    private Connection connection;
    private ResourceBundle resource;


    public ResourceBundleDataSource() {
        resource = ResourceBundle.getBundle("EPAMdb");
    }

    public ResourceBundleDataSource(String bankName) {
        resource = ResourceBundle.getBundle(bankName);
    }


    public Connection getConnection() {
        String url = resource.getString("url");
        String user = resource.getString("user");
        String pass = resource.getString("password");
        try {
            return connection = DriverManager.getConnection(url, user, pass);
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

    }
}

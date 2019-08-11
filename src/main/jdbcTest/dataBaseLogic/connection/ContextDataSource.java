package main.jdbcTest.dataBaseLogic.connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ContextDataSource implements main.jdbcTest.dataBaseLogic.connection.DataSource {

    private Connection connection;

    public Connection getConnection() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource dataSource = (DataSource) envContext.lookup("jdbc/EPAMdb");
            connection = dataSource.getConnection();
            System.out.println("datasource " + dataSource);
            System.out.println("connection " + connection);
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
        return connection;
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

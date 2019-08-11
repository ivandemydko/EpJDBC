package main.jdbcTest.dataBaseLogic.connection;

import java.sql.Connection;

public interface DataSource {

    Connection getConnection();
    void closeConnection();
    void closeConnection(Connection connection);
}

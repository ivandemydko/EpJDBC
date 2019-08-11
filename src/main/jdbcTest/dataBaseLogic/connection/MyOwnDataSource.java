package main.jdbcTest.dataBaseLogic.connection;

import main.jdbcTest.dataBaseLogic.connection.exception.MyTimeOutException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;


public class MyOwnDataSource implements main.jdbcTest.dataBaseLogic.connection.DataSource {
    private Deque<Connection> connections;
    private ResourceBundle resource;
    private volatile static MyOwnDataSource instance;

    public static MyOwnDataSource getInstance() {
        if (instance == null) {
            synchronized (MyOwnDataSource.class) {
                if (instance == null) {
                    instance = new MyOwnDataSource();
                }
            }
        }
        return instance;
    }

    private MyOwnDataSource() {
        resource = ResourceBundle.getBundle("EPAMdb");
        String url = resource.getString("url");
        String user = resource.getString("user");
        String pass = resource.getString("password");
        String capacity = resource.getString("pool.capacity");
        int size = Integer.parseInt(capacity);
        connections = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(url, user, pass);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connections.push(connection);
        }
    }

    @Override
    public Connection getConnection() {
        synchronized (this) {
            if (!connections.isEmpty()) {
                return connections.pop();
            }
        }
        System.out.println("The connection pool is empty, wait...");
        String waitTime = resource.getString("maxWaitMillis");
        int time = Integer.parseInt(waitTime);

        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (this) {
            if (!connections.isEmpty()) {
                return connections.pop();
            }
        }
        throw new MyTimeOutException("Server is overloaded, try again latter");
    }


    @Override
    public void closeConnection() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void closeConnection(Connection connection) {
         synchronized (this){
        connections.push(connection);
         }
    }

    public int getConnectionSize() {
        System.out.println(connections.size());
        for (Connection c : connections) {
            System.out.println(c);
        }
        return connections.size();
    }
}

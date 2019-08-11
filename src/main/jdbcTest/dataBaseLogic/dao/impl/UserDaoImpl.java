package main.jdbcTest.dataBaseLogic.dao.impl;

import main.jdbcTest.dataBaseLogic.connection.ContextDataSource;
import main.jdbcTest.dataBaseLogic.connection.DataSource;
import main.jdbcTest.dataBaseLogic.connection.MyOwnDataSource;
import main.jdbcTest.dataBaseLogic.connection.ResourceBundleDataSource;
import main.jdbcTest.dataBaseLogic.dao.interfaces.UserDao;
import main.jdbcTest.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDaoImpl implements UserDao {

    private DataSource dataSource;
    private Connection connection;

    public UserDaoImpl() {
        dataSource = MyOwnDataSource.getInstance();
       // dataSource = new ContextDataSource();
        connection = dataSource.getConnection();
    }

    public UserDaoImpl(String dataBaseName) {
        dataSource = new ResourceBundleDataSource(dataBaseName);
        connection = dataSource.getConnection();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM user");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                double balance = rs.getDouble("balance");
                long userTypeId = rs.getLong("userType_id");

                User user = new User();
                user.setId(id);
                user.setName(name);
                user.setLastName(lastName);
                user.setBalance(balance);
                user.setUserTypeId(userTypeId);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("Statement doesn't exist");
            }
            dataSource.closeConnection(connection);
        }
        return users;
    }

    @Override
    public User getUserById(long id) {
        PreparedStatement statement = null;
        User user = null;
        try {
            statement = connection.prepareStatement("SELECT*from user where id=?");
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            String name = rs.getString("name");
            String lastName = rs.getString("lastName");
            double balance = rs.getDouble("balance");
            long userTypeId = rs.getLong("userType_id");
            user = new User();
            user.setId(id);
            user.setName(name);
            user.setName(lastName);
            user.setBalance(balance);
            user.setUserTypeId(userTypeId);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("Statement doesn't exist");
            }
            dataSource.closeConnection(connection);
        }
        return user;
    }

    @Override
    public void addUser(User user) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT User(name, lastName,balance, userType_id) values (?,?,?,?)");
            statement.setString(1, user.getName());
            statement.setString(2, user.getLastName());
            statement.setDouble(3, user.getBalance());
            statement.setLong(4, user.getUserTypeId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            dataSource.closeConnection(connection);
        }
    }

    @Override
    public void deleteUser(User user) {

    }
}

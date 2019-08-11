package main.jdbcTest.dataBaseLogic.dao.interfaces;

import main.jdbcTest.entity.User;

import java.sql.Connection;
import java.util.List;

public interface UserDao {

    List<User> getAllUsers();

    User getUserById(long id);

    void addUser(User user);

    void deleteUser(User user);

   // Connection getConnection();
}

package main.jdbcTest.dataBaseLogic.dao.interfaces;

import main.jdbcTest.entity.UserType;

import java.util.List;

public interface UserTypeDao {

    List<UserType> getAllUserTypes();

    UserType getUserTypeById(long id);

    void insertUserType(String name);

    void deleteUserType(String name);
}

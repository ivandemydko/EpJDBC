package main.jdbcTest.dataBaseLogic.factory;

import main.jdbcTest.dataBaseLogic.dao.impl.UserDaoImpl;
import main.jdbcTest.dataBaseLogic.dao.impl.UserTypeDaoImpl;
import main.jdbcTest.dataBaseLogic.dao.interfaces.UserDao;
import main.jdbcTest.dataBaseLogic.dao.interfaces.UserTypeDao;

public class DaoFactory {

    public static UserDao getUserDao() {
        return new UserDaoImpl();
    }

    public static UserDao getUserDao(String dataBaseName) {
        return new UserDaoImpl(dataBaseName);
    }

    public static UserTypeDao getUserTypeDao() {
        return new UserTypeDaoImpl();
    }
}

package main.jdbcTest.businessLogic;

import main.jdbcTest.dataBaseLogic.connection.MyOwnDataSource;
import main.jdbcTest.dataBaseLogic.connection.ResourceBundleDataSource;
import main.jdbcTest.dataBaseLogic.dao.impl.UserDaoImpl;
import main.jdbcTest.dataBaseLogic.dao.interfaces.UserDao;
import main.jdbcTest.dataBaseLogic.factory.DaoFactory;
import main.jdbcTest.dataBaseLogic.transaction.Transaction;
import main.jdbcTest.entity.User;


public class Main {
    public static void main(String[] args) {

        doTransaction(1, "bankA", 2, "bankB", 10);
 //       myDataSourceInMultiThreading(1000);
//        User user = new User();
//        user.setName("Vova");
//        user.setLastName("Zelensky");
//        user.setBalance(45.67);
//        user.setUserTypeId(1L);
//        addUser(user,"bankA");
    }

    public static void getAllUsers() {
        System.out.println("In getAllUsers();");
        UserDao userDao = DaoFactory.getUserDao();
        userDao.getAllUsers().forEach(System.out::println);
    }

    public static void doTransaction(long idUserFrom, String bankFrom, long idUserTo, String bankTo, double sum) {
        Transaction.getInstance().doTransaction(idUserFrom, bankFrom, idUserTo, bankTo, sum);
    }


    public static void myDataSourceInMultiThreading(int threads) {
        MyOwnDataSource dataSource = MyOwnDataSource.getInstance();
        dataSource.getConnectionSize();
        for (int i = 0; i < threads; i++) {
            new Thread(() -> {
                new UserDaoImpl().getAllUsers().forEach(System.out::println);
                System.out.println();
            }).start();
        }
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dataSource.getConnectionSize();
    }

    public static void addUser(User user, String database) {
        UserDao userDao = DaoFactory.getUserDao(database);
        userDao.addUser(user);
    }
}

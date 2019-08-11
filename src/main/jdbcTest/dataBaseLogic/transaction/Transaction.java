package main.jdbcTest.dataBaseLogic.transaction;


import main.jdbcTest.dataBaseLogic.connection.DataSource;
import main.jdbcTest.dataBaseLogic.connection.ResourceBundleDataSource;


import java.sql.*;

public class Transaction {

    private static Transaction transaction;

    private Connection connectionFrom;
    private Connection connectionTo;

    private Transaction() {
    }

    synchronized public static Transaction getInstance() {
        if (transaction == null) {
            transaction = new Transaction();
        }
        return transaction;
    }


    public void doTransaction(Long idUserFrom, String bankFrom, Long idUserTo, String bankTo, double sum) {
        if (sum <= 0) {
            throw new NumberFormatException("less or equals zero");
        }
        PreparedStatement statement = null;

        try {
            statement = getConnectionFrom(bankFrom).prepareStatement("select balance from user WHERE id=?");
            statement.setLong(1, idUserFrom);
            ResultSet rs = statement.executeQuery();
            rs.next();
            double userFromBalance = rs.getDouble(1);
            if (userFromBalance < sum) {
                throw new SQLException("Invalid balance");
            }
            statement = getConnectionTo(bankTo).prepareStatement("select balance from user WHERE id=?");
            statement.setLong(1, idUserTo);
            rs = statement.executeQuery();
            rs.next();
            double userToBalance = rs.getDouble(1);
            userFromBalance -= sum;
            userToBalance += sum;
            statement = connectionFrom.prepareStatement("UPDATE user SET balance=? where id=?");
            statement.setDouble(1, userFromBalance);
            statement.setLong(2, idUserFrom);
            statement.executeUpdate();
            statement = connectionTo.prepareStatement("UPDATE user SET balance=? where id=?");
            statement.setDouble(1, userToBalance);
            statement.setLong(2, idUserTo);
            statement.executeUpdate();
            connectionFrom.commit();
            connectionTo.commit();
        } catch (SQLException e) {
            System.err.println("SQLState: " + e.getSQLState() + " Error Message: " + e.getMessage());
            try {
                if (connectionFrom != null)
                    connectionFrom.rollback();
            } catch (SQLException e1) {
                System.err.println("Can't rollback");
                e1.printStackTrace();
            }
            try {
                if (connectionTo != null)
                    connectionTo.rollback();
            } catch (SQLException e1) {
                System.err.println("Can't rollback");
                e1.printStackTrace();
            }
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connectionFrom != null)
                    connectionFrom.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connectionTo != null)
                    connectionTo.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Connection getConnectionFrom(String bankFrom) {
        DataSource dataSource = new ResourceBundleDataSource(bankFrom);
        connectionFrom = dataSource.getConnection();
        try {
            connectionFrom.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connectionFrom;
    }

    private Connection getConnectionTo(String bankTo) {
        DataSource dataSource = new ResourceBundleDataSource(bankTo);
        connectionTo = dataSource.getConnection();
        try {
            connectionTo.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connectionTo;
    }
}

package main.jdbcTest.dataBaseLogic.connection.exception;

public class MyTimeOutException extends RuntimeException {

    public MyTimeOutException() {
    }

    public MyTimeOutException(String message) {
        super(message);
    }

    public MyTimeOutException(String message,Throwable t) {
        super(message,t);
    }
}

package org.example;

public class DaoException extends Exception {

    private static final long serialVersionUID = -7669751088704144947L;

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

}

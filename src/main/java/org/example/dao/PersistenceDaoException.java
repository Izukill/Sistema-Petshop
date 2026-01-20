package org.example.dao;

import org.example.DaoException;

public class PersistenceDaoException extends DaoException {

  private static final long serialVersionUID = 7159282553688713660L;

  public PersistenceDaoException(String message) {
    super(message);
  }

  public PersistenceDaoException(String message, Throwable cause) {
    super(message, cause);
  }

}

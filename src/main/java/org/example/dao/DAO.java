package org.example.dao;

import java.util.List;

public interface DAO<E, T> {

    void save(E obj) throws PersistenceDaoException;

    E update(E obj) throws PersistenceDaoException;

    void delete(T primaryKey) throws PersistenceDaoException;

    E getByID(T primaryKey) throws PersistenceDaoException;

    List<E> getAll() throws PersistenceDaoException;
}

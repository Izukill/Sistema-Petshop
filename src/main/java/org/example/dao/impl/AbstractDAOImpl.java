package org.example.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import org.example.dao.DAO;
import org.example.dao.EntidadeAtivavel;
import org.example.dao.PersistenceDaoException;

import java.util.List;

public abstract class AbstractDAOImpl<E, T> implements DAO<E, T> {

    private EntityManagerFactory emf;

    private Class<E> entityClass;

    public AbstractDAOImpl(Class<E> entityClass, EntityManagerFactory emf) {
        this.entityClass = entityClass;
        this.emf = emf;
    }

    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void save(E obj) throws PersistenceDaoException {
        try(EntityManager em = getEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            try {
                em.persist(obj);
                transaction.commit();
            } catch (PersistenceException pe) {
                pe.printStackTrace();
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw new PersistenceDaoException("Ocorreu algum erro ao tentar salvar a entidade.", pe);
            }
        }
    }

    @Override
    public E update(E obj) throws PersistenceDaoException {
        try(EntityManager em = getEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            try {
                E resultado = em.merge(obj);
                transaction.commit();
                return resultado;
            } catch (PersistenceException pe) {
                pe.printStackTrace();
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw new PersistenceDaoException("Ocorreu algum erro ao tentar atualizar a entidade.", pe);
            }
        }
    }

    @Override
    public void delete(T primaryKey) throws PersistenceDaoException {
        try(EntityManager em = getEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            try {

                E obj = em.find(this.entityClass, primaryKey);

                if (obj != null) {

                    if (obj instanceof EntidadeAtivavel) {
                        ((EntidadeAtivavel) obj).setAtivo(false);
                        em.merge(obj);
                    } else {
                        em.remove(obj);
                    }
                    transaction.commit();
                }
            } catch (PersistenceException pe) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw new PersistenceDaoException("Ocorreu algum erro ao tentar remover a entidade.", pe);
            }
        }
    }

    @Override
    public E getByID(T primaryKey) throws PersistenceDaoException {
        try(EntityManager em = getEntityManager()) {
            try {
                return em.find(this.entityClass, primaryKey);
            } catch (PersistenceException pe) {
                pe.printStackTrace();
                throw new PersistenceDaoException("Ocorreu algum erro ao tentar recuperar a entidade com base no ID.", pe);
            }
        }
    }

    @Override
    public List<E> getAll() throws PersistenceDaoException {
        try(EntityManager em = getEntityManager()) {
            try {
                TypedQuery<E> query = em.createQuery(String.format("SELECT obj FROM %s obj", this.entityClass.getSimpleName()), this.entityClass);
                return query.getResultList();
            } catch (PersistenceException pe) {
                pe.printStackTrace();
                throw new PersistenceDaoException("Ocorreu algum erro ao tentar recuperar todas as instâncias da entidade.", pe);
            }
        }
    }

}
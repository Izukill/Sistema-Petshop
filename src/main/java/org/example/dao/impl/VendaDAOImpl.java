package org.example.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.example.dao.PersistenceDaoException;
import org.example.dao.VendaDAO;
import org.example.entidades.Venda;

import java.time.LocalDate;
import java.util.List;

public class VendaDAOImpl extends AbstractDAOImpl<Venda, Integer> implements VendaDAO {


    public VendaDAOImpl(EntityManagerFactory emf) {
        super(Venda.class, emf);
    }

    //busca para os relatórios
    @Override
    public List<Venda> buscarPorPeriodo(LocalDate inicio, LocalDate fim) throws PersistenceDaoException {
        try (EntityManager em = getEntityManager()) {

            String jpql = "SELECT v FROM Venda v WHERE v.data BETWEEN :inicio AND :fim";
            TypedQuery<Venda> query = em.createQuery(jpql, Venda.class);
            query.setParameter("inicio", inicio);
            query.setParameter("fim", fim);
            return query.getResultList();
        } catch (Exception e) {
            throw new PersistenceDaoException("Erro ao buscar vendas por período", e);
        }
    }
}

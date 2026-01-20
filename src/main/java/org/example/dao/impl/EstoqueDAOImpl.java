package org.example.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.example.dao.EstoqueDAO;
import org.example.dao.PersistenceDaoException;
import org.example.entidades.Estoque;
import org.example.entidades.ItemEstoque;

public class EstoqueDAOImpl extends AbstractDAOImpl<Estoque, Integer> implements EstoqueDAO {

    public EstoqueDAOImpl(EntityManagerFactory emf) {
        super(Estoque.class, emf);
    }

    @Override
    public void inserirItem(ItemEstoque item) throws PersistenceDaoException {
        try (EntityManager em = getEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            try {
                if (item.getEstoque() != null) {
                    em.persist(item);

                } else {
                    throw new PersistenceDaoException("O item deve estar vinculado a um Estoque existente.");
                }
                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) transaction.rollback();
                throw new PersistenceDaoException("Erro ao inserir item no estoque.", e);
            }
        }
    }

    @Override
    public void removerItem(ItemEstoque item, int qtd) throws PersistenceDaoException {
        try (EntityManager em = getEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            try {
                ItemEstoque itemBanco = em.find(ItemEstoque.class, item.getId());

                if (itemBanco != null) {
                    int novaQtd = itemBanco.getQuantidadeAtual() - qtd;

                    if (novaQtd < 0) {
                        throw new PersistenceDaoException("Quantidade insuficiente em estoque para remoção.");
                    }

                    itemBanco.setQuantidadeAtual(novaQtd);
                    em.merge(itemBanco);

                } else {
                    throw new PersistenceDaoException("Item não encontrado no banco de dados.");
                }

                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) transaction.rollback();
                throw new PersistenceDaoException("Erro ao remover quantidade do item.", e);
            }
        }
    }
}

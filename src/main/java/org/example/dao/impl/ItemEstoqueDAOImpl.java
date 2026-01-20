package org.example.dao.impl;

import jakarta.persistence.EntityManagerFactory;
import org.example.dao.ItemEstoqueDAO;
import org.example.entidades.ItemEstoque;

public class ItemEstoqueDAOImpl extends AbstractDAOImpl<ItemEstoque, Integer> implements ItemEstoqueDAO {


    public ItemEstoqueDAOImpl(EntityManagerFactory emf) {
        super(ItemEstoque.class, emf);
    }
}

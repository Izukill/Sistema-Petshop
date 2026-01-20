package org.example.dao.impl;

import jakarta.persistence.EntityManagerFactory;
import org.example.dao.ItemVendaDAO;
import org.example.entidades.ItemVenda;

public class ItemVendaDAOImpl extends AbstractDAOImpl<ItemVenda, Integer> implements ItemVendaDAO {

    public ItemVendaDAOImpl(EntityManagerFactory emf) {
        super(ItemVenda.class, emf);
    }
}

package org.example.dao.impl;

import jakarta.persistence.EntityManagerFactory;
import org.example.dao.ProdutoDAO;
import org.example.entidades.Produto;

public class ProdutoDAOImpl extends AbstractDAOImpl<Produto,Integer> implements ProdutoDAO {


    public ProdutoDAOImpl(EntityManagerFactory emf) {
        super(Produto.class, emf);
    }
}

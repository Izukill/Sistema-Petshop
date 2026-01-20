package org.example.dao.impl;

import jakarta.persistence.EntityManagerFactory;
import org.example.dao.FornecedorDAO;
import org.example.entidades.Fornecedor;

public class ForcenedorDAOImpl extends AbstractDAOImpl<Fornecedor, Integer> implements FornecedorDAO {


    public ForcenedorDAOImpl(EntityManagerFactory emf) {
        super(Fornecedor.class, emf);
    }
}

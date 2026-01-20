package org.example.dao.impl;

import jakarta.persistence.EntityManagerFactory;
import org.example.dao.ClienteDAO;
import org.example.entidades.Cliente;

public class ClienteDAOImpl extends AbstractDAOImpl<Cliente, Integer> implements ClienteDAO {


    public ClienteDAOImpl(EntityManagerFactory emf) {
        super(Cliente.class, emf);
    }
}

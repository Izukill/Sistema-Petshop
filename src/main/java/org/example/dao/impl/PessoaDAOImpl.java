package org.example.dao.impl;

import jakarta.persistence.EntityManagerFactory;
import org.example.dao.PessoaDAO;
import org.example.entidades.Pessoa;

public class PessoaDAOImpl extends AbstractDAOImpl<Pessoa, Integer> implements PessoaDAO {


    public PessoaDAOImpl(EntityManagerFactory emf) {
        super(Pessoa.class, emf);
    }
}

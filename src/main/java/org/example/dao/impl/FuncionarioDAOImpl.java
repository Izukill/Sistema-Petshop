package org.example.dao.impl;

import jakarta.persistence.EntityManagerFactory;
import org.example.dao.FuncionarioDAO;
import org.example.entidades.Funcionario;

public class FuncionarioDAOImpl extends AbstractDAOImpl<Funcionario, Integer> implements FuncionarioDAO {

    public FuncionarioDAOImpl(EntityManagerFactory emf) {
        super(Funcionario.class, emf);
    }
}

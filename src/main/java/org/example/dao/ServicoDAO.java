package org.example.dao;

import org.example.entidades.Servico;

import java.time.LocalDate;

public interface ServicoDAO extends DAO<Servico, Integer>{

    void agendar(Servico servico) throws PersistenceDaoException;

    boolean verificarDisponibilidade(LocalDate data, Integer idFuncionario) throws PersistenceDaoException;

    String consultarStatus(Integer id) throws PersistenceDaoException;

    void atualizarStatus(Integer id, String novoStatus) throws PersistenceDaoException;
}

package org.example.dao;

import org.example.entidades.Venda;

import java.time.LocalDate;
import java.util.List;

public interface VendaDAO extends DAO<Venda, Integer>{


    List<Venda> buscarPorPeriodo(LocalDate inicio, LocalDate fim) throws PersistenceDaoException;


}

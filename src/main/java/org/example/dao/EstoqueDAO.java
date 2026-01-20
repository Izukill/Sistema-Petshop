package org.example.dao;

import org.example.entidades.Estoque;
import org.example.entidades.ItemEstoque;

public interface EstoqueDAO extends DAO<Estoque,Integer>{

    void inserirItem(ItemEstoque item) throws PersistenceDaoException;

    void removerItem(ItemEstoque item, int qtd) throws PersistenceDaoException;


}

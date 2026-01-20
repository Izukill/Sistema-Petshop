package org.example.dao;

import org.example.entidades.Cliente;
import org.example.entidades.Pet;

import java.util.List;

public interface PetDAO extends DAO<Pet, Integer>{

    public List<Pet> buscarPorDono(Cliente c) throws PersistenceDaoException;
}

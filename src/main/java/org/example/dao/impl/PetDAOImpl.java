package org.example.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.example.dao.PersistenceDaoException;
import org.example.dao.PetDAO;
import org.example.entidades.Cliente;
import org.example.entidades.Pet;

import java.util.List;

public class PetDAOImpl extends AbstractDAOImpl<Pet, Integer> implements PetDAO {

    public PetDAOImpl(EntityManagerFactory emf) {
        super(Pet.class, emf);
    }

    @Override
    public List<Pet> buscarPorDono(Cliente c) throws PersistenceDaoException {
        try (EntityManager em = getEntityManager()) {
            try {

                String jpql = "SELECT p FROM Pet p WHERE p.dono = :cliente";

                TypedQuery<Pet> query = em.createQuery(jpql, Pet.class);
                query.setParameter("cliente", c);

                return query.getResultList();

            } catch (Exception e) {
                e.printStackTrace();
                throw new PersistenceDaoException("Erro ao buscar pets do dono: " + c.getNome(), e);
            }
        }
    }
}

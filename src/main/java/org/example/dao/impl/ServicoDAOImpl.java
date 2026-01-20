package org.example.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.example.dao.PersistenceDaoException;
import org.example.dao.ServicoDAO;
import org.example.entidades.Servico;

import java.time.LocalDate;

public class ServicoDAOImpl extends AbstractDAOImpl<Servico, Integer> implements ServicoDAO {


    public ServicoDAOImpl(EntityManagerFactory emf) {
        super(Servico.class, emf);
    }


    @Override
    public void agendar(Servico servico) throws PersistenceDaoException {
        servico.setStatus("AGENDADO");
        super.save(servico);
    }

    @Override
    public boolean verificarDisponibilidade(LocalDate data, Integer idFuncionario) throws PersistenceDaoException {
        try (EntityManager em = getEntityManager()) {

            String jpql = "SELECT COUNT(s) FROM Servico s WHERE s.dataExecucao = :data AND s.funcionario.id = :idFunc";

            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("data", data);
            query.setParameter("idFunc", idFuncionario);

            Long quantidade = query.getSingleResult();


            return quantidade > 0;

        } catch (Exception e) {
            throw new PersistenceDaoException("Erro ao verificar disponibilidade.", e);
        }
    }


    @Override
    public String consultarStatus(Integer id) throws PersistenceDaoException {
        Servico servico = super.getByID(id);
        if (servico != null) {
            return servico.getStatus();
        }
        throw new PersistenceDaoException("Serviço não encontrado para o ID: " + id);
    }

    @Override
    public void atualizarStatus(Integer id, String novoStatus) throws PersistenceDaoException {
        Servico servico = super.getByID(id);
        if (servico != null) {
            servico.setStatus(novoStatus);
            super.update(servico);
        } else {
            throw new PersistenceDaoException("Não foi possível atualizar. Serviço não encontrado.");
        }
    }



}

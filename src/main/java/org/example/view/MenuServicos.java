package org.example.view;

import org.example.dao.*;
import org.example.entidades.*;
import org.example.service.SistemaMsg;
import org.example.util.ConsoleUI;
import java.math.BigDecimal;
import java.time.LocalDate;

public class MenuServicos {

    private final ServicoDAO servicoDAO;
    private final PetDAO petDAO;
    private final FuncionarioDAO funcionarioDAO;
    private final SistemaMsg sistemaMsg;

    public MenuServicos(ServicoDAO servicoDAO, PetDAO petDAO, FuncionarioDAO funcionarioDAO, SistemaMsg sistemaMsg) {
        this.servicoDAO = servicoDAO;
        this.petDAO = petDAO;
        this.funcionarioDAO = funcionarioDAO;
        this.sistemaMsg = sistemaMsg;
    }

    public void exibirMenu() {
        System.out.println("\n--- SERVIÇOS ---");
        System.out.println("1. Agendar | 2. Consultar | 3. Atualizar Status | 0. Voltar");
        try {
            int op = ConsoleUI.lerInteiro("Opção: ");
            switch (op) {
                case 1 -> agendarServico();
                case 2 -> consultarServico();
                case 3 -> atualizarStatusServico();
            }
        } catch (PersistenceDaoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void agendarServico() throws PersistenceDaoException {
        int idPet = ConsoleUI.lerInteiro("ID Pet:");
        Pet pet = petDAO.getByID(idPet);
        if (pet == null || (pet.getDono() != null && !pet.getDono().getAtivo())) {
            System.out.println("Pet inválido ou dono inativo.");
            return;
        }

        int idFunc = ConsoleUI.lerInteiro("ID Funcionário:");
        Funcionario func = funcionarioDAO.getByID(idFunc);
        if (func == null || !func.getAtivo()) {
            System.out.println("Funcionário inválido.");
            return;
        }

        LocalDate data = null;
        while (data == null) {
            int d = ConsoleUI.lerInteiro("Dia:");
            int m = ConsoleUI.lerInteiro("Mês:");
            int a = ConsoleUI.lerInteiro("Ano:");
            try {
                LocalDate tent = LocalDate.of(a, m, d);
                if (servicoDAO.verificarDisponibilidade(tent, func.getId())) {
                    System.out.println("Data ocupada. Tentar outra? (S/N)");
                    if (ConsoleUI.lerTexto("").equalsIgnoreCase("N")) return;
                } else {
                    data = tent;
                }
            } catch (Exception e) {
                System.out.println("Data inválida.");
            }
        }

        String desc = ConsoleUI.lerTexto("Descrição:");
        double val = ConsoleUI.lerDecimal("Valor:");
        Servico s = new Servico(data, desc, "", BigDecimal.valueOf(val), "AGENDADO", pet, func);
        servicoDAO.agendar(s);
        System.out.println("Agendado ID: " + s.getId());
        sistemaMsg.notificarStatusServico(s);
    }

    private void consultarServico() throws PersistenceDaoException {
        int id = ConsoleUI.lerInteiro("ID Serviço:");
        System.out.println("Status: " + servicoDAO.consultarStatus(id));
    }

    private void atualizarStatusServico() throws PersistenceDaoException {
        int id = ConsoleUI.lerInteiro("ID Serviço:");
        System.out.println("1-ANDAMENTO, 2-CONCLUIDA, 3-CANCELADO");
        int op = ConsoleUI.lerInteiro("Opção:");
        String st = switch(op) { case 1 -> "EM ANDAMENTO"; case 2 -> "CONCLUIDA"; case 3 -> "CANCELADO"; default -> null; };

        if (st != null) {
            servicoDAO.atualizarStatus(id, st);
            System.out.println("Atualizado.");
            Servico s = servicoDAO.getByID(id);
            if(s!=null) sistemaMsg.notificarStatusServico(s);
        }
    }
}
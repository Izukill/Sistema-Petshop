package org.example.view;

import org.example.dao.*;
import org.example.entidades.*;
import org.example.util.ConsoleUI;

public class MenuGerenciamento {

    private final PetDAO petDAO;
    private final ClienteDAO clienteDAO;
    private final FuncionarioDAO funcionarioDAO;
    private final FornecedorDAO fornecedorDAO;
    private final EstoqueDAO estoqueDAO;

    public MenuGerenciamento(ClienteDAO clienteDAO, EstoqueDAO estoqueDAO, FornecedorDAO fornecedorDAO, FuncionarioDAO funcionarioDAO, PetDAO petDAO) {
        this.clienteDAO = clienteDAO;
        this.estoqueDAO = estoqueDAO;
        this.fornecedorDAO = fornecedorDAO;
        this.funcionarioDAO = funcionarioDAO;
        this.petDAO = petDAO;
    }

    public void exibirMenu() {
        System.out.println("\n--- GERENCIAMENTO DE DADOS ---");
        System.out.println("1. Gerenciar Clientes");
        System.out.println("2. Gerenciar Funcionários");
        System.out.println("3. Gerenciar Fornecedores");
        System.out.println("4. Gerenciar Pets");
        System.out.println("5. Gerenciar Estoques (Locais)");
        System.out.println("0. Voltar");

        int op = ConsoleUI.lerInteiro("Opção: ");
        try {
            switch (op) {
                case 1 -> gerenciarClientes();
                case 2 -> gerenciarFuncionarios();
                case 3 -> gerenciarFornecedores();
                case 4 -> gerenciarPets();
                case 5 -> gerenciarEstoques();
            }
        } catch (PersistenceDaoException e) {
            System.out.println("Erro na operação: " + e.getMessage());
        }
    }

    // --- GERENCIAR CLIENTES ---
    private void gerenciarClientes() throws PersistenceDaoException {
        System.out.println("\n>> CLIENTES");
        System.out.println("1. Listar Todos | 2. Atualizar | 3. Remover/Desativar | 0. Voltar");
        int op = ConsoleUI.lerInteiro("Opção: ");

        if (op == 1) {
            var lista = clienteDAO.getAll();
            for (Cliente c : lista)
                System.out.println("ID: " + c.getId() + " | Nome: " + c.getNome() + " | Ativo: " + c.getAtivo());
        } else if (op == 2) {
            int id = ConsoleUI.lerInteiro("ID do Cliente para atualizar: ");
            Cliente c = clienteDAO.getByID(id);
            if (c != null) {
                // ATUALIZAÇÃO INCLUINDO ENDEREÇO
                c.setNome(ConsoleUI.lerTextoAtualizar("Nome", c.getNome()));
                c.setEndereco(ConsoleUI.lerTextoAtualizar("Endereço", c.getEndereco())); // <--- ADICIONADO AQUI
                c.setEmail(ConsoleUI.lerTextoAtualizar("Email", c.getEmail()));
                c.setTelefone(ConsoleUI.lerTextoAtualizar("Telefone", c.getTelefone()));

                // Opção para reativar
                if (!c.getAtivo()) {
                    System.out.println("Este cliente está INATIVO. Deseja reativar? (S/N)");
                    String resp = ConsoleUI.scanner.nextLine();
                    if(resp.equalsIgnoreCase("S")) c.setAtivo(true);
                }
                clienteDAO.update(c);
                System.out.println("Cliente atualizado com sucesso!");
            } else {
                System.out.println("Cliente não encontrado.");
            }
        } else if (op == 3) {
            int id = ConsoleUI.lerInteiro("ID do Cliente para remover/desativar: ");
            clienteDAO.delete(id);
            System.out.println("Operação realizada.");
        }
    }

    // --- GERENCIAR FUNCIONÁRIOS ---
    private void gerenciarFuncionarios() throws PersistenceDaoException {
        System.out.println("\n>> FUNCIONÁRIOS");
        System.out.println("1. Listar Todos | 2. Atualizar | 3. Remover/Desativar | 0. Voltar");
        int op = ConsoleUI.lerInteiro("Opção: ");

        if (op == 1) {
            var lista = funcionarioDAO.getAll();
            for (Funcionario f : lista)
                System.out.println("ID: " + f.getId() + " | Nome: " + f.getNome() + " | Ativo: " + f.getAtivo());
        } else if (op == 2) {
            int id = ConsoleUI.lerInteiro("ID do Funcionário: ");
            Funcionario f = funcionarioDAO.getByID(id);
            if (f != null) {
                // ATUALIZAÇÃO INCLUINDO ENDEREÇO
                f.setNome(ConsoleUI.lerTextoAtualizar("Nome", f.getNome()));
                f.setEndereco(ConsoleUI.lerTextoAtualizar("Endereço", f.getEndereco())); // <--- ADICIONADO AQUI
                f.setCargo(ConsoleUI.lerTextoAtualizar("Cargo", f.getCargo()));

                if(!f.getAtivo()) {
                    System.out.println("Reativar funcionário? (S/N)");
                    if(ConsoleUI.scanner.nextLine().equalsIgnoreCase("S")) f.setAtivo(true);
                }
                funcionarioDAO.update(f);
                System.out.println("Funcionário atualizado!");
            }
        } else if (op == 3) {
            int id = ConsoleUI.lerInteiro("ID para remover: ");
            funcionarioDAO.delete(id);
            System.out.println("Operação realizada.");
        }
    }

    // --- GERENCIAR FORNECEDORES ---
    private void gerenciarFornecedores() throws PersistenceDaoException {
        System.out.println("\n>> FORNECEDORES");
        System.out.println("1. Listar Todos | 2. Atualizar | 3. Remover/Desativar | 0. Voltar");
        int op = ConsoleUI.lerInteiro("Opção: ");

        if (op == 1) {
            var lista = fornecedorDAO.getAll();
            for (Fornecedor f : lista)
                System.out.println("ID: " + f.getId() + " | Nome: " + f.getNome() + " | Ativo: " + f.getAtivo());
        } else if (op == 2) {
            int id = ConsoleUI.lerInteiro("ID do Fornecedor: ");
            Fornecedor f = fornecedorDAO.getByID(id);
            if (f != null) {
                // ATUALIZAÇÃO INCLUINDO ENDEREÇO
                f.setNome(ConsoleUI.lerTextoAtualizar("Nome Fantasia", f.getNome()));
                f.setEndereco(ConsoleUI.lerTextoAtualizar("Endereço", f.getEndereco())); // <--- ADICIONADO AQUI

                if(!f.getAtivo()) {
                    System.out.println("Reativar fornecedor? (S/N)");
                    if(ConsoleUI.scanner.nextLine().equalsIgnoreCase("S")) f.setAtivo(true);
                }
                fornecedorDAO.update(f);
                System.out.println("Fornecedor atualizado!");
            }
        } else if (op == 3) {
            int id = ConsoleUI.lerInteiro("ID para remover: ");
            fornecedorDAO.delete(id);
            System.out.println("Operação realizada.");
        }
    }

    // --- GERENCIAR PETS ---
    private void gerenciarPets() throws PersistenceDaoException {
        System.out.println("\n>> PETS");
        System.out.println("1. Listar Todos | 2. Atualizar | 3. Remover/Desativar | 0. Voltar");
        int op = ConsoleUI.lerInteiro("Opção: ");

        if (op == 1) {
            var lista = petDAO.getAll();
            for (Pet p : lista) System.out.println("ID: " + p.getId() + " | Nome: " + p.getNome() );
        } else if (op == 2) {
            int id = ConsoleUI.lerInteiro("ID do Pet: ");
            Pet p = petDAO.getByID(id);
            if (p != null) {
                p.setNome(ConsoleUI.lerTextoAtualizar("Nome", p.getNome()));
                petDAO.update(p);
                System.out.println("Pet atualizado!");
            }
        } else if (op == 3) {
            int id = ConsoleUI.lerInteiro("ID para remover: ");
            petDAO.delete(id);
            System.out.println("Operação realizada.");
        }
    }

    // --- GERENCIAR ESTOQUES ---
    private void gerenciarEstoques() throws PersistenceDaoException {
        System.out.println("\n>> ESTOQUES");
        System.out.println("1. Listar Locais | 2. Renomear | 3. Remover | 0. Voltar");
        int op = ConsoleUI.lerInteiro("Opção: ");

        if (op == 1) {
            var lista = estoqueDAO.getAll();
            for (Estoque e : lista) System.out.println("ID: " + e.getId());
        } else if (op == 2) {
            int id = ConsoleUI.lerInteiro("ID do Estoque: ");
            Estoque e = estoqueDAO.getByID(id);
            if (e != null) {
                // Se o estoque tiver algum campo nome/localização, atualize aqui
                estoqueDAO.update(e);
            }
        } else if (op == 3) {
            int id = ConsoleUI.lerInteiro("ID para remover: ");
            estoqueDAO.delete(id);
            System.out.println("Removido.");
        }
    }
}
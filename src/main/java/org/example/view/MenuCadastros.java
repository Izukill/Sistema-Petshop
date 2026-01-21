package org.example.view;

import org.example.dao.*;
import org.example.entidades.*;
import org.example.util.ConsoleUI;
import java.math.BigDecimal;
import java.time.LocalDate;

public class MenuCadastros {

    private final ClienteDAO clienteDAO;
    private final FuncionarioDAO funcionarioDAO;
    private final PetDAO petDAO;
    private final ProdutoDAO produtoDAO;
    private final FornecedorDAO fornecedorDAO;
    private final EstoqueDAO estoqueDAO;

    public MenuCadastros(ClienteDAO clienteDAO, FuncionarioDAO funcionarioDAO, PetDAO petDAO,
                         ProdutoDAO produtoDAO, FornecedorDAO fornecedorDAO, EstoqueDAO estoqueDAO) {
        this.clienteDAO = clienteDAO;
        this.funcionarioDAO = funcionarioDAO;
        this.petDAO = petDAO;
        this.produtoDAO = produtoDAO;
        this.fornecedorDAO = fornecedorDAO;
        this.estoqueDAO = estoqueDAO;
    }

    public void exibirMenu() {
        System.out.println("\n--- CADASTROS ---");
        System.out.println("1. Cadastrar Cliente");
        System.out.println("2. Cadastrar Funcionário");
        System.out.println("3. Cadastrar Pet");
        System.out.println("4. Cadastrar Produto (Completo)");
        System.out.println("5. Cadastrar Fornecedor");
        System.out.println("0. Voltar");

        try {
            int op = ConsoleUI.lerInteiro("Opção: ");
            switch (op) {
                case 1 -> cadastrarCliente();
                case 2 -> cadastrarFuncionario();
                case 3 -> cadastrarPet();
                case 4 -> cadastrarProdutoCompleto();
                case 5 -> cadastrarFornecedor();
            }
        } catch (PersistenceDaoException e) {
            System.out.println("Erro no cadastro: " + e.getMessage());
        }
    }



    private void cadastrarCliente() throws PersistenceDaoException {
        String nome = ConsoleUI.lerTexto("Nome do Cliente:");
        String email = ConsoleUI.lerTexto("Email:");
        String fone = ConsoleUI.lerTexto("Telefone:");
        Cliente c = new Cliente(true, email, "Endereço Padrão", nome, fone, LocalDate.now());
        clienteDAO.save(c);
        System.out.println("Cliente salvo ID: " + c.getId());
    }



    private void cadastrarPet() throws PersistenceDaoException {
        int idDono = ConsoleUI.lerInteiro("ID do Dono (Cliente):");
        Cliente dono = clienteDAO.getByID(idDono);
        if (dono == null || !dono.getAtivo()) {
            System.out.println("Cliente inválido ou inativo.");
            return;
        }
        String nome = ConsoleUI.lerTexto("Nome do Pet:");
        String especie = ConsoleUI.lerTexto("Espécie:");
        Pet p = new Pet(especie, LocalDate.now(), nome, "SRD", dono);
        petDAO.save(p);
        System.out.println("Pet cadastrado!");
    }

    private void cadastrarFornecedor() throws PersistenceDaoException {
        String nome = ConsoleUI.lerTexto("Nome Fantasia:");
        String cnpj = ConsoleUI.lerTexto("CNPJ:");
        String cat = ConsoleUI.lerTexto("Categoria:");
        String email = ConsoleUI.lerTexto("Email:");
        String fone = ConsoleUI.lerTexto("Telefone:");
        Fornecedor f = new Fornecedor(true, email, "Endereço", nome, fone, cat, cnpj);
        fornecedorDAO.save(f);
        System.out.println("Fornecedor salvo ID: " + f.getId());
    }

    private void cadastrarFuncionario() throws PersistenceDaoException {
        String nome = ConsoleUI.lerTexto("Nome:");
        String cargo = ConsoleUI.lerTexto("Cargo:");
        Funcionario f = new Funcionario(true, "email@func.com", "Endereço", nome, "0000", cargo, "Geral", "MAT-01");
        funcionarioDAO.save(f);
        System.out.println("Funcionário salvo ID: " + f.getId());
    }

    private void cadastrarProdutoCompleto() throws PersistenceDaoException {
        String nome = ConsoleUI.lerTexto("Nome do Produto:");
        double preco = ConsoleUI.lerDecimal("Preço Base:");

        System.out.println(">>> Vincular Fornecedor <<<");
        for(Fornecedor f : fornecedorDAO.getAll()) System.out.println("ID: " + f.getId() + " - " + f.getNome());
        int idForn = ConsoleUI.lerInteiro("ID Fornecedor:");
        Fornecedor forn = fornecedorDAO.getByID(idForn);

        if (forn == null || !forn.getAtivo()) { System.out.println("Fornecedor inválido."); return; }

        Produto p = new Produto(true, "Geral", forn, nome, BigDecimal.valueOf(preco));
        produtoDAO.save(p);

        System.out.println(">>> Entrada Estoque <<<");
        for(Estoque e : estoqueDAO.getAll()) System.out.println("ID: " + e.getId());
        int idEst = ConsoleUI.lerInteiro("ID Estoque:");
        Estoque est = estoqueDAO.getByID(idEst);

        if (est == null) { System.out.println("Estoque inválido."); return; }

        int qtd = ConsoleUI.lerInteiro("Qtd Inicial:");
        int min = ConsoleUI.lerInteiro("Qtd Mínima:");

        ItemEstoque item = new ItemEstoque(est, p, qtd, min);
        estoqueDAO.inserirItem(item);
        System.out.println("Produto cadastrado e estocado!");
    }
}
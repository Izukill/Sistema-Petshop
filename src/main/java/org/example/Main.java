package org.example;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.dao.*;
import org.example.dao.impl.*;
import org.example.service.GerarRelatorio;
import org.example.service.SistemaMsg;
import org.example.util.ConsoleUI;
import org.example.view.*;

public class Main {

    private static EntityManagerFactory emf;

    public static void main(String[] args) {
        try {
            emf = Persistence.createEntityManagerFactory("petshop-pu");

            //inicializando os DAOS
            ClienteDAO clienteDAO = new ClienteDAOImpl(emf);
            FuncionarioDAO funcionarioDAO = new FuncionarioDAOImpl(emf);
            FornecedorDAO fornecedorDAO = new ForcenedorDAOImpl(emf);
            PetDAO petDAO = new PetDAOImpl(emf);
            ProdutoDAO produtoDAO = new ProdutoDAOImpl(emf);
            ServicoDAO servicoDAO = new ServicoDAOImpl(emf);
            VendaDAO vendaDAO = new VendaDAOImpl(emf);
            EstoqueDAO estoqueDAO = new EstoqueDAOImpl(emf);

            //inicializando o service
            SistemaMsg sistemaMsg = new SistemaMsg();
            GerarRelatorio relatorioService = new GerarRelatorio(vendaDAO, servicoDAO);

            //inicializa os menus
            MenuCadastros menuCadastros = new MenuCadastros(clienteDAO, funcionarioDAO, petDAO, produtoDAO, fornecedorDAO, estoqueDAO);
            MenuVendas menuVendas = new MenuVendas(vendaDAO, clienteDAO, funcionarioDAO, produtoDAO, estoqueDAO);
            MenuServicos menuServicos = new MenuServicos(servicoDAO, petDAO, funcionarioDAO, sistemaMsg);
            MenuEstoque menuEstoque = new MenuEstoque(estoqueDAO, produtoDAO);
            MenuGerenciamento menuGerenciamento = new MenuGerenciamento(clienteDAO, estoqueDAO,fornecedorDAO,funcionarioDAO,petDAO);
            MenuRelatorios menuRelatorios = new MenuRelatorios(relatorioService);

            System.out.println("=== SISTEMA INICIADO COM SUCESSO ===");

            boolean rodando = true;
            while (rodando) {
                System.out.println("\n========= PETSHOP CONTROL =========");
                System.out.println("1. Cadastros");
                System.out.println("2. Serviços");
                System.out.println("3. Vendas");
                System.out.println("4. Estoque");
                System.out.println("5. Relatórios");
                System.out.println("6. Gerenciamento");
                System.out.println("0. Sair");

                int opcao = ConsoleUI.lerInteiro("Opção: ");

                switch (opcao) {
                    case 1 -> menuCadastros.exibirMenu();
                    case 2 -> menuServicos.exibirMenu();
                    case 3 -> menuVendas.exibirMenu();
                    case 4 -> menuEstoque.exibirMenu();
                    case 5 -> menuRelatorios.exibirMenu();
                    case 6 -> menuGerenciamento.exibirMenu();
                    case 0 -> {
                        rodando = false;
                        System.out.println("Encerrando...");
                    }
                    default -> System.out.println("Opção inválida!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (emf != null) emf.close();
            ConsoleUI.scanner.close();
        }
    }
}
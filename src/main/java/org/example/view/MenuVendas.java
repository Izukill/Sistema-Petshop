package org.example.view;

import org.example.dao.*;
import org.example.entidades.*;
import org.example.util.ConsoleUI;
import java.time.LocalDate;

public class MenuVendas {

    private final VendaDAO vendaDAO;
    private final ClienteDAO clienteDAO;
    private final FuncionarioDAO funcionarioDAO;
    private final ProdutoDAO produtoDAO;
    private final EstoqueDAO estoqueDAO;

    public MenuVendas(VendaDAO vendaDAO, ClienteDAO clienteDAO, FuncionarioDAO funcionarioDAO, ProdutoDAO produtoDAO, EstoqueDAO estoqueDAO) {
        this.vendaDAO = vendaDAO;
        this.clienteDAO = clienteDAO;
        this.funcionarioDAO = funcionarioDAO;
        this.produtoDAO = produtoDAO;
        this.estoqueDAO = estoqueDAO;
    }

    public void exibirMenu() {
        System.out.println("\n--- VENDAS ---");
        System.out.println("1. Realizar Nova Venda");
        System.out.println("0. Voltar");
        int op = ConsoleUI.lerInteiro("Opção: ");
        if (op == 1) realizarVenda();
    }

    private void realizarVenda() {
        try {
            int idCli = ConsoleUI.lerInteiro("ID Cliente:");
            Cliente cli = clienteDAO.getByID(idCli);
            if (cli == null || !cli.getAtivo()) { System.out.println("Cliente inválido."); return; }

            int idFunc = ConsoleUI.lerInteiro("ID Funcionário:");
            Funcionario func = funcionarioDAO.getByID(idFunc);
            if (func == null || !func.getAtivo()) { System.out.println("Funcionário inválido."); return; }

            Venda venda = new Venda(cli, LocalDate.now(), func);

            while (true) {
                int idProd = ConsoleUI.lerInteiro("ID Produto (0 para fechar):");
                if (idProd == 0) break;

                Produto prod = produtoDAO.getByID(idProd);
                if (prod != null && prod.isAtivo()) {
                    int qtd = ConsoleUI.lerInteiro("Quantidade:");
                    int disponivel = consultarEstoqueTotal(prod.getId());

                    if (disponivel >= qtd) {
                        venda.adicionarProduto(new ItemVenda(prod, qtd));
                        System.out.println("Adicionado. Subtotal: " + venda.getValorTotal());
                    } else {
                        System.out.println("Estoque insuficiente. Disponível: " + disponivel);
                    }
                } else {
                    System.out.println("Produto não encontrado ou inativo.");
                }
            }

            if (!venda.getItens().isEmpty()) {
                vendaDAO.save(venda);
                atualizarEstoqueAposVenda(venda);
                System.out.println("Venda Finalizada! Total: " + venda.getValorTotal());
            } else {
                System.out.println("Venda cancelada (vazia).");
            }

        } catch (PersistenceDaoException e) {
            System.out.println("Erro na venda: " + e.getMessage());
        }
    }

    private int consultarEstoqueTotal(int idProduto) throws PersistenceDaoException {
        int total = 0;
        for (Estoque est : estoqueDAO.getAll()) {
            if (est.getItens() != null) {
                for (ItemEstoque item : est.getItens()) {
                    if (item.getProduto().getId() == idProduto) total += item.getQuantidadeAtual();
                }
            }
        }
        return total;
    }

    private void atualizarEstoqueAposVenda(Venda venda) throws PersistenceDaoException {
        for (ItemVenda vendido : venda.getItens()) {
            boolean debitado = false;
            for (Estoque est : estoqueDAO.getAll()) {
                if (debitado) break;
                if (est.getItens() != null) {
                    for (ItemEstoque ie : est.getItens()) {
                        if (ie.getProduto().getId().equals(vendido.getProduto().getId()) && ie.getQuantidadeAtual() >= vendido.getQuantidade()) {
                            estoqueDAO.removerItem(ie, vendido.getQuantidade());
                            debitado = true;
                            break;
                        }
                    }
                }
            }
        }
    }
}
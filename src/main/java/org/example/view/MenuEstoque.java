package org.example.view;

import org.example.dao.EstoqueDAO;
import org.example.dao.PersistenceDaoException;
import org.example.dao.ProdutoDAO; // Adicionado import
import org.example.entidades.Estoque;
import org.example.entidades.ItemEstoque;
import org.example.entidades.Produto;
import org.example.util.ConsoleUI;

import java.util.ArrayList;

public class MenuEstoque {

    private final EstoqueDAO estoqueDAO;
    private final ProdutoDAO produtoDAO;


    public MenuEstoque(EstoqueDAO estoqueDAO, ProdutoDAO produtoDAO) {
        this.estoqueDAO = estoqueDAO;
        this.produtoDAO = produtoDAO;
    }


    public void exibirMenu() {
        System.out.println("\n--- ESTOQUE (ITENS) ---");
        System.out.println("1. Listar Estoques e Itens");
        System.out.println("2. Criar Novo Local de Estoque");
        System.out.println("3. Adicionar Quantidade (Entrada Avulsa)");
        System.out.println("4. Remover Quantidade (Baixa/Perda)");
        System.out.println("0. Voltar");

        try {
            int op = ConsoleUI.lerInteiro("Opção: ");
            switch (op) {
                case 1 -> listarEstoques();
                case 2 -> criarEstoque();
                case 3 -> adicionarItemEstoque();
                case 4 -> removerItemEstoque();
            }
        } catch (PersistenceDaoException e) {
            System.out.println("Erro no estoque: " + e.getMessage());
        }
    }



    private void criarEstoque() throws PersistenceDaoException {
        String local = ConsoleUI.lerTexto("Nome do Local (ex: Loja):");
        Estoque est = new Estoque(new ArrayList<>());
        estoqueDAO.save(est);
        System.out.println("Criado!");
    }

    private void listarEstoques() throws PersistenceDaoException {
        var lista = estoqueDAO.getAll();
        for(Estoque e : lista) {
            System.out.println("\n[ESTOQUE ID: " + e.getId());
            if(e.getItens() != null){
                for(ItemEstoque item : e.getItens()){
                    String status = item.getProduto().isAtivo() ? "" : "(PRODUTO INATIVO)";
                    System.out.println("   - Produto: " + item.getProduto().getNome() +
                            " | Qtd: " + item.getQuantidadeAtual() + " " + status);
                }
            }
        }
    }

    private void listarEstoquesSimples() throws PersistenceDaoException {
        var lista = estoqueDAO.getAll();
        for(Estoque e : lista) System.out.println("ID: " + e.getId());
    }

    private void adicionarItemEstoque() throws PersistenceDaoException {
        System.out.println(">>> ENTRADA AVULSA <<<");
        listarEstoquesSimples();
        System.out.println("ID do Estoque:");
        int idEst = ConsoleUI.lerInteiro("");
        Estoque est = estoqueDAO.getByID(idEst);

        System.out.println("ID do Produto:");
        int idProd = ConsoleUI.lerInteiro("");


        Produto prod = produtoDAO.getByID(idProd);

        if(est != null && prod != null){
            if(!prod.isAtivo()) {
                System.out.println("ERRO: Produto INATIVO. Não é possível dar entrada no estoque.");
                return;
            }

            System.out.println("Qtd a adicionar:");
            int qtd = ConsoleUI.lerInteiro("");
            //considerando qtd minima 0 para entrada avulsa simplificada
            ItemEstoque item = new ItemEstoque(est, prod, qtd, 0);
            estoqueDAO.inserirItem(item);
            System.out.println("Adicionado.");
        }
    }

    private void removerItemEstoque() throws PersistenceDaoException {
        System.out.println(">>> BAIXA DE ITEM <<<");
        listarEstoques();
        System.out.println("ID do Estoque:");
        int idEst = ConsoleUI.lerInteiro("");
        Estoque est = estoqueDAO.getByID(idEst);

        if(est != null && est.getItens() != null){
            System.out.println("ID do ITEM DE ESTOQUE para baixa:");
            int idItem = ConsoleUI.lerInteiro("");

            ItemEstoque alvo = null;
            for(ItemEstoque i : est.getItens()){
                if(i.getId().intValue() == idItem) alvo = i;
            }

            if(alvo != null){
                System.out.println("Qtd a remover:");
                int qtd = ConsoleUI.lerInteiro("");
                estoqueDAO.removerItem(alvo, qtd);
                System.out.println("Baixa realizada.");
            } else {
                System.out.println("Item não encontrado neste estoque.");
            }
        }
    }
}
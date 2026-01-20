package org.example.service;

import org.example.dao.PersistenceDaoException;
import org.example.dao.ServicoDAO;
import org.example.dao.VendaDAO;
import org.example.entidades.ItemVenda;
import org.example.entidades.Servico;
import org.example.entidades.Venda;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GerarRelatorio {

    private final VendaDAO vendaDAO;
    private final ServicoDAO servicoDAO;

    public GerarRelatorio(VendaDAO vendaDAO, ServicoDAO servicoDAO) {
        this.vendaDAO = vendaDAO;
        this.servicoDAO = servicoDAO;
    }

    //calcula quanto o petShop faturol no mes escolhido
    public BigDecimal gerarRelatorioFaturamento(int mes) {
        try {

            int anoAtual = LocalDate.now().getYear();
            LocalDate inicio = LocalDate.of(anoAtual, mes, 1);
            LocalDate fim = inicio.withDayOfMonth(inicio.lengthOfMonth());


            List<Venda> vendas = vendaDAO.buscarPorPeriodo(inicio, fim);


            BigDecimal faturamentoTotal = BigDecimal.ZERO;

            for (Venda v : vendas) {
                if (v.getValorTotal() != null) {
                    faturamentoTotal = faturamentoTotal.add(v.getValorTotal());
                }
            }
            return faturamentoTotal;

        } catch (PersistenceDaoException e) {
            System.err.println("Erro ao calcular faturamento: " + e.getMessage());
            return BigDecimal.ZERO;
        }
    }


    //lista os produtos que mais vendaram no mês
    public String gerarMaisVendidos(int mes) {
        try {
            int anoAtual = LocalDate.now().getYear();
            LocalDate inicio = LocalDate.of(anoAtual, mes, 1);
            LocalDate fim = inicio.withDayOfMonth(inicio.lengthOfMonth());

            List<Venda> vendas = vendaDAO.buscarPorPeriodo(inicio, fim);


            Map<String, Integer> contagem = new HashMap<>();

            for (Venda venda : vendas) {
                for (ItemVenda item : venda.getItens()) {
                    String nomeProduto = item.getProduto().getNome();
                    int qtdVendida = item.getQuantidade();
                    contagem.put(nomeProduto, contagem.getOrDefault(nomeProduto, 0) + qtdVendida);
                }
            }

            List<Map.Entry<String, Integer>> listaOrdenada = contagem.entrySet().stream()
                    .sorted((a, b) -> b.getValue().compareTo(a.getValue())) // b compareTo a = Decrescente
                    .toList();


            StringBuilder sb = new StringBuilder();
            sb.append("\n=== RELATÓRIO DE MAIS VENDIDOS (Mês ").append(mes).append(") ===\n");

            if (listaOrdenada.isEmpty()) {
                sb.append("Nenhuma venda registrada neste período.\n");
            } else {
                int rank = 1;
                for (Map.Entry<String, Integer> entry : listaOrdenada) {
                    sb.append(String.format("%dº - %s: %d unidades\n", rank++, entry.getKey(), entry.getValue()));
                }
            }
            return sb.toString();

        } catch (PersistenceDaoException e) {
            return "Erro ao gerar relatório: " + e.getMessage();
        }
    }


    //lista todos os serviços realizados
    public String gerarRelatorioServicos() {
        try {
            List<Servico> servicos = servicoDAO.getAll();

            StringBuilder sb = new StringBuilder();
            sb.append("\n=== RELATÓRIO GERAL DE SERVIÇOS ===\n");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            if (servicos.isEmpty()) {
                sb.append("Nenhum serviço registrado.\n");
            } else {
                for (Servico s : servicos) {
                    String nomePet = (s.getPet() != null) ? s.getPet().getNome() : "Pet Desconhecido";
                    String data = (s.getDataExecucao() != null) ? s.getDataExecucao().format(formatter) : "Sem Data";
                    String funcionario = (s.getFuncionario() != null) ? s.getFuncionario().getNome() : "Sem Funcionario";

                    sb.append(String.format("ID: %d | Data: %s | Pet: %s | Serviço: %s | Status: %s | Func: %s | Valor: R$ %s\n",
                            s.getId(), data, nomePet, s.getDescricao(), s.getStatus(), funcionario, s.getPreco()));
                }
            }
            return sb.toString();

        } catch (PersistenceDaoException e) {
            return "Erro ao gerar relatório de serviços: " + e.getMessage();
        }
    }


}

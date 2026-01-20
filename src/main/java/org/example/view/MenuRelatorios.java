package org.example.view;

import org.example.service.GerarRelatorio;
import org.example.util.ConsoleUI;

import java.math.BigDecimal;

public class MenuRelatorios {


    private final GerarRelatorio relatorioService;

    public MenuRelatorios(GerarRelatorio relatorioService) {
        this.relatorioService = relatorioService;
    }


    public void exibirMenu() {
        System.out.println("\n--- RELATÓRIOS ---");
        System.out.println("1. Faturamento Mensal");
        System.out.println("2. Produtos Mais Vendidos");
        System.out.println("3. Listagem de Serviços");
        System.out.println("0. Voltar");

        int op = ConsoleUI.lerInteiro("Opção: ");
        int mes;
        switch (op) {
            case 1:
                mes = ConsoleUI.lerInteiro("Digite o número do mês (1-12): ");
                BigDecimal fat = relatorioService.gerarRelatorioFaturamento(mes);
                System.out.println(">>> Faturamento Total: R$ " + fat);
                break;
            case 2:
                mes = ConsoleUI.lerInteiro("Digite o número do mês (1-12): ");
                System.out.println(relatorioService.gerarMaisVendidos(mes));
                break;
            case 3:
                System.out.println(relatorioService.gerarRelatorioServicos());
                break;
        }
    }
}

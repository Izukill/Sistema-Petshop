package org.example.util;

import java.util.Locale;
import java.util.Scanner;

public class ConsoleUI {


    public static final Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

    public static int lerInteiro(String msg) {
        System.out.print(msg);
        while (!scanner.hasNextInt()) {
            System.out.print("Entrada inválida. Digite um número: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // Limpa buffer
        return valor;
    }

    public static double lerDecimal(String msg) {
        System.out.print(msg);
        while (!scanner.hasNextDouble()) {
            System.out.print("Entrada inválida. Digite um valor (ex: 10.50): ");
            scanner.next();
        }
        double valor = scanner.nextDouble();
        scanner.nextLine();
        return valor;
    }

    public static String lerTexto(String msg) {
        System.out.println(msg);
        return scanner.nextLine();
    }

    public static String lerTextoAtualizar(String campo, String valorAtual) {
        System.out.println(campo + " [Atual: " + valorAtual + "] (Enter para manter):");
        String input = scanner.nextLine();
        return input.isEmpty() ? valorAtual : input;
    }
}
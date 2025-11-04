package br.edu.infnet.gabrielMarquesAPI.model.domain;

import java.util.Scanner;

public class Jogo {
    public int ano;
    public String nome;
    public boolean disponivel;
    public double preco;

    public void exibirStatus() {
        String status = verificarDisponibilidade();

        System.out.println("\n=== Detalhes do Jogo ===");
        System.out.println("Nome: " + nome);
        System.out.println("Ano de lançamento: " + ano);
        System.out.println("Preço: R$" + preco);
        System.out.println("Status: " + status);
        System.out.println("=== Detalhes do Jogo ===");
    }

    private String verificarDisponibilidade() {
        return disponivel ? "Disponível para locação" : "Indisponível no momento";
    }
}

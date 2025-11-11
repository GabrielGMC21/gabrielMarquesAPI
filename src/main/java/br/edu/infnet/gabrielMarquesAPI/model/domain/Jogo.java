package br.edu.infnet.gabrielMarquesAPI.model.domain;

import java.util.Scanner;

public class Jogo {
    public int ano;
    public String nome;
    public boolean disponivel;
    public double precoAluguel;
    public double precoVenda;

    public void registrarJogo(Scanner in) {
        System.out.print("Digite o nome do jogo: ");
        nome = in.nextLine();

        boolean anoValido = false;
        do {
            System.out.print("Digite o ano de lançamento do jogo: ");
            ano = in.nextInt();

            if (ano < 0 || ano > 2025) {
                System.out.println("Ano inválido! Digite um ano entre 0 e 2025.");
            } else {
                anoValido = true;
            }
        } while (!anoValido);

        System.out.print("Digite o preço para alugar o jogo: R$");
        precoAluguel = in.nextDouble();

        System.out.print("Digite o preço para comprar o jogo: R$");
        precoVenda = in.nextDouble();

        System.out.print("O jogo está disponível para locação? (true/false): ");
        disponivel = in.nextBoolean();

        System.out.println("\nJogo registrado com sucesso!");
    }

    public boolean verificarDisponibilidade() {
        if (!disponivel){
            System.out.println("O jogo " + nome + " não está disponível para locação.");
            return false;
        }
        System.out.println("O jogo " + nome + " está disponível para locação.");
        return true;
    }

    public void alugar(double valorPago) {
        if (disponivel && valorPago >= precoAluguel) {
            disponivel = false;
            double troco = valorPago - precoAluguel;
            System.out.println("Você alugou o jogo " + nome + " por R$" + precoAluguel + ".");
            if(troco > 0) {
                System.out.println("Seu troco é R$" + troco + ".");
            }
        } else if (disponivel && valorPago < precoAluguel) {
            System.out.println("Valor insuficiente para alugar o jogo " + nome + ". O preço do aluguel é R$" + precoAluguel + ".");
        } else{
            System.out.println("O jogo " + nome + " não está disponível para locação.");
        }
    }

    public void exibirDetalhes() {
        System.out.println("Nome: " + nome);
        System.out.println("Ano de Lançamento: " + ano);
        System.out.println("Preço de Aluguel: R$" + precoAluguel);
        System.out.println("Preço de Venda: R$" + precoVenda);
        System.out.println("Disponível para Locação: " + (disponivel ? "Sim" : "Não"));
    }
}

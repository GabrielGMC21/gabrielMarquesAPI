package br.edu.infnet.gabrielMarquesAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.edu.infnet.gabrielMarquesAPI.model.domain.Jogo;

import java.util.Scanner;

@SpringBootApplication
public class GabrielMarquesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GabrielMarquesApiApplication.class, args);

        Scanner in = new Scanner(System.in);
        Jogo jogo1 = new Jogo();

        System.out.println("=== Sistema de Locadora de Videogames ===");

        System.out.println("Digite o nome do jogo: ");
        jogo1.nome = in.nextLine();

        System.out.println("Digite o ano de lançamento: ");
        jogo1.ano = in.nextInt();
        in.nextLine();

        System.out.println("Digite o preço de locação: ");
        jogo1.preco = in.nextDouble();

        System.out.println("O jogo está disponível? (true/false):  ");
        jogo1.disponivel = in.nextBoolean();

        jogo1.exibirStatus();

        in.close();
	}

}

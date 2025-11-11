package br.edu.infnet.gabrielMarquesAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.edu.infnet.gabrielMarquesAPI.model.domain.Jogo;

import java.util.ArrayList;
import java.util.Scanner;

@SpringBootApplication
public class GabrielMarquesApiApplication {

	private static void listarJogos(ArrayList<Jogo> jogos) {
		if (jogos.isEmpty()) {
			System.out.println("Nenhum jogo registrado ainda!");
		} else {
			System.out.println("\n=== Jogos Registrados ===\n");
            for (Jogo jogo : jogos) {
                jogo.exibirDetalhes();
                System.out.println("------------------------\n");
            }
			System.out.println("\n=== Jogos Registrados ===");
		}
	}

    private static boolean verificarDisponibilidade(ArrayList<Jogo> jogos, Scanner in) {
        boolean valido = false;
        boolean disponivel = false;
        int i = 1;

		if (jogos.isEmpty()) {
			System.out.println("Nenhum jogo registrado ainda!");
		} else {
			do {
				System.out.println("\nEscolha o jogo para verificar disponibilidade:");
				for (Jogo jogo : jogos) {
					System.out.println( i + " - " + jogo.nome);
                    i++;
				}
				System.out.print("\nDigite o número do jogo: ");
				int jogoEscolhido = in.nextInt();
				in.nextLine();

				if (jogoEscolhido > 0 && jogoEscolhido <= jogos.size()) {
					Jogo jogoSelecionado = jogos.get(jogoEscolhido - 1);
					disponivel = jogoSelecionado.verificarDisponibilidade();
                    valido = true;
				} else {
					System.out.println("Número de jogo inválido!");
				}
			} while(!valido);
		}

		return disponivel;
	}

	private static void alugarJogo(ArrayList<Jogo> jogos, Scanner in) {
        int i = 0;

		if (jogos.isEmpty()) {
			System.out.println("Nenhum jogo registrado ainda!");
		} else {
			System.out.println("\nEscolha o jogo para alugar:");

			for (Jogo jogo : jogos) {
                i++;
                if(!jogo.disponivel) {
                    continue;
                }
                System.out.println(i + " - " + jogo.nome + " (R$" + jogo.precoAluguel + ")");
			}

			System.out.print("\nDigite o número do jogo: ");
			int jogoAlugar = in.nextInt();
			in.nextLine();

			if (jogoAlugar > 0 && jogoAlugar <= jogos.size()) {
				Jogo jogoParaAlugar = jogos.get(jogoAlugar - 1);

				System.out.print("\nConfirma o aluguel de '" + jogoParaAlugar.nome + "'? (S/N): ");
				String confirmacao = in.nextLine();

				if  (confirmacao.equalsIgnoreCase("s")){
					System.out.print("Quanto o usuário irá pagar? R$");
					double valorPago = in.nextDouble();
					in.nextLine();
					jogoParaAlugar.alugar(valorPago);
				} else {
					System.out.println("Aluguel cancelado!");
				}
			} else {
				System.out.println("Número de jogo inválido!");
			}
		}
	}

	private static void comprarJogo(ArrayList<Jogo> jogos, Scanner in) {
        int i = 0;

		if (jogos.isEmpty()) {
			System.out.println("Nenhum jogo registrado ainda!");
		} else {
			System.out.println("\nEscolha o jogo para comprar:");

			for (Jogo jogo : jogos) {
                i++;

                if(!jogo.disponivel){
                    continue;
                }

                System.out.println(i + " - " + jogo.nome + " (R$" + jogo.precoVenda + ")");
			}

			System.out.print("\nDigite o número do jogo: ");
			int jogoComprar = in.nextInt();
			in.nextLine();

		if (jogoComprar > 0 && jogoComprar <= jogos.size()) {
			Jogo jogoParaComprar = jogos.get(jogoComprar - 1);

			if (!jogoParaComprar.disponivel) {
				System.out.println("O jogo " + jogoParaComprar.nome + " não está disponível para compra.");
				return;
			}

			System.out.print("\nConfirma a compra de '" + jogoParaComprar.nome + "' por R$" + jogoParaComprar.precoVenda + "? (S/N): ");
            String confirmacao = in.nextLine();

            if  (confirmacao.equalsIgnoreCase("s")){
				System.out.print("\nQuanto o usuário irá pagar? R$");
				double valorPago = in.nextDouble();
				in.nextLine();

				if (valorPago >= jogoParaComprar.precoVenda) {
					double troco = valorPago - jogoParaComprar.precoVenda;
					System.out.println("\nJogo '" + jogoParaComprar.nome + "' comprado com sucesso!");
					System.out.println("Preço: R$" + jogoParaComprar.precoVenda);

					if (troco > 0) {
						System.out.println("Seu troco é R$" + troco);
					}

					jogos.remove(jogoComprar - 1);
				} else {
					System.out.println("Valor insuficiente para comprar o jogo " + jogoParaComprar.nome + ". O preço é R$" + jogoParaComprar.precoVenda + ".");
				}
			} else {
				System.out.println("Compra cancelada!");
			}
		} else {
			System.out.println("Número de jogo inválido!");
		}
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(GabrielMarquesApiApplication.class, args);

        Scanner in = new Scanner(System.in);
        ArrayList<Jogo> jogos = new ArrayList<>();
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n=== Sistema de Locadora de Videogames ===");
            System.out.println("\n1 - Registrar um jogo \n2 - Listar jogos registrados\n3 - Verificar disponibilidade\n4 - Alugar um jogo \n5 - Comprar um jogo \n6 - Sair");
            System.out.println("\n=== Sistema de Locadora de Videogames ===");

            System.out.print("\nSeleciona uma das opções: ");
            int opcao = in.nextInt();
            in.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Opção selecionada: Registrar um jogo");
                    Jogo jogo = new Jogo();
                    jogo.registrarJogo(in);
                    jogos.add(jogo);
                    break;
                case 2:
                    System.out.println("Opção selecionada: Listar jogos registrados");
                    listarJogos(jogos);
                    break;
                case 3:
                    System.out.println("Opção selecionada: Verificar disponibilidade de um jogo");
                    boolean disponivel = verificarDisponibilidade(jogos, in);
                    if(disponivel){
                        System.out.println("Deseja alugar este jogo? (S/N): ");
                        String resposta = in.nextLine();
                        if(resposta.equalsIgnoreCase("s")) {
                            alugarJogo(jogos, in);
                        }
                    }
                    break;
                case 4:
                    System.out.println("Opção selecionada: Alugar um jogo");
                    alugarJogo(jogos, in);
                    break;
                case 5:
                    System.out.println("Opção selecionada: Comprar um jogo");
                    comprarJogo(jogos, in);
                    break;
                case 6:
                    System.out.println("Desligando sistema...");
                    continuar = false;
                    in.close();
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }

        System.exit(0);
    }


}

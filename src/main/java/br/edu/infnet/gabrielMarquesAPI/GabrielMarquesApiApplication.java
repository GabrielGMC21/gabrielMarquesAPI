package br.edu.infnet.gabrielMarquesAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.edu.infnet.gabrielMarquesAPI.model.domain.Jogo;
import br.edu.infnet.gabrielMarquesAPI.model.domain.Cliente;

import java.util.ArrayList;
import java.util.Scanner;

@SpringBootApplication
public class GabrielMarquesApiApplication {

	private static void executarTestes() {
		System.out.println("\n=== Executar Testes Automáticos ===\n");

		System.out.println("--- Construtores Relacionamentos e Enum ---\n");
		Cliente cliente1 = new Cliente("João Silva", "joao@email.com", "11987654321");
		Jogo jogo1 = new Jogo("The Last of Us", "2013", "15.00", "150.00", "sim", "PS3");
		Jogo jogo2 = new Jogo("God of War", "2018", "20.00", "200.00", "sim", "PS4");
		System.out.println("✓ Objetos criados com construtores parametrizados");
		System.out.println("✓ Enum Plataformas: " + jogo1.getPlataforma());

		System.out.println("\n--- Coleções e Métodos ---\n");
		cliente1.adicionarJogoAlugado(jogo1);
		cliente1.adicionarJogoAlugado(jogo2);
		System.out.println("✓ ArrayList<Jogo> - Total de jogos: " + cliente1.getJogosAlugados().size());

		System.out.println("\n--- Sobrecarga de Métodos ---\n");
		System.out.println("Sem parâmetros:");
		cliente1.listarJogosAlugados();
		System.out.println("\nCom parâmetro boolean:");
		cliente1.listarJogosAlugados(true);

		System.out.println("\n--- toString() ---\n");
		System.out.println(cliente1);

		System.out.println("\n--- Encapsulamento ---\n");
		System.out.println("✓ Getter: " + jogo1.getNome());
		try {
			jogo1.setNome("");
		} catch (IllegalArgumentException e) {
			System.out.println("✓ Setter com validação: " + e.getMessage());
		}

		System.out.println("\n=== Testes Concluídos ===\n");
	}

	private static Jogo registrarJogo(Scanner in) {
		System.out.print("Digite o nome do jogo: ");
		String nome = in.nextLine();

		System.out.print("Digite o ano de lançamento do jogo: ");
		String ano = in.nextLine();

		System.out.print("Digite o preço para alugar o jogo: R$");
		String precoAluguel = in.nextLine();

		System.out.print("Digite o preço para comprar o jogo: R$");
		String precoVendas = in.nextLine();

		System.out.print("O jogo está disponível para locação? (true/false, sim/não, 1/0): ");
		String disponibilidade = in.nextLine();

		System.out.print("Digite a plataforma do jogo: ");
		String plataforma = in.nextLine();

		try {
			Jogo jogo = new Jogo(nome, ano, precoAluguel, precoVendas, disponibilidade, plataforma);
			System.out.println("\nJogo registrado com sucesso!");
			return jogo;
		} catch (IllegalArgumentException e) {
			System.out.println("Erro ao registrar jogo: " + e.getMessage());
			System.out.println("Jogo não foi registrado. Tente novamente.");
			return null;
		}
	}

	private static void listarJogos(ArrayList<Jogo> jogos) {
		if (jogos.isEmpty()) {
			System.out.println("Nenhum jogo registrado ainda!");
            return;
		}

        System.out.println("\n=== Jogos Registrados ===\n");
        for (Jogo jogo : jogos) {
            System.out.println(jogo.toString());
            System.out.println("------------------------\n");
        }
        System.out.println("\n=== Jogos Registrados ===");
	}

	private static void cadastrarCliente(ArrayList<Cliente> clientes, Scanner in) {
		System.out.print("Digite o nome do cliente: ");
		String nome = in.nextLine();

		System.out.print("Digite o email do cliente: ");
		String email = in.nextLine();

		System.out.print("Digite o telefone do cliente: ");
		String telefone = in.nextLine();

		try {
			Cliente cliente = new Cliente(nome, email, telefone);
			clientes.add(cliente);
			System.out.println("\nCliente cadastrado com sucesso!");
		} catch (IllegalArgumentException e) {
			System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
		}
	}

	private static void listarClientes(ArrayList<Cliente> clientes) {
		if (clientes.isEmpty()) {
			System.out.println("Nenhum cliente cadastrado ainda!");
            return;
		}

        System.out.println("\n=== Clientes Cadastrados ===\n");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println((i + 1) + ".");
            System.out.println(clientes.get(i).toString());
            System.out.println();
        }
        System.out.println("=== Clientes Cadastrados ===");
	}

	private static void listarJogosAlugadosPorCliente(ArrayList<Cliente> clientes, Scanner in) {
		if (clientes.isEmpty()) {
			System.out.println("Nenhum cliente cadastrado ainda!");
			return;
		}

		System.out.println("\nSelecione o cliente:");
		for (int i = 0; i < clientes.size(); i++) {
			System.out.println((i + 1) + " - " + clientes.get(i).getNome());
		}
		System.out.print("\nDigite o número do cliente: ");
		int clienteEscolhido = in.nextInt();
		in.nextLine();

		if (clienteEscolhido > 0 && clienteEscolhido <= clientes.size()) {
			Cliente cliente = clientes.get(clienteEscolhido - 1);
			System.out.print("\nDeseja ver detalhes completos dos jogos? (S/N): ");
			String resposta = in.nextLine();

			if (resposta.equalsIgnoreCase("s")) {
				cliente.listarJogosAlugados(true);
			} else {
				cliente.listarJogosAlugados();
			}
		} else {
			System.out.println("Número de cliente inválido!");
		}
	}

	private static void devolverJogo(ArrayList<Cliente> clientes, Scanner in) {
		if (clientes.isEmpty()) {
			System.out.println("Nenhum cliente cadastrado ainda!");
			return;
		}

		System.out.println("\nSelecione o cliente que irá devolver o jogo:");
		for (int i = 0; i < clientes.size(); i++) {
			Cliente cliente = clientes.get(i);
			System.out.println((i + 1) + " - " + cliente.getNome() +
				" (Jogos alugados: " + cliente.getJogosAlugados().size() + ")");
		}
		System.out.print("\nDigite o número do cliente: ");
		int clienteEscolhido = in.nextInt();
		in.nextLine();

		if (clienteEscolhido <= 0 || clienteEscolhido > clientes.size()) {
			System.out.println("Número de cliente inválido!");
			return;
		}

		Cliente cliente = clientes.get(clienteEscolhido - 1);

		if (cliente.getJogosAlugados().isEmpty()) {
			System.out.println("\nCliente " + cliente.getNome() + " não possui jogos alugados!");
			return;
		}

		cliente.listarJogosAlugados();

		System.out.print("\nDigite o número do jogo a devolver: ");
		int jogoEscolhido = in.nextInt();
		in.nextLine();

		ArrayList<Jogo> jogosAlugados = cliente.getJogosAlugados();
		if (jogoEscolhido <= 0 || jogoEscolhido > jogosAlugados.size()) {
			System.out.println("Número de jogo inválido!");
			return;
		}

		Jogo jogoParaDevolver = jogosAlugados.get(jogoEscolhido - 1);

		System.out.print("\nConfirma a devolução de '" + jogoParaDevolver.getNome() + "'? (S/N): ");
		String confirmacao = in.nextLine();

		if (!confirmacao.equalsIgnoreCase("s")) {
			System.out.println("Devolução cancelada!");
			return;
		}

		jogoParaDevolver.setDisponivel("sim");
		cliente.removerJogoAlugado(jogoParaDevolver);
		System.out.println("\nJogo '" + jogoParaDevolver.getNome() + "' devolvido com sucesso!");
		System.out.println("O jogo está novamente disponível para aluguel.");
	}

	private static Cliente selecionarCliente(ArrayList<Cliente> clientes, Scanner in) {
		if (clientes.isEmpty()) {
			System.out.println("Nenhum cliente cadastrado ainda!");
			System.out.print("Deseja cadastrar um cliente agora? (S/N): ");
			String resposta = in.nextLine();
			if (resposta.equalsIgnoreCase("s")) {
				cadastrarCliente(clientes, in);
				if (!clientes.isEmpty()) {
					return clientes.get(clientes.size() - 1);
				}
			}
			return null;
		}

		System.out.println("\nSelecione o cliente:");
		for (int i = 0; i < clientes.size(); i++) {
			System.out.println((i + 1) + " - " + clientes.get(i).getNome());
		}
		System.out.print("\nDigite o número do cliente: ");
		int clienteEscolhido = in.nextInt();
		in.nextLine();

		if (clienteEscolhido > 0 && clienteEscolhido <= clientes.size()) {
			return clientes.get(clienteEscolhido - 1);
		} else {
			System.out.println("Número de cliente inválido!");
			return null;
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
					System.out.println(i + " - " + jogo.getNome());
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

	private static void alugarJogo(ArrayList<Jogo> jogos, ArrayList<Cliente> clientes, Scanner in) {
        int i = 0;

		if (jogos.isEmpty()) {
			System.out.println("Nenhum jogo registrado ainda!");
            return;
		}
        Cliente clienteSelecionado = selecionarCliente(clientes, in);
        if (clienteSelecionado == null) {
            System.out.println("Operação cancelada - nenhum cliente selecionado.");
            return;
        }

        System.out.println("\nEscolha o jogo para alugar:");

        for (Jogo jogo : jogos) {
            i++;
            if(!jogo.getDisponibilidade()) {
                continue;
            }
            System.out.println(i + " - " + jogo.getNome() + " (R$" + String.format("%.2f", jogo.getPrecoAluguel()) + ")");
        }

        System.out.print("\nDigite o número do jogo: ");
        int jogoAlugar = in.nextInt();
        in.nextLine();

        if (jogoAlugar < 0 || jogoAlugar > jogos.size()) {
            System.out.println("Número de jogo inválido!");
            return;
        }

        Jogo jogoParaAlugar = jogos.get(jogoAlugar - 1);

        if (!jogoParaAlugar.getDisponibilidade()) {
            System.out.println("O jogo " + jogoParaAlugar.getNome() + " não está disponível para aluguel.");
            return;
        }

        System.out.print("\nConfirma o aluguel de '" + jogoParaAlugar.getNome() + "' para " + clienteSelecionado.getNome() + "? (S/N): ");
        String confirmacao = in.nextLine();

        if  (confirmacao.equalsIgnoreCase("s")){
            System.out.print("\nQuanto o cliente irá pagar? R$");
            double valorPago = in.nextDouble();
            in.nextLine();
            jogoParaAlugar.alugar(clienteSelecionado, valorPago);
        } else {
            System.out.println("Aluguel cancelado!");
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

                if(!jogo.getDisponibilidade()){
                    continue;
                }

                System.out.println(i + " - " + jogo.getNome() + " (R$" + String.format("%.2f", jogo.getPrecoVenda()) + ")");
			}

			System.out.print("\nDigite o número do jogo: ");
			int jogoComprar = in.nextInt();
			in.nextLine();

		if (jogoComprar > 0 && jogoComprar <= jogos.size()) {
			Jogo jogoParaComprar = jogos.get(jogoComprar - 1);

			if (!jogoParaComprar.getDisponibilidade()) {
				System.out.println("O jogo " + jogoParaComprar.getNome() + " não está disponível para compra.");
				return;
			}

			System.out.print("\nConfirma a compra de '" + jogoParaComprar.getNome() + "' por R$" + String.format("%.2f", jogoParaComprar.getPrecoVenda()) + "? (S/N): ");
            String confirmacao = in.nextLine();

            if  (confirmacao.equalsIgnoreCase("s")){
				System.out.print("\nQuanto o usuário irá pagar? R$");
				double valorPago = in.nextDouble();
				in.nextLine();

				if (valorPago >= jogoParaComprar.getPrecoVenda()) {
					double troco = valorPago - jogoParaComprar.getPrecoVenda();
					System.out.println("\nJogo '" + jogoParaComprar.getNome() + "' comprado com sucesso!");
					System.out.println("Preço: R$" + String.format("%.2f", jogoParaComprar.getPrecoVenda()));

					if (troco > 0) {
						System.out.println("Seu troco é R$" + String.format("%.2f", troco));
					}

					jogos.remove(jogoComprar - 1);
				} else {
					System.out.println("Valor insuficiente para comprar o jogo " + jogoParaComprar.getNome() + ". O preço é R$" + String.format("%.2f", jogoParaComprar.getPrecoVenda()) + ".");
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
        ArrayList<Cliente> clientes = new ArrayList<>();
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n=== Sistema de Locadora de Videogames ===");
            System.out.println("\n1 - Registrar um jogo");
            System.out.println("2 - Listar jogos registrados");
            System.out.println("3 - Verificar disponibilidade");
            System.out.println("4 - Alugar um jogo");
            System.out.println("5 - Comprar um jogo");
            System.out.println("6 - Cadastrar cliente");
            System.out.println("7 - Listar clientes");
            System.out.println("8 - Listar jogos alugados por cliente");
            System.out.println("9 - Devolver um jogo");
            System.out.println("10 - Executar Testes Automáticos");
            System.out.println("11 - Sair");
            System.out.println("\n=== Sistema de Locadora de Videogames ===");

            System.out.print("\nSeleciona uma das opções: ");
            int opcao = in.nextInt();
            in.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Opção selecionada: Registrar um jogo");
                    Jogo jogo = registrarJogo(in);
                    if (jogo != null) {
                        jogos.add(jogo);
                    }
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
                            alugarJogo(jogos, clientes, in);
                        }
                    }
                    break;
                case 4:
                    System.out.println("Opção selecionada: Alugar um jogo");
                    alugarJogo(jogos, clientes, in);
                    break;
                case 5:
                    System.out.println("Opção selecionada: Comprar um jogo");
                    comprarJogo(jogos, in);
                    break;
                case 6:
                    System.out.println("Opção selecionada: Cadastrar cliente");
                    cadastrarCliente(clientes, in);
                    break;
                case 7:
                    System.out.println("Opção selecionada: Listar clientes");
                    listarClientes(clientes);
                    break;
                case 8:
                    System.out.println("Opção selecionada: Listar jogos alugados por cliente");
                    listarJogosAlugadosPorCliente(clientes, in);
                    break;
                case 9:
                    System.out.println("Opção selecionada: Devolver um jogo");
                    devolverJogo(clientes, in);
                    break;
                case 10:
                    System.out.println("Opção selecionada: Executar Testes Automáticos");
                    executarTestes();
                    break;
                case 11:
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

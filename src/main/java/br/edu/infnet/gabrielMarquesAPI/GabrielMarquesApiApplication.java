package br.edu.infnet.gabrielMarquesAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.edu.infnet.gabrielMarquesAPI.model.domain.Jogo;
import br.edu.infnet.gabrielMarquesAPI.model.domain.Cliente;
import br.edu.infnet.gabrielMarquesAPI.model.domain.Funcionario;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@SpringBootApplication
public class GabrielMarquesApiApplication {

	private static final String ARQUIVO_FUNCIONARIOS = "funcionarios.txt";
	private static final String ARQUIVO_JOGOS = "jogos.txt";
	private static final String ARQUIVO_CLIENTES = "clientes.txt";
	private static Funcionario[] funcionarios = new Funcionario[10];
	private static int qtdFuncionarios = 0;

	private static void pausa(Scanner in) {
		System.out.println("\nPressione ENTER para continuar...");
		in.nextLine();
		for (int i = 0; i < 50; i++) {
			System.out.println();
		}
	}

	private static void carregarFuncionarios() {
		File arquivo = new File(ARQUIVO_FUNCIONARIOS);
		if (!arquivo.exists()) {
			return;
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
			String linha;
			while ((linha = reader.readLine()) != null && qtdFuncionarios < 10) {
				String[] dados = linha.split(";");
				if (dados.length == 6) {
					String nome = dados[0];
					String email = dados[1];
					String telefone = dados[2];
                    String salario = dados[3];
                    String vendas = dados[4];
					String cargo = dados[5];

					Funcionario funcionario = new Funcionario(nome, email, telefone, salario, vendas, cargo);
					funcionarios[qtdFuncionarios++] = funcionario;
				}
			}
		} catch (IOException | NumberFormatException e) {
			System.out.println("Erro ao carregar funcionários: " + e.getMessage());
		}
	}

	private static void carregarJogos(ArrayList<Jogo> jogos) {
		File arquivo = new File(ARQUIVO_JOGOS);
		if (!arquivo.exists()) {
			return;
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
			String linha;
			while ((linha = reader.readLine()) != null) {
				String[] dados = linha.split(";");
				if (dados.length >= 6) {
					String nome = dados[0];
					String ano = dados[1];
					String precoAluguel = dados[2];
					String precoVenda = dados[3];
					String disponivel = dados[4];
					String plataforma = dados[5];

					try {
						Jogo jogo = new Jogo(nome, ano, precoAluguel, precoVenda, disponivel, plataforma);

						if (dados.length > 6 && !dados[6].isEmpty() && !dados[6].equals("null")) {
							try {
								LocalDate dataUltimoAluguel = LocalDate.parse(dados[6]);
								jogo.setDataUltimoAluguel(dataUltimoAluguel);
							} catch (DateTimeParseException e) {
								System.out.println("Erro ao converter data do último aluguel para o jogo " + nome);
							}
						}

						jogos.add(jogo);
					} catch (IllegalArgumentException e) {
						System.out.println("Erro ao carregar jogo do arquivo: " + e.getMessage());
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Erro ao carregar jogos: " + e.getMessage());
		}
	}

	private static void carregarClientes(ArrayList<Cliente> clientes, ArrayList<Jogo> jogosDisponiveis) {
		File arquivo = new File(ARQUIVO_CLIENTES);
		if (!arquivo.exists()) {
			return;
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
			String linha;
			while ((linha = reader.readLine()) != null) {
				String[] dados = linha.split(";");
				if (dados.length >= 3) {
					String nome = dados[0];
					String email = dados[1];
					String telefone = dados[2];

					try {
						Cliente cliente = new Cliente(nome, email, telefone);

						if (dados.length > 3 && !dados[3].isEmpty()) {
							String[] nomesJogos = dados[3].split(",");
							for (String nomeJogo : nomesJogos) {
								for (Jogo jogo : jogosDisponiveis) {
									if (jogo.getNome().equalsIgnoreCase(nomeJogo.trim())) {
										cliente.adicionarJogoAlugado(jogo);
										break;
									}
								}
							}
						}

						clientes.add(cliente);
					} catch (IllegalArgumentException e) {
						System.out.println("Erro ao carregar cliente do arquivo: " + e.getMessage());
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Erro ao carregar clientes: " + e.getMessage());
		}
	}

	private static void salvarFuncionarioNoArquivo(Funcionario funcionario) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_FUNCIONARIOS, true))) {
			String linha = funcionario.getNome() + ";" +
					funcionario.getEmail() + ";" +
					funcionario.getTelefone() + ";" +
					funcionario.getSalario() + ";" +
					funcionario.getVendas() + ";" +
					funcionario.getCargo();
			writer.write(linha);
			writer.newLine();
		} catch (IOException e) {
			System.out.println("Erro ao salvar funcionário no arquivo: " + e.getMessage());
		}
	}

	private static void salvarJogoNoArquivo(Jogo jogo) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_JOGOS, true))) {
			String linha = jogo.getNome() + ";" +
					jogo.getAno() + ";" +
					jogo.getPrecoAluguel() + ";" +
					jogo.getPrecoVenda() + ";" +
					(jogo.getDisponibilidade() ? "sim" : "não") + ";" +
					jogo.getPlataforma() + ";" +
					(jogo.getDataUltimoAluguel() != null ? jogo.getDataUltimoAluguel().toString() : "null");
			writer.write(linha);
			writer.newLine();
		} catch (IOException e) {
			System.out.println("Erro ao salvar jogo no arquivo: " + e.getMessage());
		}
	}

	private static void salvarTodosJogos(ArrayList<Jogo> jogos) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_JOGOS))) {
			for (Jogo jogo : jogos) {
				String linha = jogo.getNome() + ";" +
						jogo.getAno() + ";" +
						jogo.getPrecoAluguel() + ";" +
						jogo.getPrecoVenda() + ";" +
						(jogo.getDisponibilidade() ? "sim" : "não") + ";" +
						jogo.getPlataforma() + ";" +
						(jogo.getDataUltimoAluguel() != null ? jogo.getDataUltimoAluguel().toString() : "null");
				writer.write(linha);
				writer.newLine();
			}
		} catch (IOException e) {
			System.out.println("Erro ao atualizar arquivo de jogos: " + e.getMessage());
		}
	}

	private static void salvarTodosClientes(ArrayList<Cliente> clientes) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_CLIENTES))) {
			for (Cliente cliente : clientes) {
				StringBuilder linha = new StringBuilder();
				linha.append(cliente.getNome()).append(";")
						.append(cliente.getEmail()).append(";")
						.append(cliente.getTelefone());

				ArrayList<Jogo> jogosAlugados = cliente.getJogosAlugados();
				if (!jogosAlugados.isEmpty()) {
					linha.append(";");
					for (int i = 0; i < jogosAlugados.size(); i++) {
						linha.append(jogosAlugados.get(i).getNome());
						if (i < jogosAlugados.size() - 1) {
							linha.append(",");
						}
					}
				}

				writer.write(linha.toString());
				writer.newLine();
			}
		} catch (IOException e) {
			System.out.println("Erro ao atualizar arquivo de clientes: " + e.getMessage());
		}
	}

	private static void salvarClienteNoArquivo(Cliente cliente) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_CLIENTES, true))) {
			String linha = cliente.getNome() + ";" +
					cliente.getEmail() + ";" +
					cliente.getTelefone();
			writer.write(linha);
			writer.newLine();
		} catch (IOException e) {
			System.out.println("Erro ao salvar cliente no arquivo: " + e.getMessage());
		}
	}

	private static void cadastrarFuncionario(Scanner in) {
		if (qtdFuncionarios >= 10) {
			System.out.println("Limite de funcionários atingido (10).");
			pausa(in);
			return;
		}

		System.out.print("Digite o nome do funcionário: ");
		String nome = in.nextLine();
		System.out.print("Digite o email do funcionário: ");
		String email = in.nextLine();
		System.out.print("Digite o telefone do funcionário: ");
		String telefone = in.nextLine();
		System.out.print("Digite o salário do funcionário: ");
        String salario = in.nextLine();
		System.out.print("Digite o número de vendas do funcionário: ");
        String vendas = in.nextLine();
		System.out.print("Digite o cargo do funcionário: ");
		String cargo = in.nextLine();

		try {
			Funcionario funcionario = new Funcionario(nome, email, telefone, salario, vendas, cargo);
			funcionarios[qtdFuncionarios++] = funcionario;
			salvarFuncionarioNoArquivo(funcionario);
			System.out.println("Funcionário cadastrado com sucesso!");
		} catch (IllegalArgumentException e) {
			System.out.println("Erro ao cadastrar funcionário: " + e.getMessage());
		} finally {
			pausa(in);
		}
	}

	private static boolean logarFuncionario(Scanner in) {
		if (qtdFuncionarios == 0) {
			System.out.println("Nenhum funcionário cadastrado.");
			return false;
		}

		System.out.println("\nSelecione o funcionário para login:");
		for (int i = 0; i < qtdFuncionarios; i++) {
			System.out.println((i + 1) + " - " + funcionarios[i].getNome() + " (" + funcionarios[i].getEmail() + ")");
		}

		System.out.print("\nDigite o número do funcionário: ");
		int opcao = in.nextInt();
		in.nextLine();

		if (opcao > 0 && opcao <= qtdFuncionarios) {
			System.out.println("Login realizado com sucesso! Bem-vindo, " + funcionarios[opcao - 1].getNome());
			return true;
		}

		System.out.println("Opção inválida.");
		return false;
	}

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

		System.out.print("O jogo está disponível para locação? (sim/não): ");
		String disponibilidade = in.nextLine();

		System.out.print("Digite a plataforma do jogo: ");
		String plataforma = in.nextLine();

		try {
			Jogo jogo = new Jogo(nome, ano, precoAluguel, precoVendas, disponibilidade, plataforma);
			salvarJogoNoArquivo(jogo);
			System.out.println("\nJogo registrado com sucesso!");
			return jogo;
		} catch (IllegalArgumentException e) {
			System.out.println("Erro ao registrar jogo: " + e.getMessage());
			System.out.println("Jogo não foi registrado. Tente novamente.");
			return null;
		} finally {
			pausa(in);
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
			salvarClienteNoArquivo(cliente);
			System.out.println("\nCliente cadastrado com sucesso!");
		} catch (IllegalArgumentException e) {
			System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
		} finally {
			pausa(in);
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

	private static void devolverJogo(ArrayList<Jogo> jogos, ArrayList<Cliente> clientes, Scanner in) {
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

		jogoParaDevolver.devolver();
		cliente.removerJogoAlugado(jogoParaDevolver);
		salvarTodosJogos(jogos);
		salvarTodosClientes(clientes);
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
			salvarTodosJogos(jogos);
			salvarTodosClientes(clientes);
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
				System.out.print("O cliente possui cupom de desconto? (S/N): ");
				String temCupom = in.nextLine();
				boolean vendido;

				if (temCupom.equalsIgnoreCase("s")) {
					System.out.print("Digite a porcentagem do desconto (ex: 10 para 10%): ");
					double desconto = in.nextDouble();
					in.nextLine();

					System.out.print("\nQuanto o usuário irá pagar? R$");
					double valorPago = in.nextDouble();
					in.nextLine();

					vendido = jogoParaComprar.vender(valorPago, desconto);
				} else {
					System.out.print("\nQuanto o usuário irá pagar? R$");
					double valorPago = in.nextDouble();
					in.nextLine();

					vendido = jogoParaComprar.vender(valorPago);
				}

				if (vendido) {
					jogos.remove(jogoComprar - 1);
					salvarTodosJogos(jogos);
				}
			} else {
				System.out.println("Compra cancelada!");
			}
		} else {
			System.out.println("Número de jogo inválido!");
		}
		}
	}

	private static void listarFuncionarios(Scanner in) {
		System.out.println("\n=== Lista de Funcionários ===");
		if (qtdFuncionarios == 0) {
			System.out.println("Nenhum funcionário cadastrado.");
			pausa(in);
			return;
		}

		System.out.println("Total de funcionários: " + qtdFuncionarios);

		for (Funcionario f : funcionarios) {
			if (f != null) {
				System.out.println("Nome: " + f.getNome());
				System.out.println("Cargo: " + f.getCargo());
				System.out.println("------");
			}
		}
		pausa(in);
	}

	public static void main(String[] args) {
		SpringApplication.run(GabrielMarquesApiApplication.class, args);

        Scanner in = new Scanner(System.in);
        ArrayList<Jogo> jogos = new ArrayList<>();
        ArrayList<Cliente> clientes = new ArrayList<>();
        boolean continuar = true;
        boolean logado = false;

        carregarFuncionarios();
        carregarJogos(jogos);
        carregarClientes(clientes, jogos);

        while (!logado) {
            System.out.println("\n=== Menu de Login ===");
            System.out.println("1 - Logar como um funcionário");
            System.out.println("2 - Cadastrar funcionario");
            System.out.println("3 - Listar Funcionários");
            System.out.println("4 - Sair");
            System.out.print("\nSelecione uma opção: ");
            int opcaoLogin = in.nextInt();
            in.nextLine();

            switch (opcaoLogin) {
                case 1:
                    if (logarFuncionario(in)) {
                        logado = true;
                    }
                    pausa(in);
                    break;
                case 2:
                    cadastrarFuncionario(in);
                    break;
                case 3:
                    listarFuncionarios(in);
                    break;
                case 4:
                    System.out.println("Saindo...");
                    in.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida.");
                    pausa(in);
            }
        }

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
                    pausa(in);
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
                    pausa(in);
                    break;
                case 4:
                    System.out.println("Opção selecionada: Alugar um jogo");
                    alugarJogo(jogos, clientes, in);
                    pausa(in);
                    break;
                case 5:
                    System.out.println("Opção selecionada: Comprar um jogo");
                    comprarJogo(jogos, in);
                    pausa(in);
                    break;
                case 6:
                    System.out.println("Opção selecionada: Cadastrar cliente");
                    cadastrarCliente(clientes, in);
                    break;
                case 7:
                    System.out.println("Opção selecionada: Listar clientes");
                    listarClientes(clientes);
                    pausa(in);
                    break;
                case 8:
                    System.out.println("Opção selecionada: Listar jogos alugados por cliente");
                    listarJogosAlugadosPorCliente(clientes, in);
                    pausa(in);
                    break;
                case 9:
                    System.out.println("Opção selecionada: Devolver um jogo");
                    devolverJogo(jogos, clientes, in);
                    pausa(in);
                    break;
                case 10:
                    System.out.println("Opção selecionada: Executar Testes Automáticos");
                    executarTestes();
                    pausa(in);
                    break;
                case 11:
                    System.out.println("Desligando sistema...");
                    continuar = false;
                    in.close();
                    break;
                default:
                    System.out.println("Opção inválida.");
                    pausa(in);
                    break;
            }
        }

        System.exit(0);
    }


}

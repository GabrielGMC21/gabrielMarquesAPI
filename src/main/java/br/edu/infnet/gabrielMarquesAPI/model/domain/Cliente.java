package br.edu.infnet.gabrielMarquesAPI.model.domain;

import java.util.ArrayList;

public class Cliente {
    private String nome;
    private String email;
    private double telefone;
    private final ArrayList<Jogo> jogosAlugados;

    public Cliente() {
        this.jogosAlugados = new ArrayList<>();
    }

    public Cliente(String nome, String email, String telefone) {
        this();
        setNome(nome);
        setEmail(email);
        setTelefone(telefone);
    }

    public String getNome() {
        return this.nome;
    }

    private void setNome(String nome) {
        if(nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente não pode ser vazio.");
        }
        this.nome = nome;
    }

    private void setEmail(String email) {
        if(email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("O email do cliente não pode ser vazio.");
        } else if(!email.contains("@") || !email.contains(".com")) {
            throw new IllegalArgumentException("O email do cliente é inválido.");
        }
        this.email = email;
    }

    private void setTelefone(String telefone) {
        if(telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("O telefone do cliente não pode ser vazio.");
        }

        double telefoneConvertido;
        try {
            telefoneConvertido = Double.parseDouble(telefone);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O telefone do cliente deve conter apenas números.");
        }

        this.telefone = telefoneConvertido;
    }

    public ArrayList<Jogo> getJogosAlugados() {
        return this.jogosAlugados;
    }

    public void adicionarJogoAlugado(Jogo jogo) {
        if(jogo != null) {
            this.jogosAlugados.add(jogo);
        }
    }

    public void removerJogoAlugado(Jogo jogo) {
        this.jogosAlugados.remove(jogo);
    }

    public void listarJogosAlugados() {
        if (jogosAlugados.isEmpty()) {
            System.out.println("Cliente " + nome + " não possui jogos alugados.");
            return;
        }

        System.out.println("\n=== Jogos Alugados por " + nome + " ===");
        for (int i = 0; i < jogosAlugados.size(); i++) {
            System.out.println((i + 1) + ". " + jogosAlugados.get(i).getNome());
        }
        System.out.println("\nTotal de jogos: " + jogosAlugados.size());
    }

    public void listarJogosAlugados(boolean comDetalhes) {
        if (!comDetalhes) {
            listarJogosAlugados();
            return;
        }

        if (jogosAlugados.isEmpty()) {
            System.out.println("Cliente " + nome + " não possui jogos alugados.");
            return;
        }

        System.out.println("\n=== Jogos Alugados por " + nome + " (Detalhado) ===\n");
        for (int i = 0; i < jogosAlugados.size(); i++) {
            System.out.println((i + 1) + ".");
            System.out.println(jogosAlugados.get(i).toString() + "\n" );
        }
        System.out.println("Total de jogos: " + jogosAlugados.size());
        System.out.println("\n=== Jogos Alugados por " + nome + " (Detalhado) ===");
    }

    public String toString() {
        return "Cliente: " + nome + "\nEmail: " + email + "\nTelefone: " + telefone + "\nJogos Alugados: " + jogosAlugados.size();
    }

}

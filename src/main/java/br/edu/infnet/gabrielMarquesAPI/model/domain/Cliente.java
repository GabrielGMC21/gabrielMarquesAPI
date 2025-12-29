package br.edu.infnet.gabrielMarquesAPI.model.domain;

import java.util.ArrayList;

public class Cliente extends Pessoa {
    private final ArrayList<Jogo> jogosAlugados;

    public Cliente() {
        super();
        this.jogosAlugados = new ArrayList<>();
    }

    public Cliente(String nome, String email, String telefone) {
        super(nome, email, telefone);
        this.jogosAlugados = new ArrayList<>();
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
            System.out.println("Cliente " + super.getNome() + " não possui jogos alugados.");
            return;
        }

        System.out.println("\n=== Jogos Alugados por " + super.getNome() + " ===");
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
            System.out.println("Cliente " + super.getNome() + " não possui jogos alugados.");
            return;
        }

        System.out.println("\n=== Jogos Alugados por " + super.getNome() + " (Detalhado) ===\n");
        for (int i = 0; i < jogosAlugados.size(); i++) {
            System.out.println((i + 1) + ".");
            System.out.println(jogosAlugados.get(i).toString() + "\n" );
        }
        System.out.println("Total de jogos: " + jogosAlugados.size());
        System.out.println("\n=== Jogos Alugados por " + super.getNome() + " (Detalhado) ===");
    }

    @Override
    public String getTipoPessoa() {
        return "Cliente";
    }

    @Override
    public String toString() {
        return super.toString() +
               "\nTipo: " + getTipoPessoa() +
               "\nJogos Alugados: " + jogosAlugados.size();
    }

}

package br.edu.infnet.gabrielMarquesAPI.model.domain;

import br.edu.infnet.gabrielMarquesAPI.model.interfaces.Alugavel;
import br.edu.infnet.gabrielMarquesAPI.model.interfaces.Vendavel;

import java.time.LocalDate;

public final class Jogo implements Alugavel, Vendavel {
    private int ano;
    private String nome;
    private boolean disponivel;
    private double precoAluguel;
    private double precoVenda;
    private Plataformas plataforma;
    private LocalDate dataUltimoAluguel;

    public static final int ANO_MAXIMO = 2025;

    public Jogo(String nome, String ano, String precoAluguel, String precoVenda, String disponivel, String plataforma) {
        setNome(nome);
        setAno(ano);
        setPrecoAluguel(precoAluguel);
        setPrecoVenda(precoVenda);
        setDisponivel(disponivel);
        setPlataforma(plataforma);
    }

    public String getNome() {
        return this.nome;
    }

    public int getAno() {
        return this.ano;
    }

    public void setNome(String nome) {
        if(nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do jogo não pode ser vazio.");
        }
        this.nome = nome;
    }

    public void setAno(String anoString) {
        try {
            int ano = Integer.parseInt(anoString);
            if (ano < 0 || ano > ANO_MAXIMO) {
                throw new IllegalArgumentException("Ano inválido! Digite um ano entre 0 e " + ANO_MAXIMO + ".");
            }
            this.ano = ano;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O ano deve ser um número válido.");
        }
    }

    public boolean getDisponibilidade() {
        return this.disponivel;
    }

    public void setDisponivel(String disponivelString) {
        if (disponivelString.equalsIgnoreCase("sim")) {
            this.disponivel = true;
        } else if (disponivelString.equalsIgnoreCase("não") || disponivelString.equalsIgnoreCase("nao")) {
            this.disponivel = false;
        } else {
            throw new IllegalArgumentException("Valor inválido para disponibilidade. Use: sim/não");
        }
    }

    public double getPrecoAluguel() {
        return this.precoAluguel;
    }

    public void setPrecoAluguel(String precoAluguelString) {
        try {
            double precoAluguel = Double.parseDouble(precoAluguelString);
            if (precoAluguel < 0) {
                throw new IllegalArgumentException("O preço do aluguel não pode ser negativo.");
            }
            this.precoAluguel = precoAluguel;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O preço de aluguel deve ser um número válido.");
        }
    }

    public double getPrecoVenda() {
        return this.precoVenda;
    }

    public void setPrecoVenda(String precoVendaString) {
        try {
            double precoVenda = Double.parseDouble(precoVendaString);
            if (precoVenda < 0) {
                throw new IllegalArgumentException("O preço de venda não pode ser negativo.");
            }
            this.precoVenda = precoVenda;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O preço de venda deve ser um número válido.");
        }
    }

    public void setPlataforma(String plataformaString) {
        try {
            this.plataforma = Plataformas.valueOf(plataformaString.toUpperCase());
        } catch (IllegalArgumentException e) {
            StringBuilder mensagem = new StringBuilder("Plataforma inválida. Plataformas disponíveis: ");
            Plataformas[] plataformas = Plataformas.values();
            for (int i = 0; i < plataformas.length; i++) {
                mensagem.append(plataformas[i]);
                if (i < plataformas.length - 1) {
                    mensagem.append(", ");
                }
            }
            throw new IllegalArgumentException(mensagem.toString());
        }
    }

    public Plataformas getPlataforma() {
        return this.plataforma;
    }

    public LocalDate getDataUltimoAluguel() {
        return this.dataUltimoAluguel;
    }

    public void setDataUltimoAluguel(LocalDate dataUltimoAluguel) {
        this.dataUltimoAluguel = dataUltimoAluguel;
    }

    public boolean verificarDisponibilidade() {
        if (!disponivel){
            System.out.println("O jogo " + nome + " não está disponível para locação.");
            return false;
        }
        System.out.println("O jogo " + nome + " está disponível para locação.");
        return true;
    }

    @Override
    public void alugar(Cliente cliente, double valorPago) {
        if (!disponivel) {
            System.out.println("O jogo " + nome + " não está disponível para locação.");
            return;
        }

        if (valorPago < precoAluguel) {
            System.out.println("Valor insuficiente para alugar o jogo " + nome + ". O preço do aluguel é R$" + String.format("%.2f", precoAluguel) + ".");
            return;
        }

        setDisponivel("não");
        setDataUltimoAluguel(LocalDate.now());
        cliente.adicionarJogoAlugado(this);

        double troco = valorPago - precoAluguel;
        System.out.println("O jogo " + nome + " foi alugado por " + cliente.getNome() + ".");

        if (troco > 0) {
            System.out.println("Seu troco é R$" + String.format("%.2f", troco) + ".");
        }
    }

    @Override
    public void devolver() {
        if (disponivel) {
            System.out.println("O jogo " + nome + " já está disponível.");
            return;
        }
        setDisponivel("sim");
        System.out.println("O jogo " + nome + " foi devolvido e está disponível novamente.");
    }

    @Override
    public boolean vender(double valorPago) {
        if (!disponivel) {
            System.out.println("O jogo " + nome + " não está disponível para venda.");
            return false;
        }

        if (valorPago < precoVenda) {
            System.out.println("Valor insuficiente para comprar o jogo " + nome + ". O preço é R$" + String.format("%.2f", precoVenda) + ".");
            return false;
        }

        double troco = valorPago - precoVenda;
        System.out.println("O jogo " + nome + " foi vendido com sucesso!");

        if (troco > 0) {
            System.out.println("Seu troco é R$" + String.format("%.2f", troco) + ".");
        }

        return true;
    }

    public boolean vender(double valorPago, double descontoPercentual) {
        if (!disponivel) {
            System.out.println("O jogo " + nome + " não está disponível para venda.");
            return false;
        }

        double precoComDesconto = precoVenda * (1 - (descontoPercentual / 100));
        System.out.println("Aplicando desconto de " + descontoPercentual + "%. Novo preço: R$" + String.format("%.2f", precoComDesconto));

        if (valorPago < precoComDesconto) {
            System.out.println("Valor insuficiente para comprar o jogo " + nome + ". O preço com desconto é R$" + String.format("%.2f", precoComDesconto) + ".");
            return false;
        }

        double troco = valorPago - precoComDesconto;
        System.out.println("O jogo " + nome + " foi vendido com sucesso!");

        if (troco > 0) {
            System.out.println("Seu troco é R$" + String.format("%.2f", troco) + ".");
        }

        return true;
    }

    @Override
    public String toString() {
        String resultado = "Jogo: " + nome + " (" + ano + ")\n" +
                "Preço de Aluguel: R$" + String.format("%.2f", precoAluguel) + "\n" +
                "Preço de Venda: R$" + String.format("%.2f", precoVenda) + "\n" +
                "Disponível para Locação: " + (disponivel ? "Sim" : "Não");

        if (plataforma != null) {
            resultado += "\nPlataforma: " + plataforma;
        }

        if (dataUltimoAluguel != null) {
            resultado += "\nÚltimo aluguel: " + dataUltimoAluguel;
        }

        return resultado;
    }
}

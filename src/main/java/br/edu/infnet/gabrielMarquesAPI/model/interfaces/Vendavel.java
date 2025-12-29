package br.edu.infnet.gabrielMarquesAPI.model.interfaces;

public interface Vendavel {
    double getPrecoVenda();
    String getNome();
    boolean vender(double valorPago);
}


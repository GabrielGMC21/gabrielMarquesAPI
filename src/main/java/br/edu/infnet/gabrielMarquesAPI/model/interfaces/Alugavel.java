package br.edu.infnet.gabrielMarquesAPI.model.interfaces;

import br.edu.infnet.gabrielMarquesAPI.model.domain.Cliente;

public interface Alugavel {
    void alugar(Cliente cliente, double valorPago);
    void devolver();
    double getPrecoAluguel();
    boolean getDisponibilidade();
}


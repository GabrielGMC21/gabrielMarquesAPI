package br.edu.infnet.gabrielMarquesAPI.model.domain;

public class Funcionario extends Pessoa{
    private double salario;
    private int vendas;
    private String cargo;

    public Funcionario() {
        super();
    }

    public Funcionario(String nome, String email, String telefone, String salario, String vendas, String cargo) {
        super(nome, email, telefone);
        setSalario(salario);
        setVendas(vendas);
        setCargo(cargo);
    }

    public double getSalario() {
        return salario;
    }

    public int getVendas() {
        return vendas;
    }

    public String getCargo() {
        return cargo;
    }

    private void setSalario(String salario) {
        double salarioConvertido;

        try {
            salarioConvertido = Double.parseDouble(salario);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Salário inválido: " + salario);
        }

        if (salarioConvertido < 0) {
            throw new IllegalArgumentException("O salário não pode ser negativo.");
        }
        this.salario = salarioConvertido;
    }

    private void setVendas(String vendas) {
        int vendasConvertido;

        try {
            vendasConvertido = Integer.parseInt(vendas);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Número de vendas inválido: " + vendas);
        }

        if(vendasConvertido < 0) {
            throw new IllegalArgumentException("O número de vendas não pode ser negativo.");
        }
        this.vendas = vendasConvertido;
    }

    private void setCargo(String cargo) {
        if(cargo == null || cargo.trim().isEmpty()) {
            throw new IllegalArgumentException("O cargo não pode ser vazio.");
        }
        this.cargo = cargo;
    }

    @Override
    public String getTipoPessoa() {
        return "Funcionário";
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nCargo: " + getCargo() +
                "\nSalário: " + getSalario() +
                "\nVendas: " + getVendas();
    }
}
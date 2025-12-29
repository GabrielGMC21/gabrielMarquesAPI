package br.edu.infnet.gabrielMarquesAPI.model.domain;

public abstract class Pessoa {
    private String nome;
    private String email;
    private String telefone;

    protected Pessoa() {
    }

    protected Pessoa(String nome, String email, String telefone) {
        setNome(nome);
        setEmail(email);
        setTelefone(telefone);
    }

    public final String getNome() {
        return this.nome;
    }

    public final String getEmail() {
        return this.email;
    }

    public final String getTelefone() {
        return this.telefone;
    }

    final void setNome(String nome) {
        if(nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser vazio.");
        }

        else if(nome.length() < 3) {
            throw new IllegalArgumentException("O nome deve ter pelo menos 3 caracteres.");
        }

        else if(nome.length() > 50) {
            throw new IllegalArgumentException("O nome deve ter no máximo 50 caracteres.");
        }

        this.nome = nome;
    }

    final void setEmail(String email) {
        if(email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("O email não pode ser vazio.");
        } else if(!email.contains("@") || !email.contains(".com")) {
            throw new IllegalArgumentException("O email digitado é inválido.");
        }
        this.email = email;
    }

    final void setTelefone(String telefone) {
        if(telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("O telefone não pode ser vazio.");
        }

        if (!telefone.matches("\\d+")) {
            throw new IllegalArgumentException("O telefone deve conter apenas números.");
        }

        this.telefone = telefone;
    }

    public abstract String getTipoPessoa();

    public String toString() {
        return "Nome: " + this.nome +
               "\nEmail: " + this.email +
               "\nTelefone: " + this.telefone;
    }
}

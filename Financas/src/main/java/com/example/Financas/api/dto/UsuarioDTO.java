package com.example.Financas.api.dto;

public class UsuarioDTO {
    private String nome;
    private String email;
    private String password;

    public UsuarioDTO(String nome, String email, String password) {
        this.nome = nome;
        this.email = email;
        this.password = password;
    }

    public UsuarioDTO(){}
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

package com.example.Financas.api.dto;

public class UsuarioDTO {
    private Long id;
    private String email;
    private String nome;
    private String password;

    public UsuarioDTO(Long id, String email, String nome, String password) {
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.password = password;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

package com.example.Financas.services;

import com.example.Financas.model.entity.Usuario;

public interface UsuarioService {

    Usuario autenticar(String email, String password);

    Usuario salvarUsuario(Usuario usuario);

    void validarEmail(String email);
}

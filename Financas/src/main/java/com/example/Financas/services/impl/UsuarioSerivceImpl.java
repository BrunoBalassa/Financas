package com.example.Financas.services.impl;

import com.example.Financas.exception.ErroAutenticacao;
import com.example.Financas.exception.RegraNegocioExecpetion;
import com.example.Financas.model.entity.Usuario;
import com.example.Financas.model.repository.UsuarioRepository;
import com.example.Financas.services.UsuarioService;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioSerivceImpl implements UsuarioService {


    private UsuarioRepository usuarioRepository;

    public UsuarioSerivceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario autenticar(String email, String password) {
       Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
       if (!usuario.isPresent()){
           throw new ErroAutenticacao("Usuario não encontrado.");
       }
       if(!usuario.get().getPassword().equals(password)){
           throw new ErroAutenticacao("Senha inválida");
       }
        return usuario.get();

    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());
        return usuarioRepository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {
      boolean existe = usuarioRepository.existsByEmail(email);
      if(existe){
          throw new RegraNegocioExecpetion("Já existe um usuário cadastrado com este email.");
      }
    }

    @Override
    public Optional<Usuario> obterPorId(Long id) {
        return usuarioRepository.findById(id);
    }
}

package com.example.Financas.model.repository;

import com.example.Financas.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TestEntityManager testEntityManager;
    public Usuario criarUsuario(){
        Usuario usuario = new Usuario(null,"bruno","bno.balassa@gmail.com","123");
        return usuario;
    }

    @Test
    public void deveVerificarAExistenciaDeUmEmail(){
        Usuario usuario = criarUsuario();
        testEntityManager.persist(usuario);

        boolean result = usuarioRepository.existsByEmail("bno.balassa@gmail.com");

        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void usuarioNaoCadastrado(){

        boolean result = usuarioRepository.existsByEmail("chuca@hehehe");
        Assertions.assertThat(result).isFalse();
        System.out.println("Passou " + result);
    }
    @Test
    public void salvarUsuario() {
        Usuario usuario = criarUsuario();
        Usuario salvarUsuario = usuarioRepository.save(usuario);

        Assertions.assertThat(salvarUsuario.getId()).isNotNull();
    }

    @Test
    public void buscarUsuarioPorEmail() {
        Usuario usuario = criarUsuario();
        testEntityManager.persist(usuario);

      Optional<Usuario>  result = usuarioRepository.findByEmail("bno.balassa@gmail.com");

      Assertions.assertThat(result.isPresent()).isTrue();
    }

    @Test
    public void deveRetornarUsuarioSemEmailCadastrado() {
        Optional<Usuario>  result = usuarioRepository.findByEmail("bno.balassa@gmail.com");

        Assertions.assertThat(result.isPresent()).isFalse();
    }



}

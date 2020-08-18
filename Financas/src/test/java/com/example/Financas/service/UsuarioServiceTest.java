package com.example.Financas.service;

import com.example.Financas.exception.ErroAutenticacao;
import com.example.Financas.exception.RegraNegocioExecpetion;
import com.example.Financas.model.entity.Usuario;
import com.example.Financas.model.repository.UsuarioRepository;
import com.example.Financas.services.impl.UsuarioSerivceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @SpyBean
    UsuarioSerivceImpl usuarioService;

    @MockBean
    UsuarioRepository usuarioRepository;


    @Test(expected = Test.None.class)
    public void validarEmail() {
     Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
     usuarioService.validarEmail("bno.balassa@gmail.com");

    }
    @Test(expected = RegraNegocioExecpetion.class)
    public void naoDeveValidarEmail() {
     Mockito
             .when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(true);

     usuarioService.validarEmail("bno.balassa@gmail.com");
    }
    @Test(expected = Test.None.class)
    public void deveAutenticarUsuarioComSucesso(){
        String email = "bno.balassa@gmail.com";
        String password = "senha";

        Usuario usuario = new Usuario(null,"bruno",email,password);
        Mockito.when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

        Usuario result =  usuarioService.autenticar(email,password);

        Assertions.assertThat(result).isNotNull();
    }
    @Test
    public void deveLancarErroQuandoNaoEncontrarEmail(){
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString()))
                .thenReturn(Optional.empty());

     Throwable erro =  Assertions.catchThrowable( () -> usuarioService.autenticar("email@gmail.com","senha"));
     Assertions.assertThat(erro).isInstanceOf(ErroAutenticacao.class)
             .hasMessage("Usuario não encontrado.");
    }
     @Test
    public void deveLancarErroQuandoSenhaForErrada(){

         String password = "senha";

         Usuario usuario = new Usuario(null,"bruno","email",password);
         Mockito.when(usuarioRepository.findByEmail(Mockito.anyString()))
                 .thenReturn(Optional.of(usuario));

       Throwable throwable = Assertions.catchThrowable( () ->  usuarioService
               .autenticar("email", "123"));
                Assertions.assertThat(throwable)
                        .isInstanceOf(ErroAutenticacao.class).hasMessage("Senha inválida");

    }
    @Test(expected = Test.None.class)
    public void deveSalvarUmUsuario(){
        Mockito.doNothing().when(usuarioService).validarEmail(Mockito.anyString());
        Usuario usuario = new Usuario(null,"bruno","email","password");
        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        Usuario result = usuarioService.salvarUsuario(new Usuario());

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(null);
        Assertions.assertThat(result.getName()).isEqualTo("bruno");
        Assertions.assertThat(result.getEmail()).isEqualTo("email");
        Assertions.assertThat(result.getPassword()).isEqualTo("password");
    }
    @Test(expected = RegraNegocioExecpetion.class)
    public void naoDeveSalvarUmUsuario(){
        Usuario usuario = new Usuario(null,"bruno","email","password");
        Mockito.doThrow(RegraNegocioExecpetion.class).when(usuarioService).validarEmail("email");

        usuarioService.salvarUsuario(usuario);

        Mockito.verify(usuarioRepository,Mockito.never()).save(usuario);

    }

}

package com.example.Financas.api.resource;

import com.example.Financas.api.dto.UsuarioDTO;
import com.example.Financas.exception.ErroAutenticacao;
import com.example.Financas.exception.RegraNegocioExecpetion;
import com.example.Financas.model.entity.Usuario;
import com.example.Financas.services.LancamentoService;
import com.example.Financas.services.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = UsuarioController.class)
@AutoConfigureMockMvc
public class UsuarioResourceTest {
    static final String API = "/api/usuarios";
    static final MediaType JSON = MediaType.APPLICATION_JSON;

    @Autowired
    MockMvc mvc;

    @MockBean
    UsuarioService service;

    @MockBean
    LancamentoService lancamentoService;
    @Test
    public void deveAutenticarUmUsuario() throws Exception {
        String email = "bruno@gmail";
        String password = "123";

        UsuarioDTO dto = new UsuarioDTO(email,
                "bruno balassa", password);
        Usuario usuario = new Usuario(null,"bruno balassa",email,password);

        Mockito.when(service.autenticar(email,password)).thenReturn(usuario);

        String json = new ObjectMapper().writeValueAsString(dto);

       MockHttpServletRequestBuilder request =
               MockMvcRequestBuilders.post(API.concat("/autenticar"))
        .accept(JSON)
        .contentType(JSON)
        .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("id").value(usuario.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("name").value(usuario.getName()))
        .andExpect(MockMvcResultMatchers.jsonPath("email").value(usuario.getEmail()));
    }
    @Test
    public void deveRetornarBadRequestAoObterErroDeAutenticacao() throws Exception {
        String email = "bruno@gmail";
        String password = "123";

        UsuarioDTO dto = new UsuarioDTO(email,
                "bruno balassa", password);

        Mockito.when(service.autenticar(email,password)).thenThrow(ErroAutenticacao.class);

        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.post(API.concat("/autenticar"))
                        .accept(JSON)
                        .contentType(JSON)
                        .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deveCriarUmNovoUsuario() throws Exception {
        String email = "bruno@gmail";
        String password = "123";

        UsuarioDTO dto = new UsuarioDTO( email,
                "bruno balassa", password);
        Usuario usuario = new Usuario(null,"bruno balassa",email,password);

        Mockito.when(service.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);

        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.post(API)
                        .accept(JSON)
                        .contentType(JSON)
                        .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(usuario.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(usuario.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("email").value(usuario.getEmail()));
    }

    @Test
    public void deveRetornarBadRequestAoTentarCriarUmUsuarioInvalido() throws Exception {
        String email = "bruno@gmail";
        String password = "123";

        UsuarioDTO dto = new UsuarioDTO(email,
                "bruno balassa", password);


        Mockito.when(service.salvarUsuario(Mockito.any(Usuario.class))).thenThrow(RegraNegocioExecpetion.class);

        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.post(API)
                        .accept(JSON)
                        .contentType(JSON)
                        .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

}

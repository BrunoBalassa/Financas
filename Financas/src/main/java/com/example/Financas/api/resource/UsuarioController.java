package com.example.Financas.api.resource;

import com.example.Financas.api.dto.UsuarioDTO;
import com.example.Financas.exception.ErroAutenticacao;
import com.example.Financas.exception.RegraNegocioExecpetion;
import com.example.Financas.model.entity.Usuario;
import com.example.Financas.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/autenticar")
    public ResponseEntity autenticar(@RequestBody UsuarioDTO usuarioDTO) {

        try {
            Usuario autenticado = usuarioService.autenticar(usuarioDTO.getEmail(), usuarioDTO.getPassword());
            return ResponseEntity.ok(autenticado);
        } catch (ErroAutenticacao e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody UsuarioDTO objDTO) {
        Usuario usuario = new Usuario(objDTO.getNome(), objDTO.getEmail(), objDTO.getPassword());
        try {
            Usuario salvar = usuarioService.salvarUsuario(usuario);
            return new ResponseEntity(salvar, HttpStatus.CREATED);

        } catch (RegraNegocioExecpetion e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

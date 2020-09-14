package com.example.Financas.api.resource;

import com.example.Financas.api.dto.UsuarioDTO;
import com.example.Financas.exception.ErroAutenticacao;
import com.example.Financas.exception.RegraNegocioExecpetion;
import com.example.Financas.model.entity.Usuario;
import com.example.Financas.services.LancamentoService;
import com.example.Financas.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;
    private LancamentoService lancamentoService;

    public UsuarioController(UsuarioService usuarioService, LancamentoService lancamentoService) {
        this.usuarioService = usuarioService;
        this.lancamentoService = lancamentoService;
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
    @GetMapping("/{id}/saldo")
    public ResponseEntity obterSaldo(@PathVariable("id") Long id){
       Optional<Usuario> usuario = usuarioService.obterPorId(id);
       if (!usuario.isPresent()){
           return new ResponseEntity(HttpStatus.NOT_FOUND);
       }
        BigDecimal saldo = lancamentoService.obterSaldoPorUsuario(id);
        return ResponseEntity.ok(saldo);
    }

}

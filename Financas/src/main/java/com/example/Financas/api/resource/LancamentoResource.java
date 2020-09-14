package com.example.Financas.api.resource;

import com.example.Financas.api.dto.AtualizaStatusDTO;
import com.example.Financas.api.dto.LancamentoDTO;
import com.example.Financas.exception.RegraNegocioExecpetion;
import com.example.Financas.model.entity.Lancamentos;
import com.example.Financas.model.entity.Usuario;
import com.example.Financas.model.enums.StatusLancamento;
import com.example.Financas.model.enums.TipoLancamento;
import com.example.Financas.services.LancamentoService;
import com.example.Financas.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoResource {

    private LancamentoService lancamentoService;
    private UsuarioService usuarioService;

    public LancamentoResource(LancamentoService lancamentoService, UsuarioService usuarioService) {
        this.lancamentoService = lancamentoService;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody LancamentoDTO lancDto) {
        try {
            Lancamentos entidade = converter(lancDto);
            entidade = lancamentoService.salvar(entidade);
            return new ResponseEntity(entidade, HttpStatus.CREATED);
        } catch (RegraNegocioExecpetion e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping
    public ResponseEntity buscar(
            @RequestParam(value = "descricao", required = false) String descricao,
            @RequestParam(value = "mes", required = false) Integer mes,
            @RequestParam(value = "ano", required = false) Integer ano,
            @RequestParam("usuario") Long idUsuario) {
        Lancamentos lancamentoFiltro = new Lancamentos();
        lancamentoFiltro.setDescricao(descricao);
        lancamentoFiltro.setMes(mes);
        lancamentoFiltro.setAno(ano);
        Optional<Usuario> usuario = usuarioService.obterPorId(idUsuario);
        if (!usuario.isPresent()) {
            return ResponseEntity.badRequest().body("usuario invalido");
        } else {
            lancamentoFiltro.setUsuario(usuario.get());
        }
        List<Lancamentos> lancamentos = lancamentoService.busca(lancamentoFiltro);
        return ResponseEntity.ok(lancamentos);
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody LancamentoDTO lancdto) {
        return lancamentoService.obterPorId(id).map(entity -> {
            try {
                Lancamentos lancamentos = converter(lancdto);
                lancamentos.setId(entity.getId());
                lancamentoService.atualizar(lancamentos);
                return ResponseEntity.ok(lancamentos);
            } catch (RegraNegocioExecpetion e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(() ->
                new ResponseEntity("Lançamento não encontrado", HttpStatus.BAD_REQUEST));
    }
    @PutMapping("{id}/atualiza-status")
    public ResponseEntity atualizarStatus(@PathVariable("id") Long id, @RequestBody AtualizaStatusDTO atualizaStatusDTOdto){
           return lancamentoService.obterPorId(id).map(entity -> {
            StatusLancamento status = StatusLancamento.valueOf(atualizaStatusDTOdto.getStatus());
            if(status == null){
                return ResponseEntity.badRequest().body("Não foi possivel atualizar status lançamento");
            }
            try {
                entity.setStatusLancamento(status);
                lancamentoService.atualizar(entity);
                return ResponseEntity.ok(entity);
            }catch (RegraNegocioExecpetion e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
           }).orElseGet(() ->
                   new ResponseEntity("Lançamento não encontrado", HttpStatus.BAD_REQUEST));
    }



    @DeleteMapping({"{id}"})
    public ResponseEntity deletar(@PathVariable("id") Long id) {
        return lancamentoService.obterPorId(id).map(entity -> {
            lancamentoService.deletar(entity);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }).orElseGet(() -> new ResponseEntity("Lançamento não encontrado", HttpStatus.BAD_REQUEST));
    }

    private Lancamentos converter(LancamentoDTO dto) {
        Lancamentos lancamentos = new Lancamentos();
        lancamentos.setId(dto.getId());
        lancamentos.setDescricao(dto.getDescricao());
        lancamentos.setAno(dto.getAno());
        lancamentos.setMes(dto.getMes());
        lancamentos.setValor(dto.getValor());
        Usuario usuario = usuarioService.obterPorId(dto.getUsuario()).orElseThrow(() -> new RegraNegocioExecpetion("usuario invalido"));
        lancamentos.setUsuario(usuario);

        if(dto.getTipo() != null) {
            lancamentos.setTipoLancamento(TipoLancamento.valueOf(dto.getTipo()));
        }
        if(dto.getStatus() != null) {
            lancamentos.setStatusLancamento(StatusLancamento.valueOf(dto.getStatus()));
        }
        return lancamentos;
    }

}

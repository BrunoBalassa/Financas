package com.example.Financas.services.impl;

import com.example.Financas.exception.RegraNegocioExecpetion;
import com.example.Financas.model.entity.Lancamentos;
import com.example.Financas.model.enums.StatusLancamento;
import com.example.Financas.model.enums.TipoLancamento;
import com.example.Financas.model.repository.LancamentoRepository;
import com.example.Financas.services.LancamentoService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LancamentoServiceImp implements LancamentoService {

    private LancamentoRepository lancamentoRepository;

    public LancamentoServiceImp(LancamentoRepository lancamentoRepository){
        this.lancamentoRepository = lancamentoRepository;
    }
    @Override
    @Transactional
    public Lancamentos salvar(Lancamentos lancamentos) {
        validar(lancamentos);
        if(lancamentos.getStatusLancamento() == null) {
            lancamentos.setStatusLancamento(StatusLancamento.PENDENTE);
        }
        return lancamentoRepository.save(lancamentos);
    }

    @Override
    @Transactional
    public Lancamentos atualizar(Lancamentos lancamentos) {
        Objects.requireNonNull(lancamentos.getId());
        validar(lancamentos);
        return  salvar(lancamentos);
    }

    @Override
    @Transactional
    public void deletar(Lancamentos lancamentos) {
        Objects.requireNonNull(lancamentos.getId());
        lancamentoRepository.delete(lancamentos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lancamentos> busca(Lancamentos lancamentosFiltro) {
        Example example = Example.of(lancamentosFiltro, ExampleMatcher
                .matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return lancamentoRepository.findAll(example);

    }

    @Override
    public void atualizarStatus(Lancamentos lancamentos, StatusLancamento status) {
        lancamentos.setStatusLancamento(status);
        atualizar(lancamentos);
    }

    @Override
    public void validar(Lancamentos lancamentos) {
        if(lancamentos.getDescricao() == null || lancamentos.getDescricao().trim().equals("")){
            throw new RegraNegocioExecpetion("Informe uma Descrição válida.");
        }
        if(lancamentos.getMes() == null || lancamentos.getMes() < 1 || lancamentos.getMes() > 12){
            throw new RegraNegocioExecpetion("Informe um Mês válido.");
        }
        if(lancamentos.getAno() == null || lancamentos.getAno().toString().length() != 4 ){
            throw new RegraNegocioExecpetion("Informe um Ano válido");
        }
        if(lancamentos.getUsuario() == null || lancamentos.getUsuario().getId() == null){
            throw  new RegraNegocioExecpetion(("Informe um Usuário"));
        }
        if(lancamentos.getValor() == null || lancamentos.getValor().compareTo(BigDecimal.ZERO) < 1) {
            throw  new RegraNegocioExecpetion("Informe um valor válido");
        }
        if(lancamentos.getTipoLancamento() == null ){
            throw new RegraNegocioExecpetion("Informe um tipo de lançamento.");
        }
    }

    @Override
    public Optional<Lancamentos> obterPorId(Long id) {
        return lancamentoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal obterSaldoPorUsuario(Long id) {
       BigDecimal receita = lancamentoRepository.obterSaldoPorTipoLancamentoEUsuario(id, TipoLancamento.RECEITA);
       BigDecimal despesa = lancamentoRepository.obterSaldoPorTipoLancamentoEUsuario(id, TipoLancamento.DESPESA);
        if(receita == null){
              receita = BigDecimal.ZERO;
        }
        if(despesa == null){
            despesa = BigDecimal.ZERO;
        }
        return receita.subtract(despesa);
    }
}

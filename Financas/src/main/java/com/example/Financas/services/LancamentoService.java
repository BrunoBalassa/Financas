package com.example.Financas.services;

import com.example.Financas.model.entity.Lancamentos;
import com.example.Financas.model.enums.StatusLancamento;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface LancamentoService {

    Lancamentos salvar(Lancamentos lancamentos);

    Lancamentos atualizar(Lancamentos lancamentos);
    void deletar(Lancamentos lancamentos);
    List<Lancamentos> busca(Lancamentos lancamentosFiltro);
    void atualizarStatus(Lancamentos lancamentos, StatusLancamento status);

    void validar(Lancamentos lancamentos);
    Optional<Lancamentos>obterPorId(Long id);

    BigDecimal obterSaldoPorUsuario(Long id);


}

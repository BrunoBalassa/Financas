package com.example.Financas.services;

import com.example.Financas.model.entity.Lancamentos;
import com.example.Financas.model.enums.StatusLancamento;

import java.util.List;

public interface LancamentoService {

    Lancamentos salvar(Lancamentos lancamentos);

    Lancamentos atualizar(Lancamentos lancamentos);
    void deletar(Lancamentos lancamentos);
    List<Lancamentos> busca(Lancamentos lancamentosFiltro);
    void atualizarStatus(Lancamentos lancamentos, StatusLancamento status);

    void validar(Lancamentos lancamentos);
}

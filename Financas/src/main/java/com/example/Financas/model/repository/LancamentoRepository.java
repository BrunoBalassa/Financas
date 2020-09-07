package com.example.Financas.model.repository;

import com.example.Financas.model.entity.Lancamentos;

import com.example.Financas.model.enums.TipoLancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.math.BigDecimal;


public interface LancamentoRepository extends JpaRepository <Lancamentos, Long> {

    @Query(value = "select sum(l.valor) from Lancamentos l join l.usuario u " +
            "where u.id = :IdUsuario and l.tipoLancamento = :tipoLancamento group by u")
    BigDecimal obterSaldoPorTipoLancamentoEUsuario(@Param("IdUsuario") Long IdUsuario, @Param("tipoLancamento") TipoLancamento tipoLancamento);

}

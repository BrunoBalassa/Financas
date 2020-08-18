package com.example.Financas.model.repository;

import com.example.Financas.model.entity.Lancamentos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository <Lancamentos, Long> {
}

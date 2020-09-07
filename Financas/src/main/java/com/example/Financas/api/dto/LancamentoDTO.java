package com.example.Financas.api.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class LancamentoDTO {
    private Long id;
    private String descricao;
    private Integer ano;
    private Integer mes;
    private BigDecimal valor;
    private Long usuario;
    private String tipo;
    private String status;

    public LancamentoDTO(Long id, String descricao, Integer ano, Integer mes, BigDecimal valor, Long usuario, String tipo, String status) {
        this.id = id;
        this.descricao = descricao;
        this.ano = ano;
        this.mes = mes;
        this.valor = valor;
        this.usuario = usuario;
        this.tipo = tipo;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Long getUsuario() {
        return usuario;
    }

    public void setUsuario(Long usuario) {
        this.usuario = usuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LancamentoDTO that = (LancamentoDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(descricao, that.descricao) &&
                Objects.equals(ano, that.ano) &&
                Objects.equals(mes, that.mes) &&
                Objects.equals(valor, that.valor) &&
                Objects.equals(usuario, that.usuario) &&
                Objects.equals(tipo, that.tipo) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descricao, ano, mes, valor, usuario, tipo, status);
    }
}

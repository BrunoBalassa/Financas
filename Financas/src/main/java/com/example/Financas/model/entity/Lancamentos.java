package com.example.Financas.model.entity;

import com.example.Financas.model.enums.StatusLancamento;
import com.example.Financas.model.enums.TipoLancamento;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "lancamento", schema = "minhasfinancas")
public class Lancamentos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "mes")
    private Integer mes;

    @Column(name = "ano")
    private Integer ano;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "data_cadastro")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dataCadastro;

    @Column(name = "tipo")
    @Enumerated(value = EnumType.STRING)
    private TipoLancamento tipoLancamento;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private StatusLancamento statusLancamento;

    public Lancamentos(Long id, String descricao, Integer mes, Integer ano, Usuario usuario,
                       BigDecimal valor, LocalDate dataCadastro, TipoLancamento tipoLancamento, StatusLancamento statusLancamento) {
        this.id = id;
        this.descricao = descricao;
        this.mes = mes;
        this.ano = ano;
        this.usuario = usuario;
        this.valor = valor;
        this.dataCadastro = dataCadastro;
        this.tipoLancamento = tipoLancamento;
        this.statusLancamento = statusLancamento;
    }
    public Lancamentos(){}

    public Lancamentos(Object o, String pagamento, int i, int i1, BigDecimal ten, LocalDate now, TipoLancamento receita, StatusLancamento pendente) {
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

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public TipoLancamento getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(TipoLancamento tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public StatusLancamento getStatusLancamento() {
        return statusLancamento;
    }

    public void setStatusLancamento(StatusLancamento statusLancamento) {
        this.statusLancamento = statusLancamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lancamentos that = (Lancamentos) o;
        return id.equals(that.id) &&
                descricao.equals(that.descricao) &&
                mes.equals(that.mes) &&
                ano.equals(that.ano) &&
                usuario.equals(that.usuario) &&
                valor.equals(that.valor) &&
                dataCadastro.equals(that.dataCadastro) &&
                tipoLancamento == that.tipoLancamento &&
                statusLancamento == that.statusLancamento;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descricao, mes, ano, usuario, valor, dataCadastro, tipoLancamento, statusLancamento);
    }

    @Override
    public String toString() {
        return "Lancamentos{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", mes=" + mes +
                ", ano=" + ano +
                ", usuario=" + usuario +
                ", valor=" + valor +
                ", dataCadastro=" + dataCadastro +
                ", tipoLancamento=" + tipoLancamento +
                ", statusLancamento=" + statusLancamento +
                '}';
    }
}

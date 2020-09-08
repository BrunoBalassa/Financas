package com.example.Financas.service;


import com.example.Financas.exception.RegraNegocioExecpetion;
import com.example.Financas.model.entity.Lancamentos;
import com.example.Financas.model.entity.Usuario;
import com.example.Financas.model.enums.StatusLancamento;
import com.example.Financas.model.repository.LancamentoRepository;
import com.example.Financas.model.repository.LancamentoRepositoryTest;
import com.example.Financas.services.impl.LancamentoServiceImp;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class LancamentoServiceTest {

    @SpyBean
    LancamentoServiceImp service;

    @MockBean
    LancamentoRepository repository;

    @Test
    public void deveSalvarUmLancamento() {
        Lancamentos lancamentos = LancamentoRepositoryTest.criarLancamento();
        Lancamentos lancamentoSalvo = LancamentoRepositoryTest.criarLancamento();
        Mockito.doNothing().when(service).validar(lancamentos);
        lancamentoSalvo.setId(1l);
        lancamentoSalvo.setStatusLancamento(StatusLancamento.PENDENTE);
        Mockito.when(repository.save(lancamentos)).thenReturn(lancamentoSalvo);

        Lancamentos lancamentos1 = service.salvar(lancamentos);

        Assertions.assertThat(lancamentos1.getId()).isEqualTo(lancamentoSalvo.getId());
        Assertions.assertThat(lancamentos1.getStatusLancamento()).isEqualTo(StatusLancamento.PENDENTE);

    }

    @Test
    public void naoDeveSalvarUmLancamentoQuandoOcorrerUmErro() {
        Lancamentos lancamentos = LancamentoRepositoryTest.criarLancamento();
        Mockito.doThrow(RegraNegocioExecpetion.class).when(service).validar(lancamentos);

        Assertions.catchThrowableOfType(() -> service.salvar(lancamentos), RegraNegocioExecpetion.class);
        Mockito.verify(repository, Mockito.never()).save(lancamentos);
    }

    @Test
    public void deveLancarErroAoTentarAtualizarLancamentoNaoSalvo() {
        Lancamentos lancamentos = LancamentoRepositoryTest.criarLancamento();
        Assertions.catchThrowableOfType(() -> service.atualizar(lancamentos), NullPointerException.class);
        Mockito.verify(repository, Mockito.never()).save(lancamentos);

    }

    @Test
    public void deveDeletarUmLancamento() {
        Lancamentos lancamentos = LancamentoRepositoryTest.criarLancamento();
        lancamentos.setId(1l);

        service.deletar(lancamentos);

        Mockito.verify(repository).delete(lancamentos);
    }

    @Test
    public void deveLancarErroAoTentarDeletarUmLancamentoQueAindaNaoFoiSalvo() {
        Lancamentos lancamentos = LancamentoRepositoryTest.criarLancamento();
        Assertions.catchThrowableOfType(() -> service.deletar(lancamentos), NullPointerException.class);
        Mockito.verify(repository, Mockito.never()).delete(lancamentos);
    }

    @Test
    public void deveFiltrarLancamento() {
        Lancamentos lancamentos = LancamentoRepositoryTest.criarLancamento();
        lancamentos.setId(1L);

        List<Lancamentos> list = Arrays.asList(lancamentos);
        Mockito.when(repository.findAll(Mockito.any(Example.class))).thenReturn(list);

        List<Lancamentos> resultado = service.busca(lancamentos);

        Assertions.assertThat(resultado).isNotEmpty().hasSize(1).contains(lancamentos);
    }

    @Test
    public void deveAtualizarOstatusDeUmLancamento() {
        Lancamentos lancamentos = LancamentoRepositoryTest.criarLancamento();
        lancamentos.setId(1L);
        lancamentos.setStatusLancamento(StatusLancamento.PENDENTE);

        StatusLancamento novoStatus = StatusLancamento.EFETIVADO;
        Mockito.doReturn(lancamentos).when(service).atualizar(lancamentos);

        service.atualizarStatus(lancamentos, novoStatus);

        Assertions.assertThat(lancamentos.getStatusLancamento()).isEqualTo(novoStatus);
        Mockito.verify(service).atualizar(lancamentos);
    }

    @Test
    public void deveObterUmLancamentoPorId() {
        Long id = 1L;
        Lancamentos lancamentos = LancamentoRepositoryTest.criarLancamento();
        lancamentos.setId(id);

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(lancamentos));

        Optional<Lancamentos> resultado = service.obterPorId(id);

        Assertions.assertThat(resultado.isPresent()).isTrue();
    }

    @Test
    public void deveRetornarVazioQuandoOLancamentoNaoExiste() {
        Long id = 1L;
        Lancamentos lancamentos = LancamentoRepositoryTest.criarLancamento();
        lancamentos.setId(id);

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<Lancamentos> resultado = service.obterPorId(id);

        Assertions.assertThat(resultado.isPresent()).isFalse();
    }

    @Test
    public void deveValidarErrosNoLancamento() {
        Lancamentos lancamentos = new Lancamentos();

        Throwable erro = Assertions.catchThrowable(() -> service.validar(lancamentos));
        Assertions.assertThat(erro).isInstanceOf(RegraNegocioExecpetion.class).hasMessage("Informe uma Descrição válida.");

        lancamentos.setDescricao("");
        erro = Assertions.catchThrowable(() -> service.validar(lancamentos));
        Assertions.assertThat(erro).isInstanceOf(RegraNegocioExecpetion.class).hasMessage("Informe uma Descrição válida.");

        lancamentos.setDescricao("Salario");

        erro = Assertions.catchThrowable(() -> service.validar(lancamentos));
        Assertions.assertThat(erro).isInstanceOf(RegraNegocioExecpetion.class).hasMessage("Informe um Mês válido.");

        lancamentos.setMes(0);
        erro = Assertions.catchThrowable(() -> service.validar(lancamentos));
        Assertions.assertThat(erro).isInstanceOf(RegraNegocioExecpetion.class).hasMessage("Informe um Mês válido.");

        lancamentos.setMes(1);
        erro = Assertions.catchThrowable(() -> service.validar(lancamentos));
        Assertions.assertThat(erro).isInstanceOf(RegraNegocioExecpetion.class).hasMessage("Informe um Ano válido");

        lancamentos.setAno(324);
        erro = Assertions.catchThrowable(() -> service.validar(lancamentos));
        Assertions.assertThat(erro).isInstanceOf(RegraNegocioExecpetion.class).hasMessage("Informe um Ano válido");

        lancamentos.setAno(2020);

        erro = Assertions.catchThrowable(() -> service.validar(lancamentos));
        Assertions.assertThat(erro).isInstanceOf(RegraNegocioExecpetion.class).hasMessage("Informe um Usuário");

        lancamentos.setUsuario(new Usuario());
        erro = Assertions.catchThrowable(() -> service.validar(lancamentos));
        Assertions.assertThat(erro).isInstanceOf(RegraNegocioExecpetion.class).hasMessage("Informe um Usuário");

        lancamentos.setUsuario(new Usuario());
        lancamentos.getUsuario().setId(1L);

        erro = Assertions.catchThrowable(() -> service.validar(lancamentos));
        Assertions.assertThat(erro).isInstanceOf(RegraNegocioExecpetion.class).hasMessage("Informe um valor válido");

        lancamentos.setValor(BigDecimal.ZERO);
        erro = Assertions.catchThrowable(() -> service.validar(lancamentos));
        Assertions.assertThat(erro).isInstanceOf(RegraNegocioExecpetion.class).hasMessage("Informe um valor válido");

        lancamentos.setValor(BigDecimal.TEN);

        erro = Assertions.catchThrowable(() -> service.validar(lancamentos));
        Assertions.assertThat(erro).isInstanceOf(RegraNegocioExecpetion.class).hasMessage("Informe um tipo de lançamento.");

    }
}

package com.example.Financas.service;


import com.example.Financas.exception.RegraNegocioExecpetion;
import com.example.Financas.model.entity.Lancamentos;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class LancamentoServiceTest {

    @SpyBean
    LancamentoServiceImp service;

    @MockBean
    LancamentoRepository repository;

    @Test
    public void deveSalvarUmLancamento(){
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
    public void naoDeveSalvarUmLancamentoQuandoOcorrerUmErro(){
        Lancamentos lancamentos = LancamentoRepositoryTest.criarLancamento();
        Mockito.doThrow(RegraNegocioExecpetion.class).when(service).validar(lancamentos);

        Assertions.catchThrowableOfType(() ->  service.salvar(lancamentos), RegraNegocioExecpetion.class);
        Mockito.verify(repository, Mockito.never()).save(lancamentos);
    }

}

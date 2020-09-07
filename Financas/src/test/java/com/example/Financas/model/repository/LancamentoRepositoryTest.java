package com.example.Financas.model.repository;

import com.example.Financas.model.entity.Lancamentos;
import com.example.Financas.model.enums.StatusLancamento;
import com.example.Financas.model.enums.TipoLancamento;
import static org.assertj.core.api.Assertions.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LancamentoRepositoryTest {
    @Autowired
    LancamentoRepository lancamentoRepository;
    @Autowired
    TestEntityManager testManager;

    @Test
    public void deveSalverUmLancamento() {

        Lancamentos lancamentos = new Lancamentos(null, "pagamento", 8, 2020,
                BigDecimal.TEN, LocalDate.now(), TipoLancamento.RECEITA,
                StatusLancamento.PENDENTE);
        lancamentos = lancamentoRepository.save(lancamentos);
        assertThat(lancamentos.getId()).isNotNull();
    }



    @Test
    public void deveDeletarUmLancamento() {
        Lancamentos lancamentos =  criarLancamento();
        testManager.persist(lancamentos);

        lancamentos = testManager.find(Lancamentos.class, lancamentos.getId());

        lancamentoRepository.delete(lancamentos);
        Lancamentos lancamentosDeletado = testManager.find(Lancamentos.class, lancamentos.getId());
        assertThat(lancamentosDeletado).isNull();
    }

    @Test
    public void deveAtualizarUmLancamento(){
      Lancamentos lancamentos =  criarEPersistirUmLancamento();
      lancamentos.setAno(2030);
      lancamentos.setStatusLancamento(StatusLancamento.EFETIVADO);
      lancamentoRepository.save(lancamentos);

      Lancamentos atualizado = testManager.find(Lancamentos.class, lancamentos.getId());

      assertThat(atualizado.getAno()).isEqualTo(2030);
      assertThat(atualizado.getStatusLancamento()).isEqualTo(StatusLancamento.EFETIVADO);

    }

    @Test
    public void deveBuscarUmLancamentoPorId(){
        Lancamentos lancamentos = criarEPersistirUmLancamento();

       Optional<Lancamentos> salvo = lancamentoRepository.findById(lancamentos.getId());
       assertThat(salvo.isPresent()).isTrue();
    }



    public static Lancamentos criarLancamento() {
        return new Lancamentos(null, "pagamento", 8, 2020,
                BigDecimal.TEN, LocalDate.now(), TipoLancamento.RECEITA,
                StatusLancamento.PENDENTE);
    }

    private Lancamentos criarEPersistirUmLancamento() {
        Lancamentos lancamentos = criarLancamento();
        testManager.persist(lancamentos);
        return lancamentos;
    }
}

package com.confianca.repositories;

import com.confianca.domain.Despesa;
import com.confianca.domain.Produto;
import com.confianca.domain.Receita;
import com.confianca.dto.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository <Despesa,Integer> {

    @Transactional(readOnly=true)
    public List<Despesa> findByServicoId(Integer idServico);

//    @Transactional(readOnly=true)
//    @Query("SELECT obj FROM Despesa obj WHERE obj.id = :idDespesa")
//    public List<Despesa> findByRecibo(Integer idDespesa);

    @Transactional(readOnly=true)
    @Query("SELECT NEW com.confianca.dto.DespesaReciboDTO " +
            "(obj.id, obj.nome, obj.dataPagamento, obj.vencimento, " +
            "obj.valor, obj.status, '', " +
            "'', '', '') " +
            "FROM Despesa obj WHERE obj.id = :idDespesa")
    public List<DespesaReciboDTO> findByRecibo(Integer idDespesa);

    @Transactional(readOnly=true)
    public List<Despesa> findByFornecedorId(Integer id);

    @Transactional(readOnly=true)
    public List<Despesa> findByCompetenciaBetween(Date dataInicial, Date dataFinal);

    @Transactional(readOnly=true)
    public List<Despesa> findByVencimentoBetween(Date dataInicial, Date dataFinal);

    @Transactional(readOnly=true)
    public List<Despesa> findByEmissaoBetween(Date dataInicial, Date dataFinal);

    @Transactional(readOnly=true)
    public List<Despesa> findByFornecedorIdAndCompetenciaBetween(Integer id, Date dataInicial, Date dataFinal);

    @Transactional(readOnly=true)
    @Query("SELECT NEW com.confianca.dto.DespesaDTO (MONTH(obj.competencia) as mes, sum(obj.valor) as valor) FROM Despesa obj WHERE YEAR(obj.competencia) = :ano GROUP BY mes")
    public List<DespesaDTO> findByDespesaAcumulada(Integer ano);

    @Transactional(readOnly=true)
    @Query("SELECT NEW com.confianca.dto.StatusDTO (obj.status) FROM Despesa obj GROUP BY obj.status")
    public List<StatusDTO> findStatus();

    @Transactional(readOnly=true)
    public List<Despesa> findByVencimentoBetweenAndTipoPagamentoRecebimentoNomeContains(Date dataInicial, Date dataFinal, String tipoPagamento);

    @Transactional(readOnly=true)
    public List<Despesa> findByCompetenciaBetweenAndTipoPagamentoRecebimentoNomeContains(Date dataInicial, Date dataFinal, String tipoPagamento);
}

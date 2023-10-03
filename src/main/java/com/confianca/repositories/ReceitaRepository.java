package com.confianca.repositories;

import com.confianca.domain.Despesa;
import com.confianca.domain.ProdutoVendido;
import com.confianca.domain.Receita;
import com.confianca.domain.Servico;
import com.confianca.dto.ExtratoDTO;
import com.confianca.dto.ReceitaDTO;
import com.confianca.dto.ReceitaReciboDTO;
import com.confianca.dto.StatusDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository <Receita,Integer> {

    @Transactional(readOnly=true)
    public List<Receita> findByServicoId(Integer idServico);


    @Transactional(readOnly=true)
    public List<Receita> findByProdutoVendidoId(Integer idProduto);

    @Transactional(readOnly=true)
    public List<Receita> findByVendaId(Integer idVenda);

//    @Transactional(readOnly=true)
//    @Query("SELECT obj FROM Receita obj WHERE obj.id = :idReceita")
//    public List<Receita> findByRecibo(Integer idReceita);

    @Transactional(readOnly=true)
    @Query("SELECT NEW com.confianca.dto.ReceitaReciboDTO " +
            "(obj.id, obj.nome, obj.dataRecebimento, obj.vencimento, " +
            "obj.valor, obj.status, '', " +
            "'', '', '') " +
            "FROM Receita obj WHERE obj.id = :idReceita")
    public List<ReceitaReciboDTO> findByRecibo(Integer idReceita);

    @Transactional(readOnly=true)
    public List<Receita> findByCompetenciaBetween(Date dataInicial, Date dataFinal);

    @Transactional(readOnly=true)
    public List<Receita> findByVencimentoBetween(Date dataInicial, Date dataFinal);

    @Transactional(readOnly=true)
    public List<Receita> findByEmissaoBetween(Date dataInicial, Date dataFinal);

    @Transactional(readOnly=true)
    @Query("SELECT NEW com.confianca.dto.ReceitaDTO (MONTH(obj.competencia) as mes, sum(obj.valor) as valor) FROM Receita obj WHERE YEAR(obj.competencia) = :ano GROUP BY mes")
    public List<ReceitaDTO> findByReceitaAcumulada(Integer ano);

    @Transactional(readOnly=true)
    @Query("SELECT NEW com.confianca.dto.StatusDTO (obj.status) FROM Receita obj GROUP BY obj.status")
    public List<StatusDTO> findStatus();

    @Transactional(readOnly=true)
    public List<Receita> findByVencimentoBetweenAndTipoPagamentoRecebimentoNomeContains(Date dataInicial, Date dataFinal, String tipoPagamento);

    @Transactional(readOnly=true)
    public List<Receita> findByCompetenciaBetweenAndTipoPagamentoRecebimentoNomeContains(Date dataInicial, Date dataFinal, String tipoPagamento);

}

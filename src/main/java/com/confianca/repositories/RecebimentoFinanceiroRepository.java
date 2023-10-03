package com.confianca.repositories;

import com.confianca.domain.RecebimentoFinanceiro;
import com.confianca.dto.RecebimentoDTO;
import com.confianca.dto.StatusDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface RecebimentoFinanceiroRepository extends JpaRepository <RecebimentoFinanceiro,Integer> {

    @Transactional(readOnly=true)
    public List<RecebimentoFinanceiro> findByVendaId(Integer idVenda);

    @Transactional(readOnly=true)
    @Query("SELECT obj FROM RecebimentoFinanceiro obj WHERE obj.id = :idReceita")
    public List<RecebimentoFinanceiro> findByRecibo(Integer idReceita);

    @Transactional(readOnly=true)
    public List<RecebimentoFinanceiro> findByVencimentoBetween(Date dataInicial, Date dataFinal);

    @Transactional(readOnly=true)
    @Query("SELECT NEW com.confianca.dto.RecebimentoDTO (MONTH(obj.vencimento) as mes, sum(obj.valor) as valor) " +
            "FROM RecebimentoFinanceiro obj WHERE YEAR(obj.vencimento) = :ano GROUP BY mes")
    public List<RecebimentoDTO> findByRecebimentoFinanceiroAcumulado(Integer ano);

    @Transactional(readOnly=true)
    @Query("SELECT NEW com.confianca.dto.StatusDTO (obj.status) FROM RecebimentoFinanceiro obj GROUP BY obj.status")
    public List<StatusDTO> findStatus();

}

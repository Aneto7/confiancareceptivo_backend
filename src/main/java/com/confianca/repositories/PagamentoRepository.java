package com.confianca.repositories;

import com.confianca.domain.Pagamento;
import com.confianca.dto.PagamentoDTO;
import com.confianca.dto.StatusDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository <Pagamento,Integer> {

    @Transactional(readOnly=true)
    public List<Pagamento> findByVendaId(Integer idVenda);

    @Transactional(readOnly=true)
    @Query("SELECT obj FROM Receita obj WHERE obj.id = :idReceita")
    public List<Pagamento> findByRecibo(Integer idReceita);

    @Transactional(readOnly=true)
    public List<Pagamento> findByVencimentoBetween(Date dataInicial, Date dataFinal);

    @Transactional(readOnly=true)
    @Query("SELECT NEW com.confianca.dto.PagamentoDTO (MONTH(obj.vencimento) as mes, sum(obj.valor) as valor) FROM Pagamento obj WHERE YEAR(obj.vencimento) = :ano GROUP BY mes")
    public List<PagamentoDTO> findByPagamentoAcumulada(Integer ano);

    @Transactional(readOnly=true)
    @Query("SELECT NEW com.confianca.dto.StatusDTO (obj.status) FROM Pagamento obj GROUP BY obj.status")
    public List<StatusDTO> findStatus();

}

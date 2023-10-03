package com.confianca.repositories;

import com.confianca.domain.Despesa;
import com.confianca.domain.Servico;
import com.confianca.domain.Venda;
import com.confianca.dto.ComprovanteVendaDTO;
import com.confianca.dto.FechamentoDTO;
import com.confianca.dto.ServicoDTO;
import com.confianca.dto.StatusDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT obj FROM Servico obj WHERE obj.identificador LIKE :parteIdentificado ORDER BY obj.identificador DESC")
    public List<Servico> findParteIdentificador(@Param("parteIdentificado") String parteIdentificado);

    @Transactional(readOnly = true)
    public List<Servico> findByGuiaId(Integer id);

    @Transactional(readOnly = true)
    public Optional<Servico> findByIdentificador(String identificador);

    @Transactional(readOnly = true)
    @Query("SELECT obj FROM Servico obj WHERE obj.id = :idServico")
    public List<Servico> findByRecibo(Integer idServico);

    @Transactional(readOnly = true)
    public List<Servico> findByDataInBetween(Date dataInicial, Date dataFinal);

    @Transactional(readOnly = true)
    public List<Servico> findByStatusAndDataInBetween(String status, Date dataInicial, Date dataFinal);

    @Transactional(readOnly = true)
    @Query("SELECT obj FROM Servico obj WHERE YEAR(obj.dataIn) = YEAR(:dataIn) AND MONTH(obj.dataIn) = MONTH(:dataIn) AND DAY(obj.dataIn) = DAY(:dataIn) AND obj.produto.id = :idProduto")
    public List<Servico> findByDataInAndProdutoId(Date dataIn, Integer idProduto);

    @Transactional(readOnly = true)
    @Query("SELECT NEW com.confianca.dto.StatusDTO (obj.status) FROM Servico obj GROUP BY obj.status")
    public List<StatusDTO> findStatus();

    @Transactional(readOnly = true)
    @Query("SELECT NEW com.confianca.dto.ServicoDTO " +
            "(obj.id, obj.nome, obj.data, " +
            "obj.dataIn, obj.dataOut, obj.identificador, " +
            "obj.ordemServico, obj.nomeCliente) " +
            "FROM Servico obj WHERE obj.id = :idServico")
    public List<ServicoDTO> findByOS(Integer idServico);

    @Transactional(readOnly = true)
    @Query("SELECT NEW com.confianca.dto.FechamentoDTO " +
            "(obj.identificador, obj.nome, " +
            "'', '', '', " +
            "COALESCE(SUM(receita.valor), 0.0), " +
            "COALESCE(SUM(despesa.valor), 0.0), " +
            "COALESCE(SUM(receita.valor), 0.0) - COALESCE(SUM(despesa.valor), 0.0)) " +
            "FROM Servico obj " +
            "LEFT JOIN obj.receitas receita " +
            "LEFT JOIN obj.despesas despesa " +
            "WHERE obj.data BETWEEN :dataInicial AND :dataFinal " +
            "AND obj.tipoProduto.nome LIKE %:tipoProduto% " +
            "GROUP BY obj.identificador, obj.nome")
    public List<FechamentoDTO> findByFechamento(@Param("dataInicial") Date dataInicial,
                                                @Param("dataFinal") Date dataFinal,
                                                @Param("tipoProduto") String tipoProduto);

}

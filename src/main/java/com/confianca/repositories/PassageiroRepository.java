package com.confianca.repositories;

import com.confianca.domain.Passageiro;
import com.confianca.domain.ProdutoVendido;
import com.confianca.domain.Venda;
import com.confianca.dto.PassageiroConsultaDTO;
import com.confianca.dto.StatusDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface PassageiroRepository extends JpaRepository <Passageiro,Integer> {

    @Transactional(readOnly=true)
    @Query("SELECT NEW com.confianca.dto.PassageiroConsultaDTO " +
            "(obj.id, obj.nome, obj.cpf, obj.nascimento, obj.telefone, obj.celular, obj.email, " +
            "obj.produtoVendido.id, obj.produtoVendido.nome, obj.produtoVendido.servico.dataIn, " +
            "obj.produtoVendido.servico.id, obj.produtoVendido.servico.identificador, " +
            "obj.produtoVendido.venda.id, obj.produtoVendido.venda.nome) FROM Passageiro obj " +
            "WHERE obj.status = :status AND obj.produtoVendido.servico.dataIn BETWEEN :dataIn AND :dataFi")
    public List<PassageiroConsultaDTO> findByStatusData(String status, Date dataIn, Date dataFi);

    @Transactional(readOnly=true)
    @Query("SELECT NEW com.confianca.dto.PassageiroConsultaDTO " +
            "(obj.id, obj.nome, obj.cpf, obj.nascimento, obj.telefone, obj.celular, obj.email, " +
            "obj.produtoVendido.id, obj.produtoVendido.nome, obj.produtoVendido.servico.dataIn, " +
            "obj.produtoVendido.servico.id, obj.produtoVendido.servico.identificador, " +
            "obj.produtoVendido.venda.id, obj.produtoVendido.venda.nome) FROM Passageiro obj " +
            "WHERE obj.produtoVendido.servico.dataIn BETWEEN :dataIn AND :dataFi")
    public List<PassageiroConsultaDTO> findByData(Date dataIn, Date dataFi);

    @Transactional(readOnly=true)
    public List<Passageiro> findByProdutoVendidoVendaId(Integer id);

    @Transactional(readOnly=true)
    public List<Passageiro> findByProdutoVendidoServicoData(Date dataIn);

    @Transactional(readOnly=true)
    public List<Passageiro> findByProdutoVendidoServicoDataBetween(Date dataIn, Date dataFi);

    @Transactional(readOnly=true)
    public List<Passageiro> findByStatusAndProdutoVendidoServicoDataBetween(String status, Date dataIn, Date dataFi);

    @Transactional(readOnly=true)
    @Query("SELECT NEW com.confianca.dto.StatusDTO (obj.status) FROM Venda obj GROUP BY obj.status")
    public List<StatusDTO> findStatus();
}

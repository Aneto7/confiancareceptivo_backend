package com.confianca.repositories;

import com.confianca.domain.Venda;
import com.confianca.dto.ComprovanteVendaDTO;
import com.confianca.dto.StatusDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository <Venda,Integer> {

    @Transactional(readOnly = true)
    public List<Venda> findByStatusContainingAndClienteIdAndDataVencimentoBetween(
            String status,
            Integer clienteId,
            Date dataInicial,
            Date dataFinal);

    @Transactional(readOnly = true)
    public List<Venda> findByStatusContainingAndProdutosVendidosProdutoIdAndClienteIdAndDataVencimentoBetween(
            String status,
            Integer idProduto,
            Integer clienteId,
            Date dataInicial,
            Date dataFinal);

    @Transactional(readOnly = true)
    public List<Venda> findByStatusAndDataVencimentoBetween(
            String status,
            Date dataInicial,
            Date dataFinal);

    @Transactional(readOnly = true)
    public List<Venda> findByStatusContainingAndClienteIdAndProdutosVendidosServicoDataInBetween(
            String status,
            Integer clienteId,
            Date dataIni,
            Date dataFin);


    @Transactional(readOnly = true)
    public List<Venda> findByStatusContainingAndProdutosVendidosProdutoIdAndClienteIdAndProdutosVendidosServicoDataInBetween(
            String status,
            Integer idProduto,
            Integer clienteId,
            Date dataIni,
            Date dataFin);

    @Transactional(readOnly = true)
    public List<Venda> findByClienteIdAndProdutosVendidosPassageirosNomeContaining(
            Integer clienteId,
            String passageiro);

    @Transactional(readOnly = true)
    public List<Venda> findByIdAndClienteId(
            Integer idVenda,
            Integer idCliente);

    @Transactional(readOnly = true)
    public List<Venda> findByProdutosVendidosServicoId(Integer idServico);

    @Transactional(readOnly = true)
    public List<Venda> findByProdutosVendidosId(Integer idProdutoVendido);

    @Transactional(readOnly = true)
    public List<Venda> findByDataVencimentoBetween(Date dataInicial, Date dataFinal);

    @Transactional(readOnly=true)
    @Query("SELECT obj FROM Venda obj WHERE obj.id = :idReceita")
    public List<Venda> findByRecibo(Integer idReceita);

    @Transactional(readOnly=true)
    @Query("SELECT NEW com.confianca.dto.StatusDTO (obj.status) FROM Venda obj GROUP BY obj.status")
    public List<StatusDTO> findStatus();

    @Transactional(readOnly=true)
    @Query("SELECT NEW com.confianca.dto.ComprovanteVendaDTO " +
            "(obj.id, obj.nome, obj.dataVencimento, " +
            "obj.cliente.nome, obj.cliente.cnpjOuCpf, obj.cliente.endereco, " +
            "obj.cliente.bairro, obj.cliente.cep, " +
            "obj.cliente.cidade.nome, obj.cliente.cidade.estado.sigla, obj.valor, " +
            "obj.desconto, obj.valorFinal) " +
            "FROM Venda obj WHERE obj.id = :idVenda")
    public List<ComprovanteVendaDTO> findByComprovante(Integer idVenda);
}

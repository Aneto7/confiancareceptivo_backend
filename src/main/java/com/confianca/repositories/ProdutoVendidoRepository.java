package com.confianca.repositories;

import com.confianca.domain.ProdutoVendido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface ProdutoVendidoRepository extends JpaRepository <ProdutoVendido,Integer> {

    @Transactional(readOnly=true)
    public List<ProdutoVendido> findByTarifaDataIdAndProdutoId(Integer idTarifa, Integer idProduto);

    @Transactional(readOnly=true)
    public List<ProdutoVendido> findByProdutoIdAndTarifaDataDataBetween(Integer idProduto, Date dataInicial, Date dataFinal);

    @Transactional(readOnly=true)
    public List<ProdutoVendido> findByTarifaDataDataBetween(Date dataInicial, Date dataFinal);

    @Transactional(readOnly=true)
    public List<ProdutoVendido> findByTarifaIdAndProdutoId(Integer idTarifa, Integer idProduto);

    @Transactional(readOnly=true)
    @Query("SELECT obj FROM ProdutoVendido obj WHERE obj.tarifa.id = :idTarifa AND obj.produto.id = :idProduto AND obj.status <> :status")
    public List<ProdutoVendido> findByProdutoVend(Integer idTarifa, Integer idProduto, String status);

    @Transactional(readOnly=true)
    public List<ProdutoVendido> findByServicoId(Integer idServico);

    @Transactional(readOnly=true)
    @Query("SELECT obj FROM ProdutoVendido obj WHERE obj.servico.id = :idServico AND obj.servico.status <> :status")
    public List<ProdutoVendido> findByServicoAtivo(Integer idServico, String status);

    @Transactional(readOnly=true)
    public List<ProdutoVendido> findByVendaId(Integer idVenda);

//    @Transactional(readOnly=true)
//    @Query("SELECT NEW com.confianca.dto.ProdutoVendidoVoucherDTO " +
//            "(obj.id, obj.nome, obj.tarifa.data.data, obj.produto.horarioSaida, " +
//            "obj.produto.horarioRetorno, obj.produto.pontoDeSaida.nome, obj.produto.pontoDeSaida.endereco, " +
//            "obj.venda.cliente.nome, obj.venda.cliente.cnpjOuCpf, obj.venda.cliente.endereco, " +
//            "obj.venda.cliente.bairro, obj.venda.cliente.cep, " +
//            "obj.venda.cliente.cidade.nome, obj.venda.cliente.cidade.estado.sigla, obj.venda.dataVencimento) " +
//            "FROM ProdutoVendido obj WHERE obj.id = :idProdutoVendido")
//    public List<ProdutoVendidoVoucherDTO> findByVoucher(Integer idProdutoVendido);

}

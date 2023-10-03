/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.confianca.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Pagamento", catalog = "receptivo", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"idPagamento"})})
public class Pagamento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPagamento", nullable = false)
    private Integer id;

    @JoinColumn(name = "CentroDeCusto_idCentroDeCusto")
    @ManyToOne
    private CentroDeCusto centroDeCusto;

    @JoinColumn(name = "Fornecedor_idFornecedor")
    @ManyToOne
    private Fornecedor fornecedor;

    @JoinColumn(name = "TipoPagamentoRecebimento_idTipoPagamentoRecebimento")
    @ManyToOne
    private TipoPagamentoRecebimento tipo;

    @JoinColumn(name = "TipoDespesa_idTipoDespesa")
    @ManyToOne
    private TipoDespesa tipoDespesa;

    @JoinColumn(name = "Venda_idVenda")
    @ManyToOne
    private Venda venda;

    @JsonIgnore
    @JoinColumn(name = "Despesa_idDespesa")
    @ManyToOne
    private Despesa despesa;

    @Size(max = 255)
    @Column(name = "PagamentoIdCartao", length = 255)
    private String idCartao;

    @Size(max = 255)
    @Column(name = "PagamentoNome", length = 255)
    private String nome;

    @Column(name = "PagamentoValor")
    private Double valor;

    @Column(name = "PagamentoDesconto")
    private Double desconto;

    @Column(name = "PagamentoTaxa")
    private Double taxa;

    @Column(name = "PagamentoValorFinal")
    private Double valorFinal;

    @Column(name = "PagamentoData")
    @Temporal(TemporalType.DATE)
    private Date pagamentoData;

    @Column(name = "PagamentoVencimento")
    @Temporal(TemporalType.DATE)
    private Date vencimento;

    @Size(max = 45)
    @Column(name = "PagamentoParcelado", length = 45)
    private String parcelado;

    @Column(name = "PagamentoQuantidadeParcelas")
    private Integer quantidadeParcelas;

    @Column(name = "PagamentoNumeroParcela")
    private Integer nParcelas;

    @Column(name = "PagamentoStatus")
    private String status;

    @Column(name = "PagamentoEmissao")
    private Date emissao;

    public Pagamento() {
    }

    public Pagamento(Integer id, CentroDeCusto centroDeCusto, Fornecedor fornecedor, TipoPagamentoRecebimento tipo,
                     TipoDespesa tipoDespesa, Venda venda, Despesa despesa, String idCartao, String nome,
                     Double valor, Double desconto, Double taxa, Double valorFinal, Date pagamentoData,
                     Date vencimento, String parcelado, Integer quantidadeParcelas, Integer nParcelas,
                     String status, Date emissao) {
        this.id = id;
        this.centroDeCusto = centroDeCusto;
        this.fornecedor = fornecedor;
        this.tipo = tipo;
        this.tipoDespesa = tipoDespesa;
        this.venda = venda;
        this.despesa = despesa;
        this.idCartao = idCartao;
        this.nome = nome;
        this.valor = valor;
        this.desconto = desconto;
        this.taxa = taxa;
        this.valorFinal = valorFinal;
        this.pagamentoData = pagamentoData;
        this.vencimento = vencimento;
        this.parcelado = parcelado;
        this.quantidadeParcelas = quantidadeParcelas;
        this.nParcelas = nParcelas;
        this.status = status;
        this.emissao = emissao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CentroDeCusto getCentroDeCusto() {
        return centroDeCusto;
    }

    public void setCentroDeCusto(CentroDeCusto centroDeCusto) {
        this.centroDeCusto = centroDeCusto;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public TipoPagamentoRecebimento getTipo() {
        return tipo;
    }

    public void setTipo(TipoPagamentoRecebimento tipo) {
        this.tipo = tipo;
    }

    public TipoDespesa getTipoDespesa() {
        return tipoDespesa;
    }

    public void setTipoDespesa(TipoDespesa tipoDespesa) {
        this.tipoDespesa = tipoDespesa;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Despesa getDespesa() {
        return despesa;
    }

    public void setDespesa(Despesa despesa) {
        this.despesa = despesa;
    }

    public String getIdCartao() {
        return idCartao;
    }

    public void setIdCartao(String idCartao) {
        this.idCartao = idCartao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Double getTaxa() {
        return taxa;
    }

    public void setTaxa(Double taxa) {
        this.taxa = taxa;
    }

    public Double getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(Double valorFinal) {
        this.valorFinal = valorFinal;
    }

    public Date getPagamentoData() {
        return pagamentoData;
    }

    public void setPagamentoData(Date pagamentoData) {
        this.pagamentoData = pagamentoData;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public String getParcelado() {
        return parcelado;
    }

    public void setParcelado(String parcelado) {
        this.parcelado = parcelado;
    }

    public Integer getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(Integer quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public Integer getnParcelas() {
        return nParcelas;
    }

    public void setnParcelas(Integer nParcelas) {
        this.nParcelas = nParcelas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pagamento)) return false;

        Pagamento that = (Pagamento) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

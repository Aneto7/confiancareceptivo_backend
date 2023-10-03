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
@Table(name = "RecebimentoFinanceiro", catalog = "receptivo", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"idRecebimentoFinanceiro"})})
public class RecebimentoFinanceiro implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRecebimentoFinanceiro", nullable = false)
    private Integer id;

    @JoinColumn(name = "CentroDeCusto_idCentroDeCusto")
    @ManyToOne
    private CentroDeCusto centroDeCusto;

    @JoinColumn(name = "Cliente_idCliente")
    @ManyToOne
    private Cliente cliente;

    @JoinColumn(name = "TipoPagamentoRecebimento_idTipoPagamentoRecebimento")
    @ManyToOne
    private TipoPagamentoRecebimento tipo;

    @JoinColumn(name = "TipoReceita_idTipoReceita")
    @ManyToOne
    private TipoReceita tipoReceita;

    @JoinColumn(name = "Venda_idVenda")
    @ManyToOne
    private Venda venda;

    @JsonIgnore
    @JoinColumn(name = "Recebimento_idRecebimento")
    @ManyToOne
    private Recebimento recebimento;

    @Size(max = 255)
    @Column(name = "RecebimentoIdCartao", length = 255)
    private String idCartao;

    @Size(max = 255)
    @Column(name = "RecebimentoFinanceiroNome", length = 255)
    private String nome;

    @Column(name = "RecebimentoFinanceiroValor")
    private Double valor;

    @Column(name = "RecebimentoFinanceiroDesconto")
    private Double desconto;

    @Column(name = "RecebimentoFinanceiroTaxa")
    private Double taxa;

    @Column(name = "RecebimentoFinanceiroValorFinal")
    private Double valorFinal;

    @Column(name = "RecebimentoFinanceiroData")
    @Temporal(TemporalType.DATE)
    private Date recebimentoData;

    @Column(name = "RecebimentoFinanceiroVencimento")
    @Temporal(TemporalType.DATE)
    private Date vencimento;

    @Size(max = 45)
    @Column(name = "RecebimentoFinanceiroParcelado", length = 45)
    private String parcelado;

    @Column(name = "RecebimentoFinanceiroQuantidadeParcelas")
    private Integer quantidadeParcelas;

    @Column(name = "RecebimentoFinanceiroNumeroParcela")
    private Integer nParcelas;

    @Column(name = "RecebimentoFinanceiroStatus")
    private String status;

    @Column(name = "RecebimentoFinanceiroEmissao")
    private Date emissao;

    public RecebimentoFinanceiro() {
    }

    public RecebimentoFinanceiro(Integer id, CentroDeCusto centroDeCusto, Cliente cliente,
                                 TipoPagamentoRecebimento tipo, TipoReceita tipoReceita,
                                 Venda venda, Recebimento recebimento, String idCartao,
                                 String nome, Double valor, Double desconto, Double taxa,
                                 Double valorFinal, Date recebimentoData, Date vencimento,
                                 String parcelado, Integer quantidadeParcelas, Integer nParcelas,
                                 String status, Date emissao) {
        this.id = id;
        this.centroDeCusto = centroDeCusto;
        this.cliente = cliente;
        this.tipo = tipo;
        this.tipoReceita = tipoReceita;
        this.venda = venda;
        this.recebimento = recebimento;
        this.idCartao = idCartao;
        this.nome = nome;
        this.valor = valor;
        this.desconto = desconto;
        this.taxa = taxa;
        this.valorFinal = valorFinal;
        this.recebimentoData = recebimentoData;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public TipoPagamentoRecebimento getTipo() {
        return tipo;
    }

    public void setTipo(TipoPagamentoRecebimento tipo) {
        this.tipo = tipo;
    }

    public TipoReceita getTipoReceita() {
        return tipoReceita;
    }

    public void setTipoReceita(TipoReceita tipoReceita) {
        this.tipoReceita = tipoReceita;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Recebimento getRecebimento() {
        return recebimento;
    }

    public void setRecebimento(Recebimento recebimento) {
        this.recebimento = recebimento;
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

    public Date getRecebimentoData() {
        return recebimentoData;
    }

    public void setRecebimentoData(Date recebimentoData) {
        this.recebimentoData = recebimentoData;
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
        if (!(o instanceof RecebimentoFinanceiro)) return false;

        RecebimentoFinanceiro that = (RecebimentoFinanceiro) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

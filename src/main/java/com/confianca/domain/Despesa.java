/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.confianca.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Despesa", catalog = "receptivo", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"idDespesa"})})
public class Despesa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDespesa", nullable = false)
    private Integer id;

    @JoinColumn(name = "Servico_idServico")
    @ManyToOne
    private Servico servico;

    @JoinColumn(name = "Guia_idGuia")
    @ManyToOne
    private Guia guia;

    @JoinColumn(name = "CentroDeCusto_idCentroDeCusto")
    @ManyToOne
    private CentroDeCusto centroDeCusto;

    @JoinColumn(name = "TipoDespesa_idTipoDespesa")
    @ManyToOne
    private TipoDespesa tipoDespesa;

    @JoinColumn(name = "Fornecedor_idFornecedor")
    @ManyToOne
    private Fornecedor fornecedor;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "DespesaNome", nullable = false, length = 255)
    private String nome;

    @NotNull
    @Column(name = "DespesaValor", nullable = false)
    private Double valor;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "DespesaStatus", nullable = false, length = 255)
    private String status;

    @NotNull
    @Column(name = "DespesaVencimento", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date vencimento;

    @Column(name = "DespesaDataPagamento")
    @Temporal(TemporalType.DATE)
    private Date dataPagamento;

    @NotNull
    @Column(name = "DespesaQuantidadeParcelas")
    private Integer quantidadeParcelas;

    @NotNull
    @Column(name = "DespesaCompetencia", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date competencia;

    @Column(name = "DespesaPendente")
    private Boolean pendente;

    @Size(max = 3000)
    @Column(name = "DespesaObservacao", length = 3000)
    private String obs;

    @JoinColumn(name = "TipoPagamentoRecebimento_idTipoPagamentoRecebimento")
    @ManyToOne
    private TipoPagamentoRecebimento tipoPagamentoRecebimento;

    @OneToMany(mappedBy="despesa")
    private List<Pagamento> pagamentos;

    @Column(name = "DespesaEmissao")
    private Date emissao;

    public Despesa() {
    }

    public Despesa(Integer id, Servico servico, Guia guia, CentroDeCusto centroDeCusto, TipoDespesa tipoDespesa,
                   Fornecedor fornecedor, String nome, Double valor, String status, Date vencimento,
                   Date dataPagamento, Integer quantidadeParcelas, Date competencia, Boolean pendente,
                   String obs, TipoPagamentoRecebimento tipoPagamentoRecebimento, Date emissao) {
        this.id = id;
        this.servico = servico;
        this.guia = guia;
        this.centroDeCusto = centroDeCusto;
        this.tipoDespesa = tipoDespesa;
        this.fornecedor = fornecedor;
        this.nome = nome;
        this.valor = valor;
        this.status = status;
        this.vencimento = vencimento;
        this.dataPagamento = dataPagamento;
        this.quantidadeParcelas = quantidadeParcelas;
        this.competencia = competencia;
        this.pendente = pendente;
        this.obs = obs;
        this.tipoPagamentoRecebimento = tipoPagamentoRecebimento;
        this.emissao = emissao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Guia getGuia() {
        return guia;
    }

    public void setGuia(Guia guia) {
        this.guia = guia;
    }

    public CentroDeCusto getCentroDeCusto() {
        return centroDeCusto;
    }

    public void setCentroDeCusto(CentroDeCusto centroDeCusto) {
        this.centroDeCusto = centroDeCusto;
    }

    public TipoDespesa getTipoDespesa() {
        return tipoDespesa;
    }

    public void setTipoDespesa(TipoDespesa tipoDespesa) {
        this.tipoDespesa = tipoDespesa;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Integer getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(Integer quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public Date getCompetencia() {
        return competencia;
    }

    public void setCompetencia(Date competencia) {
        this.competencia = competencia;
    }

    public Boolean getPendente() {
        return pendente;
    }

    public void setPendente(Boolean pendente) {
        this.pendente = pendente;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public TipoPagamentoRecebimento getTipoPagamentoRecebimento() {
        return tipoPagamentoRecebimento;
    }

    public void setTipoPagamentoRecebimento(TipoPagamentoRecebimento tipoPagamentoRecebimento) {
        this.tipoPagamentoRecebimento = tipoPagamentoRecebimento;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
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
        if (!(o instanceof Despesa)) return false;

        Despesa despesa = (Despesa) o;

        return getId() != null ? getId().equals(despesa.getId()) : despesa.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

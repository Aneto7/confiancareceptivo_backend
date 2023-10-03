/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.confianca.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Receita", catalog = "receptivo", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"idReceita"})})
public class Receita implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReceita", nullable = false)
    private Integer id;

    @JoinColumn(name = "Venda_idVenda")
    @ManyToOne
    private Venda venda;

    @JoinColumn(name = "Servico_idServico")
    @ManyToOne
    private Servico servico;

    @JoinColumn(name = "CentroDeCusto_idCentroDeCusto_")
    @ManyToOne
    private CentroDeCusto centroDeCusto;

    @JoinColumn(name = "TipoReceita_idTipoReceita_")
    @ManyToOne
    private TipoReceita tipoReceita;

    @JoinColumn(name = "Cliente_idCliente")
    @ManyToOne
    private Cliente cliente;

    @JoinColumn(name = "TipoProduto_idTipoProduto")
    @ManyToOne
    private TipoProduto tipoProduto;

    @JoinColumn(name = "Recebimento_idRecebimento")
    @ManyToOne
    private Recebimento recebimento;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ReceitaNome", nullable = false, length = 255)
    private String nome;

    @NotNull
    @Column(name = "ReceitaValorTotal", nullable = false)
    private Double valorTotal;

    @NotNull
    @Column(name = "ReceitaValor", nullable = false)
    private Double valor;

    @Column(name = "ReceitaTaxa")
    private Double taxa;

    @Column(name = "ReceitaValorFinal")
    private Double valorFinal;

    @NotNull
    @Column(name = "ReceitaQuantidadeParcelas")
    private Integer quantidadeParcelas;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ReceitaStatus", nullable = false, length = 255)
    private String status;

    @NotNull
    @Column(name = "ReceitaVencimento", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date vencimento;

    @Column(name = "ReceitaDataRecebimento")
    @Temporal(TemporalType.DATE)
    private Date dataRecebimento;

    @NotNull
    @Column(name = "ReceitaCompetencia", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date competencia;

    @Column(name = "ReceitaPendente")
    private Boolean pendente;

    @Size(max = 3000)
    @Column(name = "ReceitaObservacao", length = 3000)
    private String obs;

    @JoinColumn(name = "TipoPagamentoRecebimento_idTipoPagamentoRecebimento")
    @ManyToOne
    private TipoPagamentoRecebimento tipoPagamentoRecebimento;

    @JsonIgnore
    @JoinColumn(name = "ProdutoVendido_idProdutoVendido")
    @ManyToOne
    private ProdutoVendido produtoVendido;

    @Column(name = "ReceitaEmissao")
    private Date emissao;

    public Receita() {
    }

    public Receita(Integer id, Venda venda, Servico servico, CentroDeCusto centroDeCusto,
                   TipoReceita tipoReceita, Cliente cliente, TipoProduto tipoProduto,
                   Recebimento recebimento, String nome, Double valorTotal, Double valor,
                   Double taxa, Double valorFinal, Integer quantidadeParcelas, String status,
                   Date vencimento, Date dataRecebimento, Date competencia, Boolean pendente,
                   String obs, TipoPagamentoRecebimento tipoPagamentoRecebimento,
                   ProdutoVendido produtoVendido, Date emissao) {
        this.id = id;
        this.venda = venda;
        this.servico = servico;
        this.centroDeCusto = centroDeCusto;
        this.tipoReceita = tipoReceita;
        this.cliente = cliente;
        this.tipoProduto = tipoProduto;
        this.recebimento = recebimento;
        this.nome = nome;
        this.valorTotal = valorTotal;
        this.valor = valor;
        this.taxa = taxa;
        this.valorFinal = valorFinal;
        this.quantidadeParcelas = quantidadeParcelas;
        this.status = status;
        this.vencimento = vencimento;
        this.dataRecebimento = dataRecebimento;
        this.competencia = competencia;
        this.pendente = pendente;
        this.obs = obs;
        this.tipoPagamentoRecebimento = tipoPagamentoRecebimento;
        this.produtoVendido = produtoVendido;
        this.emissao = emissao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public CentroDeCusto getCentroDeCusto() {
        return centroDeCusto;
    }

    public void setCentroDeCusto(CentroDeCusto centroDeCusto) {
        this.centroDeCusto = centroDeCusto;
    }

    public TipoReceita getTipoReceita() {
        return tipoReceita;
    }

    public void setTipoReceita(TipoReceita tipoReceita) {
        this.tipoReceita = tipoReceita;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(TipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public Recebimento getRecebimento() {
        return recebimento;
    }

    public void setRecebimento(Recebimento recebimento) {
        this.recebimento = recebimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
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

    public Integer getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(Integer quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
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

    public Date getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(Date dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
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

    public ProdutoVendido getProdutoVendido() {
        return produtoVendido;
    }

    public void setProdutoVendido(ProdutoVendido produtoVendido) {
        this.produtoVendido = produtoVendido;
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
        if (!(o instanceof Receita)) return false;

        Receita receita = (Receita) o;

        return getId() != null ? getId().equals(receita.getId()) : receita.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

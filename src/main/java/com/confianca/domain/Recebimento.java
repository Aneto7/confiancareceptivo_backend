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
@Table(name = "Recebimento", catalog = "receptivo", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"idRecebimento"})})
public class Recebimento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRecebimento", nullable = false)
    private Integer id;

    @JoinColumn(name = "TipoPagamentoRecebimento_idTipoPagamentoRecebimento")
    @ManyToOne
    private TipoPagamentoRecebimento tipo;

    @JoinColumn(name = "Venda_idVenda")
    @ManyToOne
    private Venda venda;

    @Size(max = 255)
    @Column(name = "RecebimentoIdCartao", length = 255)
    private String idCartao;

    @Size(max = 255)
    @Column(name = "RecebimentoNome", length = 255)
    private String nome;

    @Column(name = "RecebimentoValor")
    private Double valor;

    @Column(name = "RecebimentoData")
    @Temporal(TemporalType.DATE)
    private Date recebimentoData;

    @Column(name = "RecebimentoVencimento")
    @Temporal(TemporalType.DATE)
    private Date vencimento;

    @Size(max = 45)
    @Column(name = "RecebimentoParcelado", length = 45)
    private String parcelado;

    @Column(name = "RecebimentoQuantidadeParcelas")
    private Integer quantidadeParcelas;

    @Column(name = "RecebimentoNumeroParcela")
    private Integer nParcelas;

    @Column(name = "RecebimentoStatus")
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy="recebimento")
    private List<Receita> receitas;

    @OneToMany(mappedBy="recebimento")
    private List<RecebimentoFinanceiro> recebimentoFinanceiros;

    @Column(name = "RecebimentoEmissao")
    private Date emissao;

    public Recebimento() {
    }

    public Recebimento(Integer id, TipoPagamentoRecebimento tipo, Venda venda, String idCartao,
                       String nome, Double valor, Date recebimentoData, Date vencimento,
                       String parcelado, Integer quantidadeParcelas, Integer nParcelas,
                       String status, Date emissao) {
        this.id = id;
        this.tipo = tipo;
        this.venda = venda;
        this.idCartao = idCartao;
        this.nome = nome;
        this.valor = valor;
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

    public TipoPagamentoRecebimento getTipo() {
        return tipo;
    }

    public void setTipo(TipoPagamentoRecebimento tipo) {
        this.tipo = tipo;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
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

    public List<Receita> getReceitas() {
        return receitas;
    }

    public void setReceitas(List<Receita> receitas) {
        this.receitas = receitas;
    }

    public List<RecebimentoFinanceiro> getRecebimentoFinanceiros() {
        return recebimentoFinanceiros;
    }

    public void setRecebimentoFinanceiros(List<RecebimentoFinanceiro> recebimentoFinanceiros) {
        this.recebimentoFinanceiros = recebimentoFinanceiros;
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
        if (!(o instanceof Recebimento)) return false;

        Recebimento that = (Recebimento) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

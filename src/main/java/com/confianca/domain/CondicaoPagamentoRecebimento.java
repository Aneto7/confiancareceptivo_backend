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
import java.util.List;

@Entity
@Table(name = "CondicaoPagamentoRecebimento", catalog = "receptivo", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"idCondicaoPagamentoRecebimento"})})
public class CondicaoPagamentoRecebimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCondicaoPagamentoRecebimento", nullable = false)
    private Integer id;

    @JoinColumn(name = "idBandeira")
    @ManyToOne
    private BandeiraDeCartaoDeCredito bandeira;

    @JoinColumn(name = "idTipoPagamentoRecebimento")
    @ManyToOne
    private TipoPagamentoRecebimento tipo;

    @NotNull
    @Column(name = "CondicaoPagamentoRecebimentoQtdParcelas", nullable = false)
    private Integer qtdParcelas;

    @NotNull
    @Column(name = "CondicaoPagamentoRecebimentoValorMinimo", nullable = false)
    private Double valorMinimo;

    @NotNull
    @Column(name = "CondicaoPagamentoRecebimentoValorEntrada", nullable = false)
    private Double valorEntrada;

    @NotNull
    @Column(name = "CondicaoPagamentoRecebimentoPercentualEntrada", nullable = false)
    private Double percentualEntrada;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "CondicaoPagamentoRecebimentoDescricao", nullable = false, length = 255)
    private String nome;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "CondicaoPagamentoRecebimentoStatus", nullable = false, length = 255)
    private String status;

    public CondicaoPagamentoRecebimento() {
    }

    public CondicaoPagamentoRecebimento(Integer id, BandeiraDeCartaoDeCredito bandeira,
                                        TipoPagamentoRecebimento tipo, Integer qtdParcelas,
                                        Double valorMinimo, Double valorEntrada, Double percentualEntrada,
                                        String nome, String status) {
        this.id = id;
        this.bandeira = bandeira;
        this.tipo = tipo;
        this.qtdParcelas = qtdParcelas;
        this.valorMinimo = valorMinimo;
        this.valorEntrada = valorEntrada;
        this.percentualEntrada = percentualEntrada;
        this.nome = nome;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BandeiraDeCartaoDeCredito getBandeira() {
        return bandeira;
    }

    public void setBandeira(BandeiraDeCartaoDeCredito bandeira) {
        this.bandeira = bandeira;
    }

    public TipoPagamentoRecebimento getTipo() {
        return tipo;
    }

    public void setTipo(TipoPagamentoRecebimento tipo) {
        this.tipo = tipo;
    }

    public Integer getQtdParcelas() {
        return qtdParcelas;
    }

    public void setQtdParcelas(Integer qtdParcelas) {
        this.qtdParcelas = qtdParcelas;
    }

    public Double getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(Double valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public Double getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(Double valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public Double getPercentualEntrada() {
        return percentualEntrada;
    }

    public void setPercentualEntrada(Double percentualEntrada) {
        this.percentualEntrada = percentualEntrada;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CondicaoPagamentoRecebimento)) return false;

        CondicaoPagamentoRecebimento that = (CondicaoPagamentoRecebimento) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

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
@Table(name = "Tarifa", catalog = "receptivo", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"idTarifa"})})
public class Tarifa implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTarifa", nullable = false)
    private Integer id;

    @JsonIgnore
    @JoinColumn(name = "Produto_idProduto")
    @OneToOne
    private Produto produto;

    @JoinColumn(name = "DataTarifa_idDataTarifa")
    @ManyToOne
    private DataTarifa data;

    @JoinColumn(name = "Cupom_idCupom")
    @ManyToOne
    private Cupom cupom;

    @NotNull
    @Size(max = 255)
    @Column(name = "TarifaNome", nullable = false, length = 255)
    private String nome;

    @NotNull
    @Column(name = "TarifaValor", nullable = false)
    private Double valor;

    @NotNull
    @Column(name = "TarifaValorCrianca", nullable = false)
    private Double valorCrianca;

    @NotNull
    @Column(name = "TarifaValorBebe", nullable = false)
    private Double valorBebe;

    @NotNull
    @Column(name = "TarifaValorIdoso", nullable = false)
    private Double valorIdoso;

    @NotNull
    @Size(max = 255)
    @Column(name = "TarifaStatus", nullable = false, length = 255)
    private String status;

    @NotNull
    @Column(name = "TarifaCapacidade", nullable = false)
    private Integer capacidade;

    @NotNull
    @Column(name = "TarifaIdadeLimite", nullable = false)
    private Integer idadeLimite;

    @Size(max = 1000)
    @Column(name = "TarifaObservacao", length = 1000)
    private String obs;

    @JsonIgnore
    @OneToMany(mappedBy="tarifa")
    private List<ProdutoVendido> produtosVendidos;

    public Tarifa() {
    }

    public Tarifa(Integer id, Produto produto, DataTarifa data, Cupom cupom, String nome,
                  Double valor, Double valorCrianca, Double valorBebe, Double valorIdoso,
                  String status, Integer capacidade, Integer idadeLimite, String obs) {
        this.id = id;
        this.produto = produto;
        this.data = data;
        this.cupom = cupom;
        this.nome = nome;
        this.valor = valor;
        this.valorCrianca = valorCrianca;
        this.valorBebe = valorBebe;
        this.valorIdoso = valorIdoso;
        this.status = status;
        this.capacidade = capacidade;
        this.idadeLimite = idadeLimite;
        this.obs = obs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public DataTarifa getData() {
        return data;
    }

    public void setData(DataTarifa data) {
        this.data = data;
    }

    public Cupom getCupom() {
        return cupom;
    }

    public void setCupom(Cupom cupom) {
        this.cupom = cupom;
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

    public Double getValorCrianca() {
        return valorCrianca;
    }

    public void setValorCrianca(Double valorCrianca) {
        this.valorCrianca = valorCrianca;
    }

    public Double getValorBebe() {
        return valorBebe;
    }

    public void setValorBebe(Double valorBebe) {
        this.valorBebe = valorBebe;
    }

    public Double getValorIdoso() {
        return valorIdoso;
    }

    public void setValorIdoso(Double valorIdoso) {
        this.valorIdoso = valorIdoso;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public Integer getIdadeLimite() {
        return idadeLimite;
    }

    public void setIdadeLimite(Integer idadeLimite) {
        this.idadeLimite = idadeLimite;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public List<ProdutoVendido> getProdutosVendidos() {
        return produtosVendidos;
    }

    public void setProdutosVendidos(List<ProdutoVendido> produtosVendidos) {
        this.produtosVendidos = produtosVendidos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tarifa)) return false;

        Tarifa tarifa = (Tarifa) o;

        return getId() != null ? getId().equals(tarifa.getId()) : tarifa.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

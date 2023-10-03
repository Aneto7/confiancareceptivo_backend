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
import java.util.List;

@Entity
@Table(name = "TrechoProduto", catalog = "receptivo", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"idTrechoProduto"})})
public class TrechoProduto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTrechoProduto", nullable = false)
    private Integer id;

    @JsonIgnore
    @JoinColumn(name = "Produto_idProduto")
    @ManyToOne
    private Produto produto;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "TrechoProdutoNome", nullable = false, length = 255)
    private String nome;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "TrechoProdutoOrigem", nullable = false, length = 255)
    private String origem;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "TrechoProdutoDestino", nullable = false, length = 255)
    private String destino;

    @NotNull
    @Column(name = "TrechoProdutoValor", nullable = false)
    private Double valor;

    @Basic(optional = false)
    @Size(max = 5000)
    @Column(name = "TrechoProdutoDescricao", length = 5000)
    private String descricao;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "TrechoProdutoStatus", nullable = false, length = 255)
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy="trechoProduto")
    private List<ProdutoVendido> produtosVendidos;

    public TrechoProduto() {
    }

    public TrechoProduto(Integer id, Produto produto, String nome, String origem, String destino, Double valor, String descricao, String status) {
        this.id = id;
        this.produto = produto;
        this.nome = nome;
        this.origem = origem;
        this.destino = destino;
        this.valor = valor;
        this.descricao = descricao;
        this.status = status;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(o instanceof TrechoProduto)) return false;

        TrechoProduto that = (TrechoProduto) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

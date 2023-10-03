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
@Table(name = "OpcionalVendido", catalog = "receptivo", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"idOpcionalVendido"})})
public class OpcionalVendido implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idOpcionalVendido", nullable = false)
    private Integer id;

    @JsonIgnore
    @JoinColumn(name = "ProdutoVendido_idProdutoVendido")
    @ManyToOne
    private ProdutoVendido produtoVendido;

    @NotNull
    @Size(max = 255)
    @Column(name = "OpcionalVendidoNome", nullable = false, length = 255)
    private String nome;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "OpcionalVendidoStatus", nullable = false, length = 255)
    private String status;

    @NotNull
    @Column(name = "OpcionalVendidoValor", nullable = false)
    private Double valor;

    @NotNull
    @Column(name = "OpcionalVendidoQuantidade", nullable = false)
    private Integer quantidade;

    public OpcionalVendido() {
    }

    public OpcionalVendido(Integer id, ProdutoVendido produtoVendido, String nome, String status,
                           Double valor, Integer quantidade) {
        this.id = id;
        this.produtoVendido = produtoVendido;
        this.nome = nome;
        this.status = status;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProdutoVendido getProdutoVendido() {
        return produtoVendido;
    }

    public void setProdutoVendido(ProdutoVendido produtoVendido) {
        this.produtoVendido = produtoVendido;
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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OpcionalVendido)) return false;

        OpcionalVendido that = (OpcionalVendido) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

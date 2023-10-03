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

@Entity
@Table(name = "DetalheProduto", catalog = "receptivo", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"idDetalheProduto"})})
public class DetalheProduto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDetalheProduto", nullable = false)
    private Integer id;

    @JsonIgnore
    @JoinColumn(name = "Produto_idProduto")
    @ManyToOne
    private Produto produto;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "DetalheProdutoNome", nullable = false, length = 255)
    private String nome;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5000)
    @Column(name = "DetalheProdutoDescricao", nullable = false, length = 5000)
    private String descricao;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "DetalheProdutoStatus", nullable = false, length = 255)
    private String status;

    public DetalheProduto() {
    }

    public DetalheProduto(Integer id, Produto produto, String nome, String descricao, String status) {
        this.id = id;
        this.produto = produto;
        this.nome = nome;
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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetalheProduto)) return false;

        DetalheProduto that = (DetalheProduto) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

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
@Table(name = "TipoProduto", catalog = "receptivo", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"idTipoProduto"})})
public class TipoProduto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTipoProduto", nullable = false)
    private Integer id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "TipoProdutoNome", nullable = false, length = 255, unique = true)
    private String nome;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "TipoProdutoStatus", nullable = false, length = 255)
    private String status;

    @NotNull
    @Column(name = "TipoProdutoPlanilha")
    private Boolean planilha;

    @JsonIgnore
    @OneToMany(mappedBy="tipoProduto")
    private List<Produto> produtos;

    @JsonIgnore
    @OneToMany(mappedBy="tipoProduto")
    private List<Servico> servicos;

    @JsonIgnore
    @OneToMany(mappedBy="tipoProduto")
    private List<Receita> receitas;

    public TipoProduto() {
    }

    public TipoProduto(Integer id, String nome, String status, Boolean planilha) {
        this.id = id;
        this.nome = nome;
        this.status = status;
        this.planilha = planilha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Boolean getPlanilha() {
        return planilha;
    }

    public void setPlanilha(Boolean planilha) {
        this.planilha = planilha;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }

    public List<Receita> getReceitas() {
        return receitas;
    }

    public void setReceitas(List<Receita> receitas) {
        this.receitas = receitas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoProduto)) return false;

        TipoProduto that = (TipoProduto) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

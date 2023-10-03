package com.confianca.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Cidade", catalog = "receptivo", schema = "", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"idCidade"})})
public class Cidade implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCidade", nullable = false)
    private Integer id;

    @NotNull
    @JoinColumn(name = "Estado_idEstado", nullable = false)
    @ManyToOne
    private Estado estado;

    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "CidadeNome", nullable = false, length = 45)
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy="cidade")
    private List<Cliente> clientes;

    @JsonIgnore
    @OneToMany(mappedBy="cidade")
    private List<Fornecedor> fornecedores;

    @JsonIgnore
    @OneToMany(mappedBy="cidade")
    private List<Unidade> unidades;

    @JsonIgnore
    @OneToMany(mappedBy="cidade")
    private List<Produto> produtos;

    @JsonIgnore
    @OneToMany(mappedBy="cidade")
    private List<PontoDeSaida> pontoDeSaidas;

    public Cidade() {
    }

    public Cidade(Integer id, Estado estado, String nome) {
        this.id = id;
        this.estado = estado;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(List<Fornecedor> fornecedores) {
        this.fornecedores = fornecedores;
    }

    public List<Unidade> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<Unidade> unidades) {
        this.unidades = unidades;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<PontoDeSaida> getPontoDeSaidas() {
        return pontoDeSaidas;
    }

    public void setPontoDeSaidas(List<PontoDeSaida> pontoDeSaidas) {
        this.pontoDeSaidas = pontoDeSaidas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cidade)) return false;

        Cidade cidade = (Cidade) o;

        return getId() != null ? getId().equals(cidade.getId()) : cidade.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

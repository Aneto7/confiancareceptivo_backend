package com.confianca.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PontoDeSaida", catalog = "receptivo", schema = "", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"idPontoDeSaida"})})
public class PontoDeSaida implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPontoDeSaida", nullable = false)
    private Integer id;

    @JoinColumn(name = "idCidade")
    @ManyToOne
    private Cidade cidade;

    @NotNull()
    @Size(min = 1, max = 255)
    @Column(name = "PontoDeSaidaNome", nullable = false, length = 255, unique = true)
    private String nome;

    @NotNull()
    @Size(min = 1, max = 255)
    @Column(name = "PontoDeSaidaEndereco", nullable = false, length = 255)
    private String endereco;

    @NotNull()
    @Size(min = 1, max = 255)
    @Column(name = "PontoDeSaidaStatus", nullable = false, length = 255)
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy="pontoDeSaida")
    private List<Produto> produtos;

    public PontoDeSaida() {
    }

    public PontoDeSaida(Integer id, Cidade cidade, String nome, String endereco, String status) {
        this.id = id;
        this.cidade = cidade;
        this.nome = nome;
        this.endereco = endereco;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PontoDeSaida)) return false;

        PontoDeSaida that = (PontoDeSaida) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

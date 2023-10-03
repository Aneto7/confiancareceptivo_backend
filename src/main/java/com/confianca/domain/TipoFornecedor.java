package com.confianca.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "TipoFornecedor", catalog = "receptivo", schema = "", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"idTipoFornecedor"})})
public class TipoFornecedor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTipoFornecedor", nullable = false)
    private Integer id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "TipoFornecedorNome", nullable = false, length = 255, unique = true)
    private String nome;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "TipoFornecedorStatus", nullable = false, length = 255)
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy="tipoFornecedor")
    private List<Fornecedor> fornecedores;

    public TipoFornecedor() {
    }

    public TipoFornecedor(Integer id, String nome, String status) {
        this.id = id;
        this.nome = nome;
        this.status = status;
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

    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(List<Fornecedor> fornecedores) {
        this.fornecedores = fornecedores;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoFornecedor)) return false;

        TipoFornecedor that = (TipoFornecedor) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

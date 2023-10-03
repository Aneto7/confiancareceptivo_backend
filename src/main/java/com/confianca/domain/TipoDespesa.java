package com.confianca.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "TipoDespesa", catalog = "receptivo", schema = "", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"idTipoDespesa"})})
public class TipoDespesa implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTipoDespesa", nullable = false)
    private Integer id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "TipoDespesaNome", nullable = false, length = 255, unique = true)
    private String nome;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "TipoDespesaStatus", nullable = false, length = 255)
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy="tipoDespesa")
    private List<Despesa> despesas;

    @JsonIgnore
    @OneToMany(mappedBy="tipoDespesa")
    private List<Fornecedor> fornecedores;

    public TipoDespesa() {
    }

    public TipoDespesa(Integer id, String nome, String status) {
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

    public List<Despesa> getDespesas() {
        return despesas;
    }

    public void setDespesas(List<Despesa> despesas) {
        this.despesas = despesas;
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
        if (!(o instanceof TipoDespesa)) return false;

        TipoDespesa that = (TipoDespesa) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

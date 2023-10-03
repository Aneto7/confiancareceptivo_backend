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
@Table(name = "CentroDeCusto", catalog = "receptivo")
public class CentroDeCusto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCentroDeCusto", nullable = false)
    private Integer id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "CentroDeCustoNome", nullable = false, length = 255, unique = true)
    private String nome;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "CentroDeCustoStatus", nullable = false, length = 255)
    private String status;

    @Size(max = 3000)
    @Column(name = "CentroDeCustoObservacao", length = 3000)
    private String obs;

    @JsonIgnore
    @OneToMany(mappedBy="centroDeCusto")
    private List<Despesa> despesas;

    @JsonIgnore
    @OneToMany(mappedBy="centroDeCusto")
    private List<Receita> receitas;

    public CentroDeCusto() {
    }

    public CentroDeCusto(Integer id, String nome, String status, String obs) {
        this.id = id;
        this.nome = nome;
        this.status = status;
        this.obs = obs;
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

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public List<Despesa> getDespesas() {
        return despesas;
    }

    public void setDespesas(List<Despesa> despesas) {
        this.despesas = despesas;
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
        if (!(o instanceof CentroDeCusto)) return false;

        CentroDeCusto that = (CentroDeCusto) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

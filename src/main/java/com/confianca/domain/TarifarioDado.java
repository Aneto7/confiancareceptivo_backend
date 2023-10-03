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

@Entity
@Table(name = "TarifarioDado", catalog = "receptivo", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"idTarifarioDado"})})
public class TarifarioDado implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTarifarioDado", nullable = false)
    private Integer id;

    @JsonIgnore
    @JoinColumn(name = "TarifarioDado_idTarifarioDado")
    @OneToOne
    private Tarifario tarifario;

    @NotNull
    @Size(max = 255)
    @Column(name = "TarifarioDadoNome", nullable = false, length = 255)
    private String nome;

    @NotNull
    @Column(name = "TarifarioDadoQuantidade", nullable = false)
    private Integer quantidade;

    @NotNull
    @Column(name = "TarifarioDadoValor", nullable = false)
    private Double valor;

    @NotNull
    @Size(max = 255)
    @Column(name = "TarifarioDadoStatus", nullable = false, length = 255)
    private String status;

    @Size(max = 1000)
    @Column(name = "TarifarioDadoObservacao", length = 1000)
    private String obs;

    public TarifarioDado() {
    }

    public TarifarioDado(Integer id, Tarifario tarifario, String nome, Integer quantidade, Double valor, String status, String obs) {
        this.id = id;
        this.tarifario = tarifario;
        this.nome = nome;
        this.quantidade = quantidade;
        this.valor = valor;
        this.status = status;
        this.obs = obs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tarifario getTarifario() {
        return tarifario;
    }

    public void setTarifario(Tarifario tarifario) {
        this.tarifario = tarifario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TarifarioDado)) return false;

        TarifarioDado tarifa = (TarifarioDado) o;

        return getId() != null ? getId().equals(tarifa.getId()) : tarifa.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

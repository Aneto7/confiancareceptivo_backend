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
@Table(name = "Tarifario", catalog = "receptivo", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"idTarifario"})})
public class Tarifario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTarifario", nullable = false)
    private Integer id;

    @JsonIgnore
    @JoinColumn(name = "Produto_idProduto")
    @OneToOne
    private Produto produto;

    @NotNull
    @Size(max = 255)
    @Column(name = "TarifarioNome", nullable = false, length = 255)
    private String nome;

    @NotNull
    @Size(max = 255)
    @Column(name = "TarifarioStatus", nullable = false, length = 255)
    private String status;

    @Size(max = 4000)
    @Column(name = "TarifarioObservacao", length = 4000)
    private String obs;

    @Size(max = 4000)
    @Column(name = "TarifarioItinerario", length = 4000)
    private String itinerario;

    @Size(max = 4000)
    @Column(name = "TarifarioDescritivo", length = 4000)
    private String descritivo;

    @Size(max = 3000)
    @Column(name = "TarifarioInformacoes", length = 3000)
    private String informacoes;

    @OneToMany(mappedBy="tarifario")
    private List<TarifarioDado> tarifarioDados;

    public Tarifario() {
    }

    public Tarifario(Integer id, Produto produto, String nome, String status, String obs, String itinerario, String descritivo, String informacoes) {
        this.id = id;
        this.produto = produto;
        this.nome = nome;
        this.status = status;
        this.obs = obs;
        this.itinerario = itinerario;
        this.descritivo = descritivo;
        this.informacoes = informacoes;
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

    public String getItinerario() {
        return itinerario;
    }

    public void setItinerario(String itinerario) {
        this.itinerario = itinerario;
    }

    public String getDescritivo() {
        return descritivo;
    }

    public void setDescritivo(String descritivo) {
        this.descritivo = descritivo;
    }

    public String getInformacoes() {
        return informacoes;
    }

    public void setInformacoes(String informacoes) {
        this.informacoes = informacoes;
    }

    public List<TarifarioDado> getTarifarioDado() {
        return tarifarioDados;
    }

    public void setTarifarioDado(List<TarifarioDado> tarifarioDados) {
        this.tarifarioDados = tarifarioDados;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tarifario)) return false;

        Tarifario tarifa = (Tarifario) o;

        return getId() != null ? getId().equals(tarifa.getId()) : tarifa.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

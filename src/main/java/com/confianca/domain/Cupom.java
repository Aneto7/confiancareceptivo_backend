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
@Table(name = "Cupom", catalog = "receptivo", schema = "", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"idCupom"})})
public class Cupom implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCupom", nullable = false)
    private Integer id;

    @Size(min = 1, max = 255)
    @Column(name = "CupomNome", nullable = false, length = 255)
    private String nome;

    @NotNull
    @Column(name = "CupomValorDesconto", nullable = false)
    private Double valorDesconto;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "CupomSatus", nullable = false, length = 255)
    private String status;

    @Size( max = 3000)
    @Column(name = "CupomObservacao", length = 3000)
    private String obs;

    @JsonIgnore
    @OneToMany(mappedBy="cupom")
    private List<Cliente> clientes;

    @JsonIgnore
    @OneToMany(mappedBy="cupom")
    private List<Produto> produtos;

    @JsonIgnore
    @OneToMany(mappedBy="cupom")
    private List<Tarifa> tarifas;

    public Cupom() {
    }

    public Cupom(Integer id, String nome, Double valorDesconto, String status, String obs) {
        this.id = id;
        this.nome = nome;
        this.valorDesconto = valorDesconto;
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

    public Double getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(Double valorDesconto) {
        this.valorDesconto = valorDesconto;
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

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<Tarifa> getTarifas() {
        return tarifas;
    }

    public void setTarifas(List<Tarifa> tarifas) {
        this.tarifas = tarifas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cupom)) return false;

        Cupom cupom = (Cupom) o;

        return getId() != null ? getId().equals(cupom.getId()) : cupom.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

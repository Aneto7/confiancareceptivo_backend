/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.confianca.dto;

import com.confianca.domain.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

public class TarifaDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private ProdutoDTO produto;
    private DataTarifa data;
    private Cupom cupom;
    private String nome;
    private Double valor;

    private Double valorCrianca;

    private Double valorBebe;

    private Double valorIdoso;

    private Integer capacidade;

    private Integer idadeLimite;
    private String status;
    private String obs;

    public TarifaDTO() {
    }

    public TarifaDTO(Tarifa obj) {
        id = obj.getId();
        produto = new ProdutoDTO(obj.getProduto());
        data = obj.getData();
        cupom = obj.getCupom();
        nome = obj.getNome();
        valor = obj.getValor();
        valorCrianca = obj.getValorCrianca();
        valorBebe = obj.getValorBebe();
        valorIdoso = obj.getValorIdoso();
        capacidade = obj.getCapacidade();
        idadeLimite = obj.getIdadeLimite();
        status = obj.getStatus();
        obs = obj.getObs();
    }

    public TarifaDTO(Integer id, ProdutoDTO produto, DataTarifa data, Cupom cupom, String nome,
                     Double valor, String status, String obs) {
        this.id = id;
        this.produto = produto;
        this.data = data;
        this.cupom = cupom;
        this.nome = nome;
        this.valor = valor;
        this.status = status;
        this.obs = obs;
    }

    public TarifaDTO(Integer id, ProdutoDTO produto, DataTarifa data, Cupom cupom,
                     String nome, Double valor, Double valorCrianca, Double valorBebe, Double valorIdoso,
                     Integer capacidade, Integer idadeLimite, String status, String obs) {
        this.id = id;
        this.produto = produto;
        this.data = data;
        this.cupom = cupom;
        this.nome = nome;
        this.valor = valor;
        this.valorCrianca = valorCrianca;
        this.valorBebe = valorBebe;
        this.valorIdoso = valorIdoso;
        this.capacidade = capacidade;
        this.idadeLimite = idadeLimite;
        this.status = status;
        this.obs = obs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProdutoDTO getProduto() {
        return produto;
    }

    public void setProduto(ProdutoDTO produto) {
        this.produto = produto;
    }

    public DataTarifa getData() {
        return data;
    }

    public void setData(DataTarifa data) {
        this.data = data;
    }

    public Cupom getCupom() {
        return cupom;
    }

    public void setCupom(Cupom cupom) {
        this.cupom = cupom;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getValorCrianca() {
        return valorCrianca;
    }

    public void setValorCrianca(Double valorCrianca) {
        this.valorCrianca = valorCrianca;
    }

    public Double getValorBebe() {
        return valorBebe;
    }

    public void setValorBebe(Double valorBebe) {
        this.valorBebe = valorBebe;
    }

    public Double getValorIdoso() {
        return valorIdoso;
    }

    public void setValorIdoso(Double valorIdoso) {
        this.valorIdoso = valorIdoso;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public Integer getIdadeLimite() {
        return idadeLimite;
    }

    public void setIdadeLimite(Integer idadeLimite) {
        this.idadeLimite = idadeLimite;
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
        if (!(o instanceof TarifaDTO)) return false;

        TarifaDTO tarifa = (TarifaDTO) o;

        return getId() != null ? getId().equals(tarifa.getId()) : tarifa.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

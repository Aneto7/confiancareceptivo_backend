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

public class ProdutoVendidoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Produto produto;

    private Tarifa tarifa;

    private Servico servico;

    private Venda venda;

    private String nome;

    private Double valor;

    private String status;

    private List<Passageiro> passageiros;

    private List<OpcionalVendido> opcionaisVendidos;

    public ProdutoVendidoDTO() {
    }

    public ProdutoVendidoDTO(ProdutoVendido obj) {
        id = obj.getId();
        produto = obj.getProduto();
        tarifa = obj.getTarifa();
        servico = obj.getServico();
        venda = obj.getVenda();
        nome = obj.getNome();
        valor = obj.getValor();
        status = obj.getStatus();
        passageiros = obj.getPassageiros();
        opcionaisVendidos = obj.getOpcionaisVendidos();
    }

    public ProdutoVendidoDTO(Integer id, Produto produto, Tarifa tarifa,
                             Servico servico, Venda venda, String nome,
                             Double valor, String status, List<Passageiro> passageiros, List<OpcionalVendido> opcionaisVendidos) {
        this.id = id;
        this.produto = produto;
        this.tarifa = tarifa;
        this.servico = servico;
        this.venda = venda;
        this.nome = nome;
        this.valor = valor;
        this.status = status;
        this.passageiros = passageiros;
        this.opcionaisVendidos = opcionaisVendidos;
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

    public Tarifa getTarifa() {
        return tarifa;
    }

    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Passageiro> getPassageiros() {
        return passageiros;
    }

    public void setPassageiros(List<Passageiro> passageiros) {
        this.passageiros = passageiros;
    }

    public List<OpcionalVendido> getOpcionaisVendidos() {
        return opcionaisVendidos;
    }

    public void setOpcionaisVendidos(List<OpcionalVendido> opcionaisVendidos) {
        this.opcionaisVendidos = opcionaisVendidos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProdutoVendidoDTO)) return false;

        ProdutoVendidoDTO tarifa = (ProdutoVendidoDTO) o;

        return getId() != null ? getId().equals(tarifa.getId()) : tarifa.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

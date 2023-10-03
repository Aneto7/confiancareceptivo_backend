/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.confianca.dto;

import com.confianca.domain.ProdutoVendido;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ProdutoVendidoVendaDTO implements Serializable {
    private Integer id;
    private String nome;
    private Date dataTarifaInicio;
    private Double valor;

    public ProdutoVendidoVendaDTO() {
    }

    public ProdutoVendidoVendaDTO(ProdutoVendido obj) {
        id = obj.getId();
        nome = obj.getNome();
        dataTarifaInicio = obj.getTarifa().getData().getData();
        valor = obj.getValor();
    }

    public ProdutoVendidoVendaDTO(Integer id, String nome, Date dataTarifaInicio, Double valor) {
        this.id = id;
        this.nome = nome;
        this.dataTarifaInicio = dataTarifaInicio;
        this.valor = valor;
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

    public Date getDataTarifaInicio() {
        return dataTarifaInicio;
    }

    public void setDataTarifaInicio(Date dataTarifaInicio) {
        this.dataTarifaInicio = dataTarifaInicio;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}

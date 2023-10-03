/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.confianca.dto;

import com.confianca.domain.OpcionalVendido;

import java.io.Serializable;

public class OpcionalVendidoDTO implements Serializable {
    private Integer id;
    private String nome;
    private Double valor;

    public OpcionalVendidoDTO() {
    }

    public OpcionalVendidoDTO(OpcionalVendido obj) {
        id = obj.getId();
        nome = obj.getNome();
        valor = obj.getValor();
    }

    public OpcionalVendidoDTO(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public OpcionalVendidoDTO(Integer id, String nome, Double valor) {
        this.id = id;
        this.nome = nome;
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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}

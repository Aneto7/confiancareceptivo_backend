/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.confianca.dto;

import com.confianca.domain.DetalheProduto;
import com.confianca.domain.OpcionalVendido;
import com.confianca.domain.Passageiro;

import java.io.Serializable;
import java.util.Date;

public class DetalhesProdutoDTO implements Serializable {
    private Integer id;
    private String nome;
    private String descricao;

    public DetalhesProdutoDTO() {
    }

    public DetalhesProdutoDTO(DetalheProduto obj) {
        id = obj.getId();
        nome = obj.getNome();
        descricao = obj.getDescricao();
    }

    public DetalhesProdutoDTO(Integer id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

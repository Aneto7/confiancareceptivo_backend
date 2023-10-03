/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.confianca.dto;

import com.confianca.domain.Passageiro;
import com.confianca.domain.ProdutoVendido;
import com.confianca.domain.Receita;

import java.io.Serializable;
import java.util.Date;

public class PassageiroDTO implements Serializable {
    private Integer id;
    private String nome;
    private String cpf;
    private Date nascimento;
    private String celular;
    private String email;

    public PassageiroDTO() {
    }

    public PassageiroDTO(Passageiro obj) {
        id = obj.getId();
        nome = obj.getNome();
        cpf = obj.getCpf();
        nascimento = obj.getNascimento();
        celular = obj.getCelular();
        email = obj.getEmail();
    }

    public PassageiroDTO(Integer id, String nome, String cpf, Date nascimento, String celular, String email) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.nascimento = nascimento;
        this.celular = celular;
        this.email = email;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

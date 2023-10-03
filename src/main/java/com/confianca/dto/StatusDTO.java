/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.confianca.dto;

import com.confianca.domain.Servico;
import com.confianca.domain.Venda;

import java.io.Serializable;

public class StatusDTO implements Serializable {
    private String nome;

    public StatusDTO() {
    }

    public StatusDTO(Venda obj) {
        nome = obj.getStatus();
    }

    public StatusDTO(Servico obj) {
        nome = obj.getStatus();
    }

    public StatusDTO(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

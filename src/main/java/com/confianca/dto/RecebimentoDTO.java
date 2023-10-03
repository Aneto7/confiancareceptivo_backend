/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.confianca.dto;


import com.confianca.domain.RecebimentoFinanceiro;

import java.io.Serializable;

public class RecebimentoDTO implements Serializable {
    private Integer mes;

    private Double valor;

    public RecebimentoDTO() {
    }

    public RecebimentoDTO(RecebimentoFinanceiro obj) {
        mes = obj.getVencimento().getMonth();
        valor = obj.getValor();
    }

    public RecebimentoDTO(Integer mes, Double valor) {
        this.mes = mes;
        this.valor = valor;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}

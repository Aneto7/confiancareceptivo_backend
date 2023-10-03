/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.confianca.dto;

import com.confianca.domain.Despesa;
import com.confianca.domain.Receita;

import java.io.Serializable;
import java.util.Date;

public class ExtratoDTO implements Serializable {
    private Integer id;
    private String idServico;
    private String servico;
    private Date data;
    private String tipoPagamento;
    private String tipo;
    private Double valor;
    private String status;

    public ExtratoDTO() {
    }

    public ExtratoDTO(Despesa obj) {
        id = obj.getId();
        if(obj.getServico() != null){
            idServico = obj.getServico().getIdentificador();
            servico = obj.getServico().getNome();
        }
        data = obj.getVencimento();
        tipoPagamento = obj.getTipoPagamentoRecebimento().getNome();
        tipo = "Despesa";
        valor = obj.getValor();
        status = obj.getStatus();
    }

    public ExtratoDTO(Receita obj) {
        id = obj.getId();
        if(obj.getServico() != null){
            idServico = obj.getServico().getIdentificador();
            servico = obj.getServico().getNome();
        }
        data = obj.getVencimento();
        tipoPagamento = obj.getTipoPagamentoRecebimento().getNome();
        tipo = "Receita";
        valor = obj.getValor();
        status = obj.getStatus();
    }

    public ExtratoDTO(Integer id, String idServico, String servico, Date data, String tipo, String tipoPagamento, Double valor, String status) {
        this.id = id;
        this.idServico = idServico;
        this.servico = servico;
        this.data = data;
        this.tipo = tipo;
        this.tipoPagamento = tipoPagamento;
        this.valor = valor;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdServico() {
        return idServico;
    }

    public void setIdServico(String idServico) {
        this.idServico = idServico;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
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
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.confianca.dto;

import com.confianca.domain.Despesa;
import com.confianca.domain.Receita;
import com.confianca.domain.Servico;

import java.io.Serializable;
import java.util.Date;

public class FechamentoDTO implements Serializable {
    private String idServico;
    private String servico;
    private String cliente;
    private String fornecedor;
    private String pagador;
    private Double receita;
    private Double despesa;
    private Double valor;

    public FechamentoDTO() {
    }

    public FechamentoDTO(Servico servico, Double somaReceita, Double somaDespesa) {
        this.idServico = servico.getIdentificador();
        this.servico = servico.getNome();
        this.cliente = "";
        this.fornecedor = "";
        this.pagador = "";
        this.receita = somaReceita;
        this.despesa = somaDespesa;
        this.valor = somaReceita - somaDespesa;
    }

    public FechamentoDTO(String idServico, String servico, String cliente, String fornecedor, String pagador, Double receita, Double despesa, Double valor) {
        this.idServico = idServico;
        this.servico = servico;
        this.cliente = cliente;
        this.fornecedor = fornecedor;
        this.pagador = pagador;
        this.receita = receita;
        this.despesa = despesa;
        this.valor = valor;
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

    public Double getReceita() {
        return receita;
    }

    public void setReceita(Double receita) {
        this.receita = receita;
    }

    public Double getDespesa() {
        return despesa;
    }

    public void setDespesa(Double despesa) {
        this.despesa = despesa;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getPagador() {
        return pagador;
    }

    public void setPagador(String pagador) {
        this.pagador = pagador;
    }
}

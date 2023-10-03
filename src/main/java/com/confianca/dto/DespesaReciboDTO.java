/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.confianca.dto;

import com.confianca.domain.Despesa;
import com.confianca.domain.Receita;
import com.confianca.services.Extenso;

import java.io.Serializable;
import java.util.Date;

public class DespesaReciboDTO implements Serializable {
    private Integer id;
    private String nome;
    private Date pagamento;
    private Date vencimento;
    private Double valor;
    private String status;
    private String identificador;
    private String servicoNome;
    private String fornecedorNome;
    private String fornecedorCpfOuCnpj;
    private String valorExtenso;

    public DespesaReciboDTO() {
    }

    public DespesaReciboDTO(Despesa obj) {
        id = obj.getId();
        nome = obj.getNome();
        pagamento = obj.getDataPagamento();
        vencimento = obj.getVencimento();
        valor = obj.getValor();
        status = obj.getStatus();
        identificador = obj.getServico().getIdentificador();
        servicoNome = obj.getServico().getNome();
        fornecedorNome = obj.getFornecedor().getNome();
        fornecedorCpfOuCnpj = obj.getFornecedor().getCnpj();
        valorExtenso = new Extenso(obj.getValor()).toString();
    }

    public DespesaReciboDTO(Integer id, String nome, Date pagamento, Date vencimento,
                            Double valor, String status, String identificador, String servicoNome,
                            String fornecedorNome, String fornecedorCpfOuCnpj) {
        this.id = id;
        this.nome = nome;
        this.pagamento = pagamento;
        this.vencimento = vencimento;
        this.valor = valor;
        this.status = status;
        this.identificador = identificador;
        this.servicoNome = servicoNome;
        this.fornecedorNome = fornecedorNome;
        this.fornecedorCpfOuCnpj = fornecedorCpfOuCnpj;
        this.valorExtenso = new Extenso(valor).toString();
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

    public Date getPagamento() {
        return pagamento;
    }

    public void setPagamento(Date pagamento) {
        this.pagamento = pagamento;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
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

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getServicoNome() {
        return servicoNome;
    }

    public void setServicoNome(String servicoNome) {
        this.servicoNome = servicoNome;
    }

    public String getFornecedorNome() {
        return fornecedorNome;
    }

    public void setFornecedorNome(String fornecedorNome) {
        this.fornecedorNome = fornecedorNome;
    }

    public String getFornecedorCpfOuCnpj() {
        return fornecedorCpfOuCnpj;
    }

    public void setFornecedorCpfOuCnpj(String fornecedorCpfOuCnpj) {
        this.fornecedorCpfOuCnpj = fornecedorCpfOuCnpj;
    }

    public String getValorExtenso() {
        return valorExtenso;
    }

    public void setValorExtenso(String valorExtenso) {
        this.valorExtenso = valorExtenso;
    }
}

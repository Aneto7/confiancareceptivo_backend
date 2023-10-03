/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.confianca.dto;

import com.confianca.domain.Passageiro;

import java.io.Serializable;
import java.util.Date;

public class PassageiroConsultaDTO implements Serializable {
    private Integer id;
    private String nome;
    private String cpf;
    private Date nascimento;
    private String telefone;
    private String celular;
    private String email;
    private Integer idProduto;
    private String produto;
    private Date dataIn;
    private Integer idServico;
    private String identificadorServico;
    private Integer idVenda;
    private String venda;


    public PassageiroConsultaDTO() {
    }

    public PassageiroConsultaDTO(Integer id, String nome, String cpf,
                                 Date nascimento, String telefone, String celular,
                                 String email, Integer idProduto, String produto,
                                 Date dataIn, Integer idServico, String identificadorServico,
                                 Integer idVenda, String venda) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.nascimento = nascimento;
        this.telefone = telefone;
        this.celular = celular;
        this.email = email;
        this.idProduto = idProduto;
        this.produto = produto;
        this.dataIn = dataIn;
        this.idServico = idServico;
        this.identificadorServico = identificadorServico;
        this.idVenda = idVenda;
        this.venda = venda;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public Date getDataIn() {
        return dataIn;
    }

    public void setDataIn(Date dataIn) {
        this.dataIn = dataIn;
    }

    public Integer getIdServico() {
        return idServico;
    }

    public void setIdServico(Integer idServico) {
        this.idServico = idServico;
    }

    public String getIdentificadorServico() {
        return identificadorServico;
    }

    public void setIdentificadorServico(String identificadorServico) {
        this.identificadorServico = identificadorServico;
    }

    public Integer getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(Integer idVenda) {
        this.idVenda = idVenda;
    }

    public String getVenda() {
        return venda;
    }

    public void setVenda(String venda) {
        this.venda = venda;
    }
}

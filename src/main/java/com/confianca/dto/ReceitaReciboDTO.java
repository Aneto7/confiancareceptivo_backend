/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.confianca.dto;

import com.confianca.domain.Receita;
import com.confianca.services.Extenso;

import java.io.Serializable;
import java.util.Date;

public class ReceitaReciboDTO implements Serializable {
    private Integer id;
    private String nome;
    private Date recebimento;
    private Date vencimento;
    private Double valor;
    private String status;
    private String identificador;
    private String servicoNome;
    private String clienteNome;
    private String clienteCpfOuCnpj;
    private String valorExtenso;
    private String voucher = "";

    public ReceitaReciboDTO() {
    }

    public ReceitaReciboDTO(Receita obj) {
        id = obj.getId();
        nome = obj.getNome();
        recebimento = obj.getDataRecebimento();
        vencimento = obj.getVencimento();
        valor = obj.getValor();
        status = obj.getStatus();
        if (obj.getServico() != null) {
            identificador = obj.getServico().getIdentificador();
        } else {
            identificador = "";
        }
        if (obj.getServico() != null) {
            servicoNome = obj.getServico().getNome();
        } else {
            servicoNome = "";
        }
        if (obj.getCliente() != null) {
            clienteNome = obj.getCliente().getNome();
        } else {
            clienteNome = obj.getNome();
        }
        if (obj.getCliente() != null) {
            clienteCpfOuCnpj = obj.getCliente().getCnpjOuCpf();
        } else {
            clienteCpfOuCnpj = "";
        }
        valorExtenso = new Extenso(obj.getValor()).toString();
        if (obj.getProdutoVendido() != null) {
            voucher = "Voucher: " + obj.getProdutoVendido().getId();
        }

    }

    public ReceitaReciboDTO(Integer id, String nome, Date recebimento, Date vencimento,
                            Double valor, String status, String identificador, String servicoNome,
                            String clienteNome, String clienteCpfOuCnpj) {
        this.id = id;
        this.nome = nome;
        this.recebimento = recebimento;
        this.vencimento = vencimento;
        this.valor = valor;
        this.status = status;
        this.identificador = identificador;
        this.servicoNome = servicoNome;
        this.clienteNome = clienteNome;
        this.clienteCpfOuCnpj = clienteCpfOuCnpj;
        this.valorExtenso = new Extenso(valor).toString();
    }

    public ReceitaReciboDTO(Integer id, String nome, Date recebimento, Date vencimento,
                            Double valor, String status, String identificador, String servicoNome,
                            String clienteNome, String clienteCpfOuCnpj, String valorExtenso, String voucher) {
        this.id = id;
        this.nome = nome;
        this.recebimento = recebimento;
        this.vencimento = vencimento;
        this.valor = valor;
        this.status = status;
        this.identificador = identificador;
        this.servicoNome = servicoNome;
        this.clienteNome = clienteNome;
        this.clienteCpfOuCnpj = clienteCpfOuCnpj;
        this.valorExtenso = valorExtenso;
        this.voucher = voucher;
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

    public Date getRecebimento() {
        return recebimento;
    }

    public void setRecebimento(Date recebimento) {
        this.recebimento = recebimento;
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

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public String getClienteCpfOuCnpj() {
        return clienteCpfOuCnpj;
    }

    public void setClienteCpfOuCnpj(String clienteCpfOuCnpj) {
        this.clienteCpfOuCnpj = clienteCpfOuCnpj;
    }

    public String getValorExtenso() {
        return valorExtenso;
    }

    public void setValorExtenso(String valorExtenso) {
        this.valorExtenso = valorExtenso;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.confianca.dto;

import com.confianca.domain.Usuario;

import java.io.Serializable;
import java.util.List;

public class DadosClienteFechamentoDTO implements Serializable {
    private String clienteNome;
    private String clienteCpfOuCnpj;
    private String clienteEndereco;
    private String clienteBairro;
    private String clienteCep;
    private String cidadeNome;
    private String estadoSigla;
    private Double receita;
    private Double despesa;
    private Double saldo;
    private List<ExtratoDTO> extratoDTOS;
    private List<FechamentoDTO> fechamentoDTOS;

    public DadosClienteFechamentoDTO() {
    }

    public DadosClienteFechamentoDTO(Usuario obj) {
        clienteNome = obj.getCliente().getNome();
        clienteCpfOuCnpj = obj.getCliente().getCnpjOuCpf();
        clienteEndereco = obj.getCliente().getEndereco();
        clienteBairro = obj.getCliente().getBairro();
        clienteCep = obj.getCliente().getCep();
        cidadeNome = obj.getCliente().getCidade().getNome();
        estadoSigla = obj.getCliente().getCidade().getEstado().getSigla();
    }

    public DadosClienteFechamentoDTO(String clienteNome, String clienteCpfOuCnpj, String clienteEndereco,
                                     String clienteBairro, String clienteCep, String cidadeNome, String estadoSigla,
                                     Double receita, Double despesa, Double saldo) {
        this.clienteNome = clienteNome;
        this.clienteCpfOuCnpj = clienteCpfOuCnpj;
        this.clienteEndereco = clienteEndereco;
        this.clienteBairro = clienteBairro;
        this.clienteCep = clienteCep;
        this.cidadeNome = cidadeNome;
        this.estadoSigla = estadoSigla;
        this.receita = receita;
        this.despesa = despesa;
        this.saldo = saldo;
    }

    public DadosClienteFechamentoDTO(String clienteNome, String clienteCpfOuCnpj, String clienteEndereco,
                                     String clienteBairro, String clienteCep, String cidadeNome, String estadoSigla,
                                     Double receita, Double despesa, Double saldo, List<ExtratoDTO> extratoDTOS, List<FechamentoDTO> fechamentoDTOS) {
        this.clienteNome = clienteNome;
        this.clienteCpfOuCnpj = clienteCpfOuCnpj;
        this.clienteEndereco = clienteEndereco;
        this.clienteBairro = clienteBairro;
        this.clienteCep = clienteCep;
        this.cidadeNome = cidadeNome;
        this.estadoSigla = estadoSigla;
        this.receita = receita;
        this.despesa = despesa;
        this.saldo = saldo;
        this.extratoDTOS = extratoDTOS;
        this.fechamentoDTOS = fechamentoDTOS;
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

    public String getClienteEndereco() {
        return clienteEndereco;
    }

    public void setClienteEndereco(String clienteEndereco) {
        this.clienteEndereco = clienteEndereco;
    }

    public String getClienteBairro() {
        return clienteBairro;
    }

    public void setClienteBairro(String clienteBairro) {
        this.clienteBairro = clienteBairro;
    }

    public String getClienteCep() {
        return clienteCep;
    }

    public void setClienteCep(String clienteCep) {
        this.clienteCep = clienteCep;
    }

    public String getCidadeNome() {
        return cidadeNome;
    }

    public void setCidadeNome(String cidadeNome) {
        this.cidadeNome = cidadeNome;
    }

    public String getEstadoSigla() {
        return estadoSigla;
    }

    public void setEstadoSigla(String estadoSigla) {
        this.estadoSigla = estadoSigla;
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

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public List<ExtratoDTO> getExtratoDTOS() {
        return extratoDTOS;
    }

    public void setExtratoDTOS(List<ExtratoDTO> extratoDTOS) {
        this.extratoDTOS = extratoDTOS;
    }

    public List<FechamentoDTO> getFechamentoDTOS() {
        return fechamentoDTOS;
    }

    public void setFechamentoDTOS(List<FechamentoDTO> fechamentoDTOS) {
        this.fechamentoDTOS = fechamentoDTOS;
    }
}

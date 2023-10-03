/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.confianca.dto;

import com.confianca.domain.ProdutoVendido;
import com.confianca.domain.Venda;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ComprovanteVendaDTO implements Serializable {
    private Integer id;
    private String nome;
    private Date emissao;
    private String clienteNome;
    private String clienteCpfOuCnpj;
    private String clienteEndereco;
    private String clienteBairro;
    private String clienteCep;
    private String cidadeNome;
    private String estadoSigla;
    private Double valor;
    private Double desconto;
    private Double valorFinal;
    private List<ProdutoVendidoVendaDTO> produtosVendidosDTO;
    private List<OpcionalVendidoDTO> opcionaisDTO;

    public ComprovanteVendaDTO() {
    }

    public ComprovanteVendaDTO(Venda obj) {
        id = obj.getId();
        nome = obj.getNome();
        clienteNome = obj.getCliente().getNome();
        clienteCpfOuCnpj = obj.getCliente().getCnpjOuCpf();
        clienteEndereco = obj.getCliente().getEndereco();
        clienteBairro = obj.getCliente().getBairro();
        clienteCep = obj.getCliente().getCep();
        cidadeNome = obj.getCliente().getCidade().getNome();
        estadoSigla = obj.getCliente().getCidade().getEstado().getSigla();
        valor = obj.getValor();
        desconto = obj.getDesconto();
        valorFinal = obj.getValorFinal();
    }

    public ComprovanteVendaDTO(Integer id, String nome, Date emissao,
                               String clienteNome, String clienteCpfOuCnpj,
                               String clienteEndereco, String clienteBairro,
                               String clienteCep, String cidadeNome, String estadoSigla,
                               Double valor, Double desconto, Double valorFinal) {
        this.id = id;
        this.nome = nome;
        this.emissao = emissao;
        this.clienteNome = clienteNome;
        this.clienteCpfOuCnpj = clienteCpfOuCnpj;
        this.clienteEndereco = clienteEndereco;
        this.clienteBairro = clienteBairro;
        this.clienteCep = clienteCep;
        this.cidadeNome = cidadeNome;
        this.estadoSigla = estadoSigla;
        this.valor = valor;
        this.desconto = desconto;
        this.valorFinal = valorFinal;
    }

    public ComprovanteVendaDTO(Integer id, String nome, Date emissao,
                               String clienteNome, String clienteCpfOuCnpj,
                               String clienteEndereco, String clienteBairro,
                               String clienteCep, String cidadeNome, String estadoSigla,
                               Double valor, Double desconto, Double valorFinal,
                               List<ProdutoVendidoVendaDTO> produtosVendidosDTO, List<OpcionalVendidoDTO> opcionaisDTO) {
        this.id = id;
        this.nome = nome;
        this.emissao = emissao;
        this.clienteNome = clienteNome;
        this.clienteCpfOuCnpj = clienteCpfOuCnpj;
        this.clienteEndereco = clienteEndereco;
        this.clienteBairro = clienteBairro;
        this.clienteCep = clienteCep;
        this.cidadeNome = cidadeNome;
        this.estadoSigla = estadoSigla;
        this.valor = valor;
        this.desconto = desconto;
        this.valorFinal = valorFinal;
        this.produtosVendidosDTO = produtosVendidosDTO;
        this.opcionaisDTO = opcionaisDTO;
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

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Double getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(Double valorFinal) {
        this.valorFinal = valorFinal;
    }

    public List<ProdutoVendidoVendaDTO> getProdutosVendidosDTO() {
        return produtosVendidosDTO;
    }

    public void setProdutosVendidosDTO(List<ProdutoVendidoVendaDTO> produtosVendidosDTO) {
        this.produtosVendidosDTO = produtosVendidosDTO;
    }

    public List<OpcionalVendidoDTO> getOpcionaisDTO() {
        return opcionaisDTO;
    }

    public void setOpcionaisDTO(List<OpcionalVendidoDTO> opcionaisDTO) {
        this.opcionaisDTO = opcionaisDTO;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.confianca.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Transfer", catalog = "receptivo", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"idTransfer"})})
public class Transfer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTransfer", nullable = false)
    private Integer id;

    @JoinColumn(name = "Fornecedor_idFornecedor")
    @ManyToOne
    private Fornecedor fornecedor;

    @JoinColumn(name = "PontoDeSaida_idPontoDeSaida")
    @ManyToOne
    private PontoDeSaida pontoDeSaida;

    @JoinColumn(name = "Servico_idServico")
    @ManyToOne
    private Servico servico;

    @NotNull
    @Column(name = "TransferNome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "TransferDiaHora", nullable = false)
    private Date diahora;

    @Size(max = 255)
    @Column(name = "TransferVeiculo", length = 255)
    private String veiculo;

    @Size(max = 255)
    @Column(name = "TransferCondutor", length = 255)
    private String condutor;

    @Size(max = 255)
    @Column(name = "TransferRota", length = 255)
    private String rota;

    @Size(max = 255)
    @Column(name = "TransferDestino", length = 255)
    private String destino;

    @NotNull
    @Size(max = 255)
    @Column(name = "TransferPAX", nullable = false, length = 255)
    private String pax;

    @NotNull
    @Size(max = 255)
    @Column(name = "TransferCliente", nullable = false, length = 255)
    private String cliente;

    @Column(name = "TransferMalai")
    private Boolean malai;

    @NotNull
    @Size(max = 255)
    @Column(name = "TransferStatus", nullable = false, length = 255)
    private String status;

    @Size(max = 255)
    @Column(name = "TransferTelefone", length = 255)
    private String telefone;

    @Column(name = "TransferEmissao")
    private Date emissao;

    public Transfer() {
    }

    public Transfer(Integer id, Fornecedor fornecedor, PontoDeSaida pontoDeSaida, Servico servico,
                    String nome, Date diahora, String veiculo, String condutor, String rota, String destino,
                    String pax, String cliente, Boolean malai, String status, String telefone, Date emissao) {
        this.id = id;
        this.fornecedor = fornecedor;
        this.pontoDeSaida = pontoDeSaida;
        this.servico = servico;
        this.nome = nome;
        this.diahora = diahora;
        this.veiculo = veiculo;
        this.condutor = condutor;
        this.rota = rota;
        this.destino = destino;
        this.pax = pax;
        this.cliente = cliente;
        this.malai = malai;
        this.status = status;
        this.telefone = telefone;
        this.emissao = emissao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public PontoDeSaida getPontoDeSaida() {
        return pontoDeSaida;
    }

    public void setPontoDeSaida(PontoDeSaida pontoDeSaida) {
        this.pontoDeSaida = pontoDeSaida;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDiahora() {
        return diahora;
    }

    public void setDiahora(Date diahora) {
        this.diahora = diahora;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getCondutor() {
        return condutor;
    }

    public void setCondutor(String condutor) {
        this.condutor = condutor;
    }

    public String getRota() {
        return rota;
    }

    public void setRota(String rota) {
        this.rota = rota;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getPax() {
        return pax;
    }

    public void setPax(String pax) {
        this.pax = pax;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Boolean getMalai() {
        return malai;
    }

    public void setMalai(Boolean malai) {
        this.malai = malai;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transfer)) return false;

        Transfer despesa = (Transfer) o;

        return getId() != null ? getId().equals(despesa.getId()) : despesa.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.confianca.dto;

import com.confianca.domain.Despesa;
import com.confianca.domain.Passageiro;
import com.confianca.domain.ProdutoVendido;
import com.confianca.domain.Servico;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ServicoDTO implements Serializable {
    private Integer id;
    private String nome;
    private Date data;
    private Date dataIn;
    private Date dataOut;
    private String identificador;
    private String ordemServico;
    private String clienteNome;
    private String obs;
    private String produto;
    private List<PassageiroDTO> passageirosDTO;

    public ServicoDTO() {
    }

    public ServicoDTO(Servico obj) {
        id = obj.getId();
        nome = obj.getNome();
        data = obj.getData();
        dataIn = obj.getDataIn();
        dataOut = obj.getDataOut();
        identificador = obj.getIdentificador();
        ordemServico = obj.getOrdemServico();
        clienteNome = obj.getNomeCliente();
        for (ProdutoVendido prodV:obj.getProdutosVendidos()) {
            for (Passageiro passageiro:prodV.getPassageiros()) {
                PassageiroDTO passDTO = new PassageiroDTO(passageiro);
                passageirosDTO.add(passDTO);
            }
        }
        obs = obj.getProduto().getObs();
        produto = obj.getProduto().getNome();
    }

    public ServicoDTO(Integer id, String nome, Date data, Date dataIn, Date dataOut, String identificador, String ordemServico, String clienteNome) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.dataIn = dataIn;
        this.dataOut = dataOut;
        this.identificador = identificador;
        this.ordemServico = ordemServico;
        this.clienteNome = clienteNome;
    }

    public ServicoDTO(Integer id, String nome, Date data, Date dataIn, Date dataOut, String identificador, String ordemServico, String clienteNome, List<PassageiroDTO> passageirosDTO) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.dataIn = dataIn;
        this.dataOut = dataOut;
        this.identificador = identificador;
        this.ordemServico = ordemServico;
        this.clienteNome = clienteNome;
        this.passageirosDTO = passageirosDTO;
    }

    public ServicoDTO(Integer id, String nome, Date data, Date dataIn, Date dataOut, String identificador, String ordemServico, String clienteNome, String obs, String produto) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.dataIn = dataIn;
        this.dataOut = dataOut;
        this.identificador = identificador;
        this.ordemServico = ordemServico;
        this.clienteNome = clienteNome;
        this.obs = obs;
        this.produto = produto;
    }

    public ServicoDTO(Integer id, String nome, Date data, Date dataIn, Date dataOut, String identificador, String ordemServico,
                      String clienteNome, String obs, String produto, List<PassageiroDTO> passageirosDTO) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.dataIn = dataIn;
        this.dataOut = dataOut;
        this.identificador = identificador;
        this.ordemServico = ordemServico;
        this.clienteNome = clienteNome;
        this.obs = obs;
        this.produto = produto;
        this.passageirosDTO = passageirosDTO;
    }

    public List<PassageiroDTO> getPassageirosDTO() {
        return passageirosDTO;
    }

    public void setPassageirosDTO(List<PassageiroDTO> passageirosDTO) {
        this.passageirosDTO = passageirosDTO;
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getDataIn() {
        return dataIn;
    }

    public void setDataIn(Date dataIn) {
        this.dataIn = dataIn;
    }

    public Date getDataOut() {
        return dataOut;
    }

    public void setDataOut(Date dataOut) {
        this.dataOut = dataOut;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(String ordemServico) {
        this.ordemServico = ordemServico;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }
}

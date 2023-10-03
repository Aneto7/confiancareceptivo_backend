/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.confianca.dto;

import com.confianca.domain.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

public class ProdutoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Cidade cidade;
    private PontoDeSaida pontoDeSaida;
    private Cupom cupom;
    private TipoProduto tipoProduto;
    private String nome;
    private String anotacao;
    private String horarioSaida;
    private String horarioRetorno;
    private String localSaida;
    private Double valor;
    private Integer capacidade;
    private String periodicidade;
    private String status;
    private String obs;
    private Boolean individual;
    private Boolean produtoAlterarValor;
    private List<String> tags;
    private List<DetalheProduto> detalhesProdutos;
    private List<Imagem> imagens;
    private List<Opcional> opcionais;
    private List<Tarifario> tarifarios;
    private List<TrechoProduto> trechoProdutos;
    private List<Cliente> clientes;
    private List<Servico> servicos;

    public ProdutoDTO() {
    }

    public ProdutoDTO(Produto obj) {
        this.id = obj.getId();
        this.cidade = obj.getCidade();
        this.pontoDeSaida = obj.getPontoDeSaida();
        this.cupom = obj.getCupom();
        this.tipoProduto = obj.getTipoProduto();
        this.nome = obj.getNome();
        this.anotacao = obj.getAnotacao();
        this.horarioSaida = obj.getHorarioSaida();
        this.horarioRetorno = obj.getHorarioRetorno();
        this.localSaida = obj.getLocalSaida();
        this.valor = obj.getValor();
        this.capacidade = obj.getCapacidade();
        this.periodicidade = obj.getPeriodicidade();
        this.status = obj.getStatus();
        this.obs = obj.getObs();
        this.individual = obj.getIndividual();
        this.produtoAlterarValor = obj.getProdutoAlterarValor();
        this.tags = obj.getTags();
        this.detalhesProdutos = obj.getDetalhesProdutos();
        this.imagens = obj.getImagens();
        this.opcionais = obj.getOpcionais();
        this.tarifarios = obj.getTarifarios();
        this.trechoProdutos = obj.getTrechoProdutos();
        this.clientes = obj.getClientes();
        this.servicos = obj.getServicos();
    }

    public ProdutoDTO(Integer id, Cidade cidade, PontoDeSaida pontoDeSaida, Cupom cupom,
                      TipoProduto tipoProduto, String nome, String anotacao, String horarioSaida,
                      String horarioRetorno, String localSaida, Double valor, Integer capacidade,
                      String periodicidade, String status, String obs, Boolean individual,
                      Boolean produtoAlterarValor, List<String> tags,
                      List<DetalheProduto> detalhesProdutos, List<Imagem> imagens,
                      List<Opcional> opcionais, List<Tarifario> tarifarios, List<TrechoProduto> trechoProdutos,
                      List<Cliente> clientes, List<Servico> servicos) {
        this.id = id;
        this.cidade = cidade;
        this.pontoDeSaida = pontoDeSaida;
        this.cupom = cupom;
        this.tipoProduto = tipoProduto;
        this.nome = nome;
        this.anotacao = anotacao;
        this.horarioSaida = horarioSaida;
        this.horarioRetorno = horarioRetorno;
        this.localSaida = localSaida;
        this.valor = valor;
        this.capacidade = capacidade;
        this.periodicidade = periodicidade;
        this.status = status;
        this.obs = obs;
        this.individual = individual;
        this.produtoAlterarValor = produtoAlterarValor;
        this.tags = tags;
        this.detalhesProdutos = detalhesProdutos;
        this.imagens = imagens;
        this.opcionais = opcionais;
        this.tarifarios = tarifarios;
        this.trechoProdutos = trechoProdutos;
        this.clientes = clientes;
        this.servicos = servicos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public PontoDeSaida getPontoDeSaida() {
        return pontoDeSaida;
    }

    public void setPontoDeSaida(PontoDeSaida pontoDeSaida) {
        this.pontoDeSaida = pontoDeSaida;
    }

    public Cupom getCupom() {
        return cupom;
    }

    public void setCupom(Cupom cupom) {
        this.cupom = cupom;
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(TipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAnotacao() {
        return anotacao;
    }

    public void setAnotacao(String anotacao) {
        this.anotacao = anotacao;
    }

    public String getHorarioSaida() {
        return horarioSaida;
    }

    public void setHorarioSaida(String horarioSaida) {
        this.horarioSaida = horarioSaida;
    }

    public String getHorarioRetorno() {
        return horarioRetorno;
    }

    public void setHorarioRetorno(String horarioRetorno) {
        this.horarioRetorno = horarioRetorno;
    }

    public String getLocalSaida() {
        return localSaida;
    }

    public void setLocalSaida(String localSaida) {
        this.localSaida = localSaida;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public String getPeriodicidade() {
        return periodicidade;
    }

    public void setPeriodicidade(String periodicidade) {
        this.periodicidade = periodicidade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Boolean getIndividual() {
        return individual;
    }

    public void setIndividual(Boolean individual) {
        this.individual = individual;
    }

    public Boolean getProdutoAlterarValor() {
        return produtoAlterarValor;
    }

    public void setProdutoAlterarValor(Boolean produtoAlterarValor) {
        this.produtoAlterarValor = produtoAlterarValor;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<DetalheProduto> getDetalhesProdutos() {
        return detalhesProdutos;
    }

    public void setDetalhesProdutos(List<DetalheProduto> detalhesProdutos) {
        this.detalhesProdutos = detalhesProdutos;
    }

    public List<Imagem> getImagens() {
        return imagens;
    }

    public void setImagens(List<Imagem> imagens) {
        this.imagens = imagens;
    }

    public List<Opcional> getOpcionais() {
        return opcionais;
    }

    public void setOpcionais(List<Opcional> opcionais) {
        this.opcionais = opcionais;
    }

    public List<Tarifario> getTarifarios() {
        return tarifarios;
    }

    public void setTarifarios(List<Tarifario> tarifarios) {
        this.tarifarios = tarifarios;
    }

    public List<TrechoProduto> getTrechoProdutos() {
        return trechoProdutos;
    }

    public void setTrechoProdutos(List<TrechoProduto> trechoProdutos) {
        this.trechoProdutos = trechoProdutos;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProdutoDTO)) return false;

        ProdutoDTO tarifa = (ProdutoDTO) o;

        return getId() != null ? getId().equals(tarifa.getId()) : tarifa.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

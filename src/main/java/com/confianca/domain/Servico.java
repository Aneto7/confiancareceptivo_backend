/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.confianca.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Servico", catalog = "receptivo", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"idServico"})})
public class Servico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idServico", nullable = false)
    private Integer id;

    @JoinColumn(name = "Guia_idGuia")
    @ManyToOne
    private Guia guia;

    @JoinColumn(name = "TipoProduto_idTipoProduto")
    @ManyToOne
    private TipoProduto tipoProduto;

    @JoinColumn(name = "Cliente_idCliente")
    @ManyToOne
    private Cliente cliente;

    @Size(max = 255)
    @Column(name = "ServicoNomeCliente", length = 255)
    private String nomeCliente;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ServicoNome", nullable = false, length = 255)
    private String nome;

    @NotNull
    @Column(name = "ServicoData", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date data;

    @Column(name = "ServicoDataIn")
    private Date dataIn;

    @Column(name = "ServicoDataOut")
    private Date dataOut;

    @Size(max = 255)
    @Column(name = "ServicoIdentificador", length = 255)
    private String identificador;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ServicoStatus", nullable = false, length = 255)
    private String status;

    @Size(max = 5000)
    @Column(name = "ServicoOrdemServico", length = 5000)
    private String ordemServico;

    @JsonIgnore
    @OneToMany(mappedBy="servico")
    private List<Despesa> despesas;

    @JsonIgnore
    @OneToMany(mappedBy="servico")
    private List<Receita> receitas;

    @JsonIgnore
    @OneToMany(mappedBy="servico")
    private List<ProdutoVendido> produtosVendidos;

    @JsonIgnore
    @JoinColumn(name = "Produto_idProduto")
    @ManyToOne
    private Produto produto;

    @Column(name = "ServicoEmissao")
    private Date emissao;

    public Servico() {
    }

    public Servico(Integer id, Guia guia, TipoProduto tipoProduto, Cliente cliente, String nomeCliente,
                   String nome, Date data, Date dataIn, Date dataOut, String identificador, String status,
                   String ordemServico, Produto produto, Date emissao) {
        this.id = id;
        this.guia = guia;
        this.tipoProduto = tipoProduto;
        this.cliente = cliente;
        this.nomeCliente = nomeCliente;
        this.nome = nome;
        this.data = data;
        this.dataIn = dataIn;
        this.dataOut = dataOut;
        this.identificador = identificador;
        this.status = status;
        this.ordemServico = ordemServico;
        this.produto = produto;
        this.emissao = emissao;
    }

    public Servico(Integer id, Guia guia, TipoProduto tipoProduto, Cliente cliente, String nomeCliente,
                   String nome, Date data, Date dataIn, Date dataOut, String identificador, String status,
                   String ordemServico, List<Despesa> despesas, List<Receita> receitas,
                   List<ProdutoVendido> produtosVendidos, Produto produto, Date emissao) {
        this.id = id;
        this.guia = guia;
        this.tipoProduto = tipoProduto;
        this.cliente = cliente;
        this.nomeCliente = nomeCliente;
        this.nome = nome;
        this.data = data;
        this.dataIn = dataIn;
        this.dataOut = dataOut;
        this.identificador = identificador;
        this.status = status;
        this.ordemServico = ordemServico;
        this.despesas = despesas;
        this.receitas = receitas;
        this.produtosVendidos = produtosVendidos;
        this.produto = produto;
        this.emissao = emissao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Guia getGuia() {
        return guia;
    }

    public void setGuia(Guia guia) {
        this.guia = guia;
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(TipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(String ordemServico) {
        this.ordemServico = ordemServico;
    }

    public List<Despesa> getDespesas() {
        return despesas;
    }

    public void setDespesas(List<Despesa> despesas) {
        this.despesas = despesas;
    }

    public List<Receita> getReceitas() {
        return receitas;
    }

    public void setReceitas(List<Receita> receitas) {
        this.receitas = receitas;
    }

    public List<ProdutoVendido> getProdutosVendidos() {
        return produtosVendidos;
    }

    public void setProdutosVendidos(List<ProdutoVendido> produtosVendidos) {
        this.produtosVendidos = produtosVendidos;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
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
        if (!(o instanceof Servico)) return false;

        Servico that = (Servico) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

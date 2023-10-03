package com.confianca.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "Produto", catalog = "receptivo", schema = "", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"idProduto"})})
public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "idProduto", nullable = false)
    private Integer id;

    @Basic(optional = false)
    @JoinColumn(name = "Fornecedor_idFornecedor")
    @ManyToOne
    private Fornecedor fornecedor;

    @JoinColumn(name = "Cidade_idCidade")
    @ManyToOne
    private Cidade cidade;

    @JoinColumn(name = "PontoDeSaida_idPontoDeSaida")
    @ManyToOne
    private PontoDeSaida pontoDeSaida;

    @JoinColumn(name = "Cupom_idCupom")
    @ManyToOne
    private Cupom cupom;

    @NotNull
    @JoinColumn(name = "TipoProduto_idTipoProduto", nullable = false)
    @ManyToOne
    private TipoProduto tipoProduto;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ProdutoNome", nullable = false, length = 255, unique = true)
    private String nome;

    @Size(max = 255)
    @Column(name = "ProdutoAnotacao", length = 255)
    private String anotacao;

    @Size(max = 255)
    @Column(name = "ProdutoHorarioSaida", length = 255)
    private String horarioSaida;

    @Size(max = 255)
    @Column(name = "ProdutoHorarioRetorno", length = 255)
    private String horarioRetorno;

    @Size(max = 255)
    @Column(name = "ProdutoLocalSaida", length = 255)
    private String localSaida;

    @Column(name = "ProdutoValor")
    private Double valor;

    @Column(name = "ProdutoCapacidade")
    private Integer capacidade;

    @Size(max = 255)
    @Column(name = "ProdutoPeriodicidade", length = 255)
    private String periodicidade;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ProdutoStatus", nullable = false, length = 255)
    private String status;

    @Size(max = 3000)
    @Column(name = "ProdutoObservacao", length = 3000)
    private String obs;

    @Column(name = "ProdutoIndividual")
    private Boolean individual;

    @Column(name = "ProdutoAlterarValor")
    private Boolean produtoAlterarValor;

    @Size(max = 3000)
    @Column(name = "ProdutoTags")
    @ElementCollection(targetClass = String.class)
    private List<String> tags;

    @OneToMany(mappedBy="produto")
    private List<DetalheProduto> detalhesProdutos;

    @OneToMany(mappedBy="produto")
    private List<Imagem> imagens;

    @OneToMany(mappedBy="produto")
    private List<Tarifa> tarifas;

    @JsonIgnore
    @OneToMany(mappedBy="produto")
    private List<ProdutoVendido> produtosVendidos;

    @ManyToMany
    private List<Opcional> opcionais;

    @OneToMany(mappedBy="produto")
    private List<Tarifario> tarifarios;

    @OneToMany(mappedBy="produto")
    private List<TrechoProduto> trechoProdutos;

    @ManyToMany
    private List<Cliente> clientes;

    @JsonIgnore
    @OneToMany(mappedBy="produto")
    private List<Servico> servicos;

    public Produto() {
    }

    public Produto(Integer id, Fornecedor fornecedor, Cidade cidade, PontoDeSaida pontoDeSaida,
                   Cupom cupom, TipoProduto tipoProduto, String nome, String anotacao,
                   String horarioSaida, String horarioRetorno, String localSaida, Double valor, Integer capacidade,
                   String periodicidade, String status, String obs, Boolean individual,
                   Boolean produtoAlterarValor, List<String> tags) {
        this.id = id;
        this.fornecedor = fornecedor;
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
    }

    public Produto(Integer id, Fornecedor fornecedor, Cidade cidade,
                   PontoDeSaida pontoDeSaida, Cupom cupom, TipoProduto tipoProduto,
                   String nome, String anotacao, String horarioSaida, String horarioRetorno, String localSaida,
                   Double valor, Integer capacidade, String periodicidade, String status,
                   String obs, Boolean individual, Boolean produtoAlterarValor,
                   List<String> tags, List<Opcional> opcionais, List<Cliente> clientes) {
        this.id = id;
        this.fornecedor = fornecedor;
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
        this.opcionais = opcionais;
        this.clientes = clientes;
    }

    public Produto(Integer id, Fornecedor fornecedor, Cidade cidade, PontoDeSaida pontoDeSaida,
                   Cupom cupom, TipoProduto tipoProduto, String nome, String anotacao,
                   String horarioSaida, String horarioRetorno, String localSaida, Double valor,
                   Integer capacidade, String periodicidade, String status, String obs,
                   Boolean individual, Boolean produtoAlterarValor,
                   List<String> tags, List<DetalheProduto> detalhesProdutos, List<Imagem> imagens,
                   List<Tarifa> tarifas, List<ProdutoVendido> produtosVendidos, List<Opcional> opcionais,
                   List<Tarifario> tarifarios, List<TrechoProduto> trechoProdutos, List<Cliente> clientes, List<Servico> servicos) {
        this.id = id;
        this.fornecedor = fornecedor;
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
        this.tarifas = tarifas;
        this.produtosVendidos = produtosVendidos;
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

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
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

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
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

    public List<Tarifa> getTarifas() {
        return tarifas;
    }

    public void setTarifas(List<Tarifa> tarifas) {
        this.tarifas = tarifas;
    }

    public List<ProdutoVendido> getProdutosVendidos() {
        return produtosVendidos;
    }

    public void setProdutosVendidos(List<ProdutoVendido> produtosVendidos) {
        this.produtosVendidos = produtosVendidos;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto)) return false;

        Produto produto = (Produto) o;

        return getId() != null ? getId().equals(produto.getId()) : produto.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

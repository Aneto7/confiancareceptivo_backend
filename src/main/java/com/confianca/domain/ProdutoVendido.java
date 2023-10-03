package com.confianca.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ProdutoVendido", catalog = "receptivo", schema = "", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"idProdutoVendido"})})
public class ProdutoVendido implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "idProdutoVendido", nullable = false)
    private Integer id;

    @Basic(optional = false)
    @JoinColumn(name = "Produto_idProduto")
    @OneToOne
    private Produto produto;

    @JoinColumn(name = "Tarifa_idTarifa")
    @ManyToOne
    private Tarifa tarifa;

    @JoinColumn(name = "Servico_idServico")
    @ManyToOne
    private Servico servico;

    @JsonIgnore
    @JoinColumn(name = "Venda_idVenda")
    @ManyToOne
    private Venda venda;

    @JoinColumn(name = "TrechoProduto_idTrechoProduto")
    @ManyToOne
    private TrechoProduto trechoProduto;

    @Size(min = 1, max = 255)
    @Column(name = "ProdutoNome", nullable = false, length = 255)
    private String nome;

    @NotNull
    @Column(name = "ProdutoValor", nullable = false)
    private Double valor;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ProdutoStatus", nullable = false, length = 255)
    private String status;

    @Column(name = "DataIda")
    private Date dataIda;

    @Column(name = "DataVolta")
    private Date dataVolta;

    @Column(name = "ProdutoIdaEVolta")
    private Boolean idaEVolta;

    @OneToMany(mappedBy="produtoVendido")
    private List<Passageiro> passageiros;

    @OneToMany(mappedBy="produtoVendido")
    private List<OpcionalVendido> opcionaisVendidos;

    @JsonIgnore
    @OneToMany(mappedBy="produtoVendido")
    private List<Receita> receitas;



    public ProdutoVendido() {
    }

    public ProdutoVendido(Integer id, Produto produto, Tarifa tarifa, Servico servico, Venda venda,
                          TrechoProduto trechoProduto, String nome, Double valor, String status,
                          Date dataIda, Date dataVolta) {
        this.id = id;
        this.produto = produto;
        this.tarifa = tarifa;
        this.servico = servico;
        this.venda = venda;
        this.trechoProduto = trechoProduto;
        this.nome = nome;
        this.valor = valor;
        this.status = status;
        this.dataIda = dataIda;
        this.dataVolta = dataVolta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Tarifa getTarifa() {
        return tarifa;
    }

    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public TrechoProduto getTrechoProduto() {
        return trechoProduto;
    }

    public void setTrechoProduto(TrechoProduto trechoProduto) {
        this.trechoProduto = trechoProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public Date getDataIda() {
        return dataIda;
    }

    public void setDataIda(Date dataIda) {
        this.dataIda = dataIda;
    }

    public Date getDataVolta() {
        return dataVolta;
    }

    public void setDataVolta(Date dataVolta) {
        this.dataVolta = dataVolta;
    }

    public Boolean getIdaEVolta() {
        return idaEVolta;
    }

    public void setIdaEVolta(Boolean idaEVolta) {
        this.idaEVolta = idaEVolta;
    }

    public List<Passageiro> getPassageiros() {
        return passageiros;
    }

    public void setPassageiros(List<Passageiro> passageiros) {
        this.passageiros = passageiros;
    }

    public List<OpcionalVendido> getOpcionaisVendidos() {
        return opcionaisVendidos;
    }

    public void setOpcionaisVendidos(List<OpcionalVendido> opcionaisVendidos) {
        this.opcionaisVendidos = opcionaisVendidos;
    }

    public List<Receita> getReceitas() {
        return receitas;
    }

    public void setReceitas(List<Receita> receitas) {
        this.receitas = receitas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProdutoVendido)) return false;

        ProdutoVendido produto = (ProdutoVendido) o;

        return getId() != null ? getId().equals(produto.getId()) : produto.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

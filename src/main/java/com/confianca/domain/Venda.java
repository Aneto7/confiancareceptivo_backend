package com.confianca.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Venda", catalog = "receptivo", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"idVenda"})})
public class Venda implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idVenda", nullable = false)
    private Integer id;

    @NotNull
    @JoinColumn(name = "Cliente_idCliente", nullable = false)
    @ManyToOne
    private Cliente cliente;

    @JoinColumn(name = "Usuario_idUsuario")
    @ManyToOne
    private Usuario usuario;

    @JoinColumn(name = "Cartao_idCartao")
    @ManyToOne
    private Cartao cartao;

    @JoinColumn(name = "Pagador_idPagador")
    @OneToOne
    private Pagador pagador;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "VendaNome", nullable = false, length = 255)
    private String nome;

    @NotNull
    @Column(name = "VendaValor", nullable = false)
    private Double valor;

    @Column(name = "VendaDesconto")
    private Double desconto;

    @Column(name = "VendaValorFinal")
    private Double valorFinal;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "VendaStatus", nullable = false, length = 255)
    private String status;

    @Size(max = 255)
    @Column(name = "VendaPagamento", length = 255)
    private String pagamento;

    @Column(name = "VendaDataPagamento")
    @Temporal(TemporalType.DATE)
    private Date dataPagamento;

    @Column(name = "VendaDataVencimento")
    @Temporal(TemporalType.DATE)
    private Date dataVencimento;

    @Size(max = 255)
    @Column(name = "VendaComprovante", length = 255)
    private String comprovante;

    @Size(max = 1000)
    @Column(name = "VendaObservacao", length = 3000)
    private String obs;

    @JsonIgnore
    @OneToMany(mappedBy="venda")
    private List<Recebimento> recebimentos;

    @JsonIgnore
    @OneToMany(mappedBy="venda")
    private List<Receita> receitas;

    @OneToMany(mappedBy="venda")
    private List<ProdutoVendido> produtosVendidos;

    @Column(name = "VendaEmissao")
    private Date emissao;

    public Venda() {
    }

    public Venda(Integer id, Cliente cliente, Usuario usuario, Cartao cartao, Pagador pagador,
                 String nome, Double valor, Double desconto, Double valorFinal, String status,
                 String pagamento, Date dataPagamento, Date dataVencimento, String comprovante,
                 String obs, Date emissao) {
        this.id = id;
        this.cliente = cliente;
        this.usuario = usuario;
        this.cartao = cartao;
        this.pagador = pagador;
        this.nome = nome;
        this.valor = valor;
        this.desconto = desconto;
        this.valorFinal = valorFinal;
        this.status = status;
        this.pagamento = pagamento;
        this.dataPagamento = dataPagamento;
        this.dataVencimento = dataVencimento;
        this.comprovante = comprovante;
        this.obs = obs;
        this.emissao = emissao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public Pagador getPagador() {
        return pagador;
    }

    public void setPagador(Pagador pagador) {
        this.pagador = pagador;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getComprovante() {
        return comprovante;
    }

    public void setComprovante(String comprovante) {
        this.comprovante = comprovante;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public List<Recebimento> getRecebimentos() {
        return recebimentos;
    }

    public void setRecebimentos(List<Recebimento> recebimentos) {
        this.recebimentos = recebimentos;
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

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Venda)) return false;

        Venda venda = (Venda) o;

        return getId() != null ? getId().equals(venda.getId()) : venda.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Venda: ");
        sb.append(id);
        sb.append("\ncliente=\n").append(cliente);
        sb.append(", usuario=").append(usuario);
        sb.append(", nome='").append(nome).append('\'');
        sb.append(", valor=").append(valor).append('\'');;
        sb.append(", desconto=").append(desconto).append('\'');;
        sb.append(", valorFinal=").append(valorFinal).append('\'');;
        sb.append(", status='").append(status).append('\'');
        sb.append(", pagamento='").append(pagamento).append('\'');
        sb.append(", dataPagamento=").append(dataPagamento).append('\'');;
        sb.append(", obs='").append(obs).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

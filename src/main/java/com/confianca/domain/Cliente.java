package com.confianca.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "Cliente", catalog = "receptivo")
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @JoinColumn(name = "idCliente", nullable = false)
    private Integer id;

    @JoinColumn(name = "idTipoCliente")
    @ManyToOne
    private TipoCliente tipoCliente;

    @JoinColumn(name = "idCidade")
    @ManyToOne
    private Cidade cidade;

    @JoinColumn(name = "idUnidade")
    @ManyToOne
    private Unidade unidade;

    @JoinColumn(name = "idCupom")
    @ManyToOne
    private Cupom cupom;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ClienteNome", nullable = false, length = 255)
    private String nome;

    @Size(max = 255)
    @Column(name = "ClienteCnpjOuCpf", length = 255, unique = true)
    private String cnpjOuCpf;

    @NotNull
    @Size(max = 255)
    @Column(name = "ClienteEmail", nullable = false, length = 255, unique = true)
    private String email;

    @Column(name = "ClienteNascimento")
    @Temporal(TemporalType.DATE)
    private Date nascimento;

    @Size(max = 255)
    @Column(name = "ClienteTelefone", length = 255)
    private String telefone;

    @Size(max = 255)
    @Column(name = "ClienteCelular", length = 255)
    private String celular;

    @Size(max = 255)
    @Column(name = "ClienteCep", length = 255)
    private String cep;

    @Size(max = 255)
    @Column(name = "ClienteBairro", length = 255)
    private String bairro;

    @Size(max = 255)
    @Column(name = "ClienteEndereco", length = 255)
    private String endereco;

    @Size(max = 255)
    @Column(name = "ClienteClassificacao", length = 255)
    private String classificacao;

    @Size(max = 255)
    @Column(name = "ClienteStatus", length = 255)
    private String status;

    @Size(max = 3000)
    @Column(name = "ClienteObservacao", length = 3000)
    private String obs;

    @JsonIgnore
    @OneToMany(mappedBy="cliente")
    private List<Usuario> usuarios;

    @JsonIgnore
    @OneToMany(mappedBy="cliente")
    private List<Venda> vendas;

    @JsonIgnore
    @OneToMany(mappedBy="cliente")
    private List<Servico> servicos;

    @OneToMany(mappedBy="cliente")
    private List<Imagem> imagens;

    public Cliente() {
    }

    public Cliente(Integer id, TipoCliente tipoCliente, Cidade cidade, Unidade unidade,
                   Cupom cupom, String nome, String cnpjOuCpf, String email, Date nascimento,
                   String telefone, String celular, String cep, String bairro, String endereco,
                   String classificacao, String status, String obs) {
        this.id = id;
        this.tipoCliente = tipoCliente;
        this.cidade = cidade;
        this.unidade = unidade;
        this.cupom = cupom;
        this.nome = nome;
        this.cnpjOuCpf = cnpjOuCpf;
        this.email = email;
        this.nascimento = nascimento;
        this.telefone = telefone;
        this.celular = celular;
        this.cep = cep;
        this.bairro = bairro;
        this.endereco = endereco;
        this.classificacao = classificacao;
        this.status = status;
        this.obs = obs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public Cupom getCupom() {
        return cupom;
    }

    public void setCupom(Cupom cupom) {
        this.cupom = cupom;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpjOuCpf() {
        return cnpjOuCpf;
    }

    public void setCnpjOuCpf(String cnpjOuCpf) {
        this.cnpjOuCpf = cnpjOuCpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
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

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }

    public List<Imagem> getImagens() {
        return imagens;
    }

    public void setImagens(List<Imagem> imagens) {
        this.imagens = imagens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;

        Cliente cliente = (Cliente) o;

        return getId() != null ? getId().equals(cliente.getId()) : cliente.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", tipoCliente=" + tipoCliente +
                ", cidade=" + cidade +
                ", unidade=" + unidade +
                ", cupom=" + cupom +
                ", nome='" + nome + '\'' +
                ", cnpjOuCpf='" + cnpjOuCpf + '\'' +
                ", email='" + email + '\'' +
                ", nascimento=" + nascimento +
                ", telefone='" + telefone + '\'' +
                ", celular='" + celular + '\'' +
                ", cep='" + cep + '\'' +
                ", bairro='" + bairro + '\'' +
                ", endereco='" + endereco + '\'' +
                ", classificacao='" + classificacao + '\'' +
                ", status='" + status + '\'' +
                ", obs='" + obs + '\'' +
                ", usuarios=" + usuarios +
                ", vendas=" + vendas +
                ", servicos=" + servicos +
                ", imagens=" + imagens +
                '}';
    }
}

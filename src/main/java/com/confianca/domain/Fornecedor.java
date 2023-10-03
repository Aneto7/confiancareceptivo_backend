package com.confianca.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Fornecedor", catalog = "receptivo", schema = "", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"idFornecedor"})})
public class Fornecedor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "idFornecedor", nullable = false)
    private Integer id;

    @JoinColumn(name = "idTipoFornecedor")
    @ManyToOne
    private TipoFornecedor tipoFornecedor;

    @JoinColumn(name = "Cidade_idCidade")
    @ManyToOne
    private Cidade cidade;

    @JoinColumn(name = "TipoDespesa_idTipoDespesa")
    @ManyToOne
    private TipoDespesa tipoDespesa;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "FornecedorNome", nullable = false, length = 255)
    private String nome;

    @Size(max = 255)
    @Column(name = "FornecedorRazaoSocial", length = 255)
    private String razaoSocial;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "FornecedorCNPJ", nullable = false, length = 255, unique = true)
    private String cnpj;

    @Size(max = 255)
    @Column(name = "FornecedorInscricaoEstadual", length = 255)
    private String inscricaoEstadual;

    @Size(max = 255)
    @Column(name = "FornecedorResponsavel", length = 255)
    private String responsavel;

    @Size(max = 255)
    @Column(name = "FornecedorTelefoneComercial", length = 255)
    private String telefoneComercial;

    @Size(max = 255)
    @Column(name = "FornecedorCelular", length = 255)
    private String celular;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "FornecedorEmail", nullable = false, length = 255, unique = true)
    private String email;

    @Size(max = 255)
    @Column(name = "FornecedorCEP", length = 255)
    private String cep;

    @Size(max = 255)
    @Column(name = "FornecedorBairro", length = 255)
    private String bairro;

    @Size(max = 255)
    @Column(name = "FornecedorEndereco", length = 255)
    private String endereco;

    @Size(min = 1, max = 255)
    @Column(name = "FornecedorStatus", length = 255)
    private String status;

    @Size(max = 3000)
    @Column(name = "FornecedorObservacao", length = 3000)
    private String observacao;

    @JsonIgnore
    @OneToMany(mappedBy="fornecedor")
    private List<Produto> produtos;

    @JsonIgnore
    @OneToMany(mappedBy="fornecedor")
    private List<Despesa> despesas;

    public Fornecedor() {
    }

    public Fornecedor(Integer id, TipoFornecedor tipoFornecedor, Cidade cidade, TipoDespesa tipoDespesa, String nome,
                      String razaoSocial, String cnpj, String inscricaoEstadual, String responsavel,
                      String telefoneComercial, String celular, String email, String cep, String bairro,
                      String endereco, String status, String observacao) {
        this.id = id;
        this.tipoFornecedor = tipoFornecedor;
        this.cidade = cidade;
        this.tipoDespesa = tipoDespesa;
        this.nome = nome;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.inscricaoEstadual = inscricaoEstadual;
        this.responsavel = responsavel;
        this.telefoneComercial = telefoneComercial;
        this.celular = celular;
        this.email = email;
        this.cep = cep;
        this.bairro = bairro;
        this.endereco = endereco;
        this.status = status;
        this.observacao = observacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoFornecedor getTipoFornecedor() {
        return tipoFornecedor;
    }

    public void setTipoFornecedor(TipoFornecedor tipoFornecedor) {
        this.tipoFornecedor = tipoFornecedor;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public TipoDespesa getTipoDespesa() {
        return tipoDespesa;
    }

    public void setTipoDespesa(TipoDespesa tipoDespesa) {
        this.tipoDespesa = tipoDespesa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getTelefoneComercial() {
        return telefoneComercial;
    }

    public void setTelefoneComercial(String telefoneComercial) {
        this.telefoneComercial = telefoneComercial;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<Despesa> getDespesas() {
        return despesas;
    }

    public void setDespesas(List<Despesa> despesas) {
        this.despesas = despesas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fornecedor)) return false;

        Fornecedor that = (Fornecedor) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

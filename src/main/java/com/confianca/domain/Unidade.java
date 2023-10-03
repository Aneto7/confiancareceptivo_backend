package com.confianca.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Unidade", catalog = "receptivo", schema = "", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"idUnidade"})})
public class Unidade implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUnidade", nullable = false)
    private Integer id;

    @JoinColumn(name = "Cidade_idCidade")
    @ManyToOne
    private Cidade cidade;

    @NotNull
    @Column(name = "UnidadeNome", nullable = false, length = 255)
    private String nome;

    @NotNull
    @Column(name = "UnidadeRazaoSocial", nullable = false, length = 255)
    private String razaoSocial;

    @Column(name = "UnidadeTelefone", length = 255)
    private String telefone;

    @Column(name = "UnidadeEndereco", length = 255)
    private String endereco;

    @Column(name = "UnidadeCEP", length = 255)
    private String cep;

    @Column(name = "UnidadeCpfOuCnpj", length = 255, unique = true)
    private String cpfOuCnpj;

    @Column(name = "UnidadeNomeResponsavel", length = 255)
    private String nomeResponsavel;

    @Column(name = "UnidadeEmail", length = 255, unique = true)
    private String email;

    @Column(name = "UnidadeSenha", length = 255)
    private String senha;

    @NotNull
    @Column(name = "UnidadeStatus", nullable = false, length = 255)
    private String status;

    @Column(name = "UnidadeObservacao", length = 3000)
    private String obs;

    @Column(name = "UnidadeTelefonePlantao", length = 255)
    private String telefonePlantao;

    @JsonIgnore
    @OneToMany(mappedBy="unidade")
    private List<Cliente> clientes;

    public Unidade() {
    }

    public Unidade(Integer id, Cidade cidade, String nome, String razaoSocial, String telefone, String endereco, String cep, String cpfOuCnpj, String nomeResponsavel, String email, String senha, String status, String obs, String telefonePlantao) {
        this.id = id;
        this.cidade = cidade;
        this.nome = nome;
        this.razaoSocial = razaoSocial;
        this.telefone = telefone;
        this.endereco = endereco;
        this.cep = cep;
        this.cpfOuCnpj = cpfOuCnpj;
        this.nomeResponsavel = nomeResponsavel;
        this.email = email;
        this.senha = senha;
        this.status = status;
        this.obs = obs;
        this.telefonePlantao = telefonePlantao;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public void setCpfOuCnpj(String cpfOuCnpj) {
        this.cpfOuCnpj = cpfOuCnpj;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    public String getTelefonePlantao() {
        return telefonePlantao;
    }

    public void setTelefonePlantao(String telefonePlantao) {
        this.telefonePlantao = telefonePlantao;
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
        if (!(o instanceof Unidade)) return false;

        Unidade unidade = (Unidade) o;

        return getId() != null ? getId().equals(unidade.getId()) : unidade.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

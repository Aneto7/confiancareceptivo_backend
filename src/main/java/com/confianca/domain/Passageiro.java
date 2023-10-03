/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.confianca.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Passageiro", catalog = "receptivo", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"idPassageiro"})})
public class Passageiro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPassageiro", nullable = false)
    private Integer id;

    @JsonIgnore
    @JoinColumn(name = "ProdutoVendido_idProdutoVendido")
    @ManyToOne
    private ProdutoVendido produtoVendido;

    @NotNull()
    @Size(min = 1, max = 255)
    @Column(name = "PassageiroNome", nullable = false, length = 255)
    private String nome;

    @NotNull
    @Column(name = "PassageiroNascimento", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date nascimento;

    @Size(max = 255)
    @Column(name = "PassageiroCPF", length = 255)
    private String cpf;

    @Size(max = 255)
    @Column(name = "PassageiroTelefone", length = 255)
    private String telefone;

    @Size(max = 255)
    @Column(name = "PassageiroCelular", length = 255)
    private String celular;

    @Size(max = 255)
    @Column(name = "PassageiroEmail", length = 255)
    private String email;

    @Size(max = 255)
    @Column(name = "PassageiroStatus", length = 255)
    private String status;

    @Size(max = 3000)
    @Column(name = "PassageiroObservacao", length = 3000)
    private String obs;

    public Passageiro() {
    }

    public Passageiro(Integer id, ProdutoVendido produtoVendido, String nome, Date nascimento, String cpf, String telefone, String celular, String email, String status, String obs) {
        this.id = id;
        this.produtoVendido = produtoVendido;
        this.nome = nome;
        this.nascimento = nascimento;
        this.cpf = cpf;
        this.telefone = telefone;
        this.celular = celular;
        this.email = email;
        this.status = status;
        this.obs = obs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProdutoVendido getProdutoVendido() {
        return produtoVendido;
    }

    public void setProdutoVendido(ProdutoVendido produtoVendido) {
        this.produtoVendido = produtoVendido;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Passageiro)) return false;

        Passageiro that = (Passageiro) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

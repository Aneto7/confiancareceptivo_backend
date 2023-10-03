package com.confianca.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Guia", catalog = "receptivo", schema = "", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"idGuia"})})
public class Guia implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idGuia", nullable = false)
    private Integer id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "GuiaNome",nullable = false, length = 255)
    private String nome;

    @Size(max = 255)
    @Column(name = "GuiaTelefone", length = 255)
    private String telefone;

    @Size(max = 255)
    @Column(name = "GuiaCelular", length = 255)
    private String celular;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "GuiaEmail", nullable = false, length = 255, unique = true)
    private String email;

    @Size(max = 1000)
    @Column(name = "GuiaObservacao", length = 1000)
    private String obs;

    @JsonIgnore
    @OneToMany(mappedBy="guia")
    private List<Despesa> despesas;

    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "GuiaStatus", nullable = false, length = 1000)
    private String status;

    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "GuiaCpfOuCnpj", nullable = false, length = 255)
    private String cpfOuCnpj;

    @JsonIgnore
    @ManyToMany(mappedBy="guia")
    private List<Servico> servicos;

    public Guia() {
    }

    public Guia(Integer id, String nome, String telefone, String celular, String email, String obs, String status, String cpfOuCnpj) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.celular = celular;
        this.email = email;
        this.obs = obs;
        this.status = status;
        this.cpfOuCnpj = cpfOuCnpj;
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

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public List<Despesa> getDespesas() {
        return despesas;
    }

    public void setDespesas(List<Despesa> despesas) {
        this.despesas = despesas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public void setCpfOuCnpj(String cpfOuCnpj) {
        this.cpfOuCnpj = cpfOuCnpj;
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
        if (!(o instanceof Guia)) return false;

        Guia guia = (Guia) o;

        return getId() != null ? getId().equals(guia.getId()) : guia.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

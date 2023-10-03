package com.confianca.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Pagador", catalog = "receptivo", schema = "", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"idPagador"})})
public class Pagador implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPagador", nullable = false)
    private Integer id;

    @JsonIgnore
    @OneToOne(mappedBy="pagador")
    private Venda venda;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "PagadorNome",nullable = false, length = 255)
    private String nome;

    @Size(max = 255)
    @Column(name = "PagadorEmail", length = 255)
    private String email;

    @Size(max = 255)
    @Column(name = "PagadorCpfOuCnpj", length = 255)
    private String cpfOuCnpj;

    @Size(max = 255)
    @Column(name = "PagadorTelefone", length = 255)
    private String telefone;

    public Pagador() {
    }

    public Pagador(Integer id, Venda venda, String nome, String email, String cpfOuCnpj, String telefone) {
        this.id = id;
        this.venda = venda;
        this.nome = nome;
        this.email = email;
        this.cpfOuCnpj = cpfOuCnpj;
        this.telefone = telefone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public void setCpfOuCnpj(String cpfOuCnpj) {
        this.cpfOuCnpj = cpfOuCnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pagador)) return false;

        Pagador guia = (Pagador) o;

        return getId() != null ? getId().equals(guia.getId()) : guia.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

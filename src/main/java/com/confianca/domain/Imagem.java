package com.confianca.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Imagem", catalog = "receptivo", schema = "", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"idImagem"})})
public class Imagem implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="idImagem", nullable = false)
    private Integer id;

    @JsonIgnore
    @JoinColumn(name = "Produto_idProduto")
    @ManyToOne
    private Produto produto;

    @JsonIgnore
    @JoinColumn(name = "Cliente_idCliente")
    @ManyToOne
    private Cliente cliente;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ImagemNome", nullable = false, length = 255)
    private String nome;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ImagemTipo", nullable = false, length = 255)
    private String tipo;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ImagemLocal", nullable = false, length = 255)
    private String local;

    @NotNull
    @Column(name = "ImagemTamanho", nullable = false, length = 255)
    private Double tamanho;

    @NotNull
    @Column(name = "ImagemPrincipal", length = 255)
    private Boolean principal;

    public Imagem() {
    }

    public Imagem(Integer id, Produto produto, Cliente cliente, String nome, String tipo, String local, Double tamanho, Boolean principal) {
        this.id = id;
        this.produto = produto;
        this.cliente = cliente;
        this.nome = nome;
        this.tipo = tipo;
        this.local = local;
        this.tamanho = tamanho;
        this.principal = principal;
    }


    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Double getTamanho() {
        return tamanho;
    }

    public void setTamanho(Double tamanho) {
        this.tamanho = tamanho;
    }

    public Boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Imagem)) return false;

        Imagem imagem = (Imagem) o;

        return getId() != null ? getId().equals(imagem.getId()) : imagem.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

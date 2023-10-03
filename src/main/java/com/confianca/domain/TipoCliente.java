package com.confianca.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TipoCliente", catalog = "receptivo", schema = "", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"idTipoCliente"})})
public class TipoCliente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTipoCliente", nullable = false)
    private Integer id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "TipoClienteNome", nullable = false, length = 255, unique = true)
    private String nome;

    @NotNull
    @Column(name = "TipoClienteDesconto")
    private Double desconto;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "TipoClienteStatus", nullable = false, length = 255)
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy="tipoCliente")
    private List<Cliente> clientes;

    public TipoCliente() {
    }

    public TipoCliente(Integer id, String nome, Double desconto, String status) {
        this.id = id;
        this.nome = nome;
        this.desconto = desconto;
        this.status = status;
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

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(o instanceof TipoCliente)) return false;

        TipoCliente that = (TipoCliente) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

package com.confianca.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Bandeira", catalog = "receptivo", schema = "", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"idBandeira"})})
public class BandeiraDeCartaoDeCredito implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBandeira", nullable = false)
    private Integer id;

    @Size(min = 1, max = 255)
    @Column(name = "BandeiraNome", nullable = false, length = 255, unique = true)
    private String nome;

    @Size(min = 1, max = 255)
    @Column(name = "BandeiraSigla", nullable = false, length = 255)
    private String sigla;

    @Size(min = 1, max = 255)
    @Column(name = "BandeiraStatus", nullable = false, length = 255)
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy="bandeira")
    private List<CondicaoPagamentoRecebimento> condicaoPagamentoRecebimentos;


    public BandeiraDeCartaoDeCredito() {
    }

    public BandeiraDeCartaoDeCredito(Integer id, String nome, String sigla, String status) {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CondicaoPagamentoRecebimento> getCondicaoPagamentoRecebimentos() {
        return condicaoPagamentoRecebimentos;
    }

    public void setCondicaoPagamentoRecebimentos(List<CondicaoPagamentoRecebimento> condicaoPagamentoRecebimentos) {
        this.condicaoPagamentoRecebimentos = condicaoPagamentoRecebimentos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BandeiraDeCartaoDeCredito)) return false;

        BandeiraDeCartaoDeCredito that = (BandeiraDeCartaoDeCredito) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

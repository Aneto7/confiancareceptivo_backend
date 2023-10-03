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
import java.util.List;

@Entity
@Table(name = "TipoPagamentoRecebimento", catalog = "receptivo", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"idTipoPagamentoRecebimento"})})
public class TipoPagamentoRecebimento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTipoPagamentoRecebimento", nullable = false)
    private Integer id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "TipoPagamentoRecebimentoNome", nullable = false, length = 255, unique = true)
    private String nome;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "TipoPagamentoRecebimentoStatus", nullable = false, length = 255)
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy="tipo")
    private List<CondicaoPagamentoRecebimento> condicaoPagamentoRecebimentos;

    @JsonIgnore
    @OneToMany(mappedBy="tipo")
    private List<Recebimento> recebimentos;

    @JsonIgnore
    @OneToMany(mappedBy="tipoPagamentoRecebimento")
    private List<Despesa> despesas;

    @JsonIgnore
    @OneToMany(mappedBy="tipoPagamentoRecebimento")
    private List<Receita> receitas;

    public TipoPagamentoRecebimento() {
    }

    public TipoPagamentoRecebimento(Integer id, String nome, String status) {
        this.id = id;
        this.nome = nome;
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

    public List<Recebimento> getRecebimentos() {
        return recebimentos;
    }

    public void setRecebimentos(List<Recebimento> recebimentos) {
        this.recebimentos = recebimentos;
    }

    public List<Despesa> getDespesas() {
        return despesas;
    }

    public void setDespesas(List<Despesa> despesas) {
        this.despesas = despesas;
    }

    public List<Receita> getReceitas() {
        return receitas;
    }

    public void setReceitas(List<Receita> receitas) {
        this.receitas = receitas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoPagamentoRecebimento)) return false;

        TipoPagamentoRecebimento that = (TipoPagamentoRecebimento) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

package com.confianca.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Meta", catalog = "receptivo", schema = "", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"idMeta"})})
public class Meta implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="idMeta", nullable = false)
    private Integer id;

    @Basic(optional = false)
    @JoinColumn(name = "Unidade_idUnidade", nullable = false)
    @ManyToOne
    private Unidade unidade;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "MetaNome", nullable = false, length = 255)
    private String nome;

    @NotNull
    @Column(name = "MetaPercentualAdicionalDeComissao", nullable = false)
    private Double percentualAdicionalDeComissao;

    @NotNull
    @Column(name = "MetaValorMeta", nullable = false)
    private Double valorMeta;

    @NotNull
    @Column(name = "MetaDataInicial", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataInicial;

    @NotNull
    @Column(name = "MetaDataFinal", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataFinal;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "MetaStatus", nullable = false, length = 255)
    private String status;

    public Meta() {
    }

    public Meta(Integer id, Unidade unidade, String nome, Double percentualAdicionalDeComissao, Double valorMeta, Date dataInicial, Date dataFinal, String status) {
        this.id = id;
        this.unidade = unidade;
        this.nome = nome;
        this.percentualAdicionalDeComissao = percentualAdicionalDeComissao;
        this.valorMeta = valorMeta;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPercentualAdicionalDeComissao() {
        return percentualAdicionalDeComissao;
    }

    public void setPercentualAdicionalDeComissao(Double percentualAdicionalDeComissao) {
        this.percentualAdicionalDeComissao = percentualAdicionalDeComissao;
    }

    public Double getValorMeta() {
        return valorMeta;
    }

    public void setValorMeta(Double valorMeta) {
        this.valorMeta = valorMeta;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meta)) return false;

        Meta meta = (Meta) o;

        return getId() != null ? getId().equals(meta.getId()) : meta.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

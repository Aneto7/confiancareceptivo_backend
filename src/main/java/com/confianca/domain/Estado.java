package com.confianca.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Estado", catalog = "receptivo", schema = "", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"idEstado"})})
public class Estado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEstado", nullable = false)
    private Integer id;

    @NotNull
    @JoinColumn(name = "Pais_idPais", nullable = false)
    @ManyToOne
    private Pais pais;

    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "EstadoNome", nullable = false, length = 45, unique = true)
    private String nome;

    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "EstadoSigla", nullable = false, length = 45)
    private String sigla;

    @JsonIgnore
    @OneToMany(mappedBy="estado")
    private List<Cidade> cidades;

    public Estado() {
    }

    public Estado(Integer id, Pais pais, String nome, String sigla) {
        this.id = id;
        this.pais = pais;
        this.nome = nome;
        this.sigla = sigla;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
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

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Estado)) return false;

        Estado estado = (Estado) o;

        return getId() != null ? getId().equals(estado.getId()) : estado.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}

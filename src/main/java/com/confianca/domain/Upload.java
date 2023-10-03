package com.confianca.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Upload", catalog = "receptivo", schema = "", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"idUpload"})})
public class Upload implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUpload", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "UploadNome", length = 255)
    private String nome;

    @Size(max = 255)
    @Column(name = "UploadCaminho", length = 255)
    private String caminho;

    @NotNull
    @Column(name = "UploadIdRegistro", nullable = false)
    private Integer idRegistro;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "UploadObjeto", nullable = false, length = 255)
    private String objeto;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "UploadStatus", nullable = false, length = 255)
    private String status;

    public Upload() {
    }

    public Upload(Integer id, String nome, String caminho, Integer idRegistro, String objeto, String status) {
        this.id = id;
        this.nome = nome;
        this.caminho = caminho;
        this.idRegistro = idRegistro;
        this.objeto = objeto;
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

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public Integer getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
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
        if (!(o instanceof Upload)) return false;

        Upload historico = (Upload) o;

        return getId() != null ? getId().equals(historico.getId()) : historico.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Upload{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", idRegistro=" + idRegistro +
                ", objeto='" + objeto + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

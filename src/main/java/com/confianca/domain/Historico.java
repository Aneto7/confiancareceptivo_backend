package com.confianca.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Historico", catalog = "receptivo", schema = "", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"idHistorico"})})
public class Historico implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHistorico", nullable = false)
    private Integer id;

    @JoinColumn(name = "UsuarioCadastro_idUsuarioCadastro")
    @ManyToOne
    private Usuario usuarioCadastro;

    @NotNull
    @Column(name = "HistoricoIdRegistro", nullable = false)
    private Integer idRegistro;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "HistoricoNome", nullable = false, length = 255)
    private String nome;

    @NotNull
    @Column(name = "HistoricoData", nullable = false)
    private Date data;

    @NotNull
    @Column(name = "HistoricoTexto", nullable = false, length = 255)
    private String texto;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "HistoricoStatus", nullable = false, length = 255)
    private String status;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "HistoricoObjeto", nullable = false, length = 255)
    private String objeto;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "HistoricoAcao", nullable = false, length = 255)
    private String acao;

    @Size(max = 3000)
    @Column(name = "HistoricoObservacao", length = 3000)
    private String obs;

    public Historico() {
    }

    public Historico(Integer id, Usuario usuarioCadastro, Integer idRegistro, String nome, Date data, String texto, String status, String objeto, String acao, String obs) {
        this.id = id;
        this.usuarioCadastro = usuarioCadastro;
        this.idRegistro = idRegistro;
        this.nome = nome;
        this.data = data;
        this.texto = texto;
        this.status = status;
        this.objeto = objeto;
        this.acao = acao;
        this.obs = obs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuarioCadastro() {
        return usuarioCadastro;
    }

    public void setUsuarioCadastro(Usuario usuarioCadastro) {
        this.usuarioCadastro = usuarioCadastro;
    }

    public Integer getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
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
        if (!(o instanceof Historico)) return false;

        Historico historico = (Historico) o;

        return getId() != null ? getId().equals(historico.getId()) : historico.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Historico{");
        sb.append("id=").append(id);
        sb.append(", usuarioCadastro=").append(usuarioCadastro);
        sb.append(", idRegistro=").append(idRegistro);
        sb.append(", nome='").append(nome).append('\'');
        sb.append(", data=").append(data);
        sb.append(", texto='").append(texto).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", objeto='").append(objeto).append('\'');
        sb.append(", acao='").append(acao).append('\'');
        sb.append(", obs='").append(obs).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

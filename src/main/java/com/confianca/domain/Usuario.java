package com.confianca.domain;

import com.confianca.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "Usuario", catalog = "receptivo", schema = "", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"UsuarioLogin"})})
public class Usuario {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario", nullable = false)
    private Integer id;

    @NotNull
    @JoinColumn(name = "Cliente_idCliente", nullable = false)
    @ManyToOne
    private Cliente cliente;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "UsuarioNome", nullable = false, length = 255)
    private String nome;

    @Size(max = 255)
    @Column(name = "UsuarioRamal", length = 255)
    private String ramal;

    @Size(max = 255)
    @Column(name = "UsuarioTipo", nullable = false)
    private String tipo;

    @Size(max = 255)
    @Column(name = "UsuarioPermissao", nullable = false)
    private String permissao;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "UsuarioStatus", nullable = false, length = 255)
    private String status;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "UsuarioLogin", nullable = false, length = 255, unique = true)
    private String login;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "UsuarioSenha", nullable = false, length = 255)
    private String senha;

    @JsonIgnore
    @OneToMany(mappedBy="usuario")
    private List<Venda> vendas;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PERFIS")
    private Set<Integer> perfis = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy="usuarioCadastro")
    private List<Historico> historicosCadastro;

    public Usuario() {
        addPerfil(Perfil.USUARIO);
    }

    public Usuario(Integer id, Cliente cliente, String nome, String ramal, String tipo, String permissao, String status, String login, String senha) {
        this.id = id;
        this.cliente = cliente;
        this.nome = nome;
        this.ramal = ramal;
        this.tipo = tipo;
        this.permissao = permissao;
        this.status = status;
        this.login = login;
        this.senha = senha;
        addPerfil(Perfil.USUARIO);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRamal() {
        return ramal;
    }

    public void setRamal(String ramal) {
        this.ramal = ramal;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPermissao() {
        return permissao;
    }

    public void setPermissao(String permissao) {
        this.permissao = permissao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }

    public Set<Perfil> getPerfis(){
        return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
    }

    public Set<Integer> getPerfisGeral() {
        return perfis;
    }

    public void setPerfis(Set<Integer> perfis) {
        this.perfis = perfis;
    }

    public void addPerfil(Perfil perfil){
        perfis.add(perfil.getCod());
    }

    public List<Historico> getHistoricosCadastro() {
        return historicosCadastro;
    }

    public void setHistoricosCadastro(List<Historico> historicosCadastro) {
        this.historicosCadastro = historicosCadastro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;

        Usuario usuario = (Usuario) o;

        return getId() != null ? getId().equals(usuario.getId()) : usuario.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}



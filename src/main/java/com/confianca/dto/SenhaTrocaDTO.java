package com.confianca.dto;

import java.io.Serializable;

public class SenhaTrocaDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String senha;
    private String novaSenha;

    public SenhaTrocaDTO() {
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNovaSenha() {
        return this.novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}

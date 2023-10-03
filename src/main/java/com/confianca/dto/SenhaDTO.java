package com.confianca.dto;

import java.io.Serializable;

public class SenhaDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String senha;

    public SenhaDTO() {
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

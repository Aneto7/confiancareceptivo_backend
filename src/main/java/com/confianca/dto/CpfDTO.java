package com.confianca.dto;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class CpfDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message="Preenchimento obrigatório")
    private String cpf;

    public CpfDTO() {
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}

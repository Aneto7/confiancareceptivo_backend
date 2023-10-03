package com.confianca.domain.enums;

public enum Perfil {
    ADMIN(1, "ROLE_ADMIN"),
    FINANCEIRO(2, "ROLE_FINANCEIRO"),
    DESENVOLVIMENTO(3, "ROLE_DESENVOLVIMENTO"),
    JURIDICO(4,"ROLE_JURIDICO"),
    USUARIO(5,"ROLE_USUARIO"),
    UNIDADE(6,"ROLE_UNIDADE"),
    CLIENTEAGENCIA(7,"ROLE_CLIENTEAGENCIA"),
    CLIENTE(8,"ROLE_CLIENTE");

    private int cod;
    private String descricao;

    private Perfil(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Perfil toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }

        for (Perfil x : Perfil.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }
        throw new IllegalArgumentException("ID inválido: " + cod);
    }
}

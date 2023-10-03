package com.confianca.dto;

public class ClienteCadastroDTO {
    private String nome;
    private String cpfOuCnpj;
    private String email;
    private String telefone;
    private String celular;
    private String cep;
    private String bairro;
    private String endereco;
    private String cidade;
    private String estado;

    public ClienteCadastroDTO() {
    }

    public ClienteCadastroDTO(String nome, String cpfOuCnpj, String email, String telefone,
                              String celular, String cep, String bairro, String endereco, String cidade, String estado) {
        this.nome = nome;
        this.cpfOuCnpj = cpfOuCnpj;
        this.email = email;
        this.telefone = telefone;
        this.celular = celular;
        this.cep = cep;
        this.bairro = bairro;
        this.endereco = endereco;
        this.cidade = cidade;
        this.estado = estado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public void setCpfOuCnpj(String cpfOuCnpj) {
        this.cpfOuCnpj = cpfOuCnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append('\n');
        sb.append("Dados do cliente para cadastro: ").append('\n');
        sb.append("Nome: ");
        sb.append(nome).append('\n');
        sb.append("CPF ou CNPJ: ");
        sb.append(cpfOuCnpj).append('\n');
        sb.append("Email: ");
        sb.append(email).append('\n');
        sb.append("Telefone: ");
        sb.append(telefone).append('\n');
        sb.append("Celular: ");
        sb.append(celular).append('\n');
        sb.append("CEP: ");
        sb.append(cep).append('\n');
        sb.append("Bairro: ");
        sb.append(bairro).append('\n');
        sb.append("Endereço: ");
        sb.append(endereco).append('\n');
        sb.append("Cidade: ");
        sb.append(cidade).append('\n');
        sb.append("Estado: ");
        sb.append(estado).append('\n');
        sb.append("Os dados foram enviados através do sistema Tour Regular, serão utilizados para análise de perfil para cadastro.").append('\n');
        sb.append("Caso seja necessário será realizado contato para mais informações.");

        return sb.toString();
    }
}

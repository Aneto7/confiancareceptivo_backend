/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.confianca.dto;

import com.confianca.domain.Passageiro;
import com.confianca.domain.ProdutoVendido;
import com.confianca.domain.Receita;
import com.confianca.services.Extenso;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProdutoVendidoVoucherDTO implements Serializable {
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    private Integer id;
    private String nome;
    private Date dataTarifaInicio;
    private String produtoHorarioSaida;
    private String produtoHorarioRetorno;
    private String pontoSaidaNome;
    private String pontoSaidaEndereco;
    private String clienteNome;
    private String clienteCpfOuCnpj;
    private String clienteEndereco;
    private String clienteBairro;
    private String clienteCep;
    private String clienteTelefone;
    private String cidadeNome;
    private String estadoSigla;
    private Date vendaVencimento;
    private String origem = "";
    private String destino = "";
    private List<PassageiroDTO> passageirosDTO;
    private List<DetalhesProdutoDTO> detalhesProdutoDTO;
    private List<OpcionalVendidoDTO> opcionaisDTO;
    private String venda = "";
    private String servico = "";

    public ProdutoVendidoVoucherDTO() {
    }

    public ProdutoVendidoVoucherDTO(ProdutoVendido obj) {
        id = obj.getId();
        nome = obj.getNome();

        if (obj.getProduto().getTipoProduto().getNome().equals("Transfer")) {
            dataTarifaInicio = obj.getDataIda();
            produtoHorarioSaida = sdf.format(obj.getDataIda());
            pontoSaidaEndereco = "";
            String parts[] = obj.getPassageiros().get(0).getObs().split("#");
            if (obj.getIdaEVolta()) {
                if (obj.getDataVolta() != null) {
                    //produtoHorarioRetorno = "Retorno: " + sdf1.format(obj.getDataVolta());
                    produtoHorarioRetorno = "";
                    origem = "Origem: " + obj.getTrechoProduto().getOrigem();
                    destino = "Destino: " + obj.getTrechoProduto().getDestino();
                    pontoSaidaNome = "Trecho:                           " + obj.getTrechoProduto().getOrigem() + " > " + obj.getTrechoProduto().getDestino();
                    if (parts.length > 1) {
                        pontoSaidaEndereco = "Informações:                  " + parts[0];
                    } else {
                        pontoSaidaEndereco = "";
                    }
                } else {
                    produtoHorarioRetorno = "";
                    origem = "Origem: " + obj.getTrechoProduto().getDestino();
                    destino = "Destino: " + obj.getTrechoProduto().getOrigem();
                    pontoSaidaNome = "Trecho:                           " + obj.getTrechoProduto().getDestino() + " > " + obj.getTrechoProduto().getOrigem();
                    if (parts.length > 1) {
                        pontoSaidaEndereco = "Informações:                  " + parts[1];
                    } else {
                        pontoSaidaEndereco = "";
                    }
                }
            } else {
                //produtoHorarioRetorno = "Retorno: " + sdf1.format(obj.getDataVolta());
                produtoHorarioRetorno = "";
                origem = "Origem: " + obj.getTrechoProduto().getOrigem();
                destino = "Destino: " + obj.getTrechoProduto().getDestino();
                pontoSaidaNome = "Trecho:                           " + obj.getTrechoProduto().getOrigem() + " > " + obj.getTrechoProduto().getDestino();
                pontoSaidaEndereco = "Informações:                  " + parts[0];
            }
        } else {
            produtoHorarioSaida = obj.getProduto().getHorarioSaida();
            produtoHorarioRetorno = "Retorno: " + obj.getProduto().getHorarioRetorno();
            dataTarifaInicio = obj.getTarifa().getData().getData();
        }

        if (obj.getProduto().getPontoDeSaida() != null && !obj.getProduto().getTipoProduto().getNome().equals("Transfer")) {
            pontoSaidaNome = "Ponto de Saída:          " + obj.getProduto().getPontoDeSaida().getNome();
            pontoSaidaEndereco = "Endereço:                   " + obj.getProduto().getPontoDeSaida().getEndereco() + " " + obj.getPassageiros().get(0).getObs();

        } else {
//            if(obj.getTrechoProduto() != null) {
//                pontoSaidaNome = obj.getTrechoProduto().getOrigem();
//                pontoSaidaEndereco = obj.getTrechoProduto().getNome() + " " + obj.getPassageiros().get(0).getObs();
//            }
        }

        clienteNome = obj.getVenda().getCliente().getNome();
        clienteCpfOuCnpj = obj.getVenda().getCliente().getCnpjOuCpf();
        clienteEndereco = obj.getVenda().getCliente().getEndereco();
        clienteBairro = obj.getVenda().getCliente().getBairro();
        clienteCep = obj.getVenda().getCliente().getCep();
        clienteTelefone = obj.getVenda().getCliente().getTelefone();
        cidadeNome = obj.getVenda().getCliente().getCidade().getNome();
        estadoSigla = obj.getVenda().getCliente().getCidade().getEstado().getSigla();
        vendaVencimento = obj.getVenda().getDataVencimento();
        venda = obj.getVenda().getId().toString();
        servico = obj.getServico().getIdentificador();
    }

    public ProdutoVendidoVoucherDTO(SimpleDateFormat sdf, SimpleDateFormat sdf1, Integer id,
                                    String nome, Date dataTarifaInicio, String produtoHorarioSaida,
                                    String produtoHorarioRetorno, String pontoSaidaNome,
                                    String pontoSaidaEndereco, String clienteNome, String clienteCpfOuCnpj,
                                    String clienteEndereco, String clienteBairro, String clienteCep,
                                    String clienteTelefone, String cidadeNome, String estadoSigla,
                                    Date vendaVencimento, String origem, String destino,
                                    List<PassageiroDTO> passageirosDTO, List<DetalhesProdutoDTO> detalhesProdutoDTO,
                                    List<OpcionalVendidoDTO> opcionaisDTO, String venda, String servico) {
        this.sdf = sdf;
        this.sdf1 = sdf1;
        this.id = id;
        this.nome = nome;
        this.dataTarifaInicio = dataTarifaInicio;
        this.produtoHorarioSaida = produtoHorarioSaida;
        this.produtoHorarioRetorno = produtoHorarioRetorno;
        this.pontoSaidaNome = pontoSaidaNome;
        this.pontoSaidaEndereco = pontoSaidaEndereco;
        this.clienteNome = clienteNome;
        this.clienteCpfOuCnpj = clienteCpfOuCnpj;
        this.clienteEndereco = clienteEndereco;
        this.clienteBairro = clienteBairro;
        this.clienteCep = clienteCep;
        this.clienteTelefone = clienteTelefone;
        this.cidadeNome = cidadeNome;
        this.estadoSigla = estadoSigla;
        this.vendaVencimento = vendaVencimento;
        this.origem = origem;
        this.destino = destino;
        this.passageirosDTO = passageirosDTO;
        this.detalhesProdutoDTO = detalhesProdutoDTO;
        this.opcionaisDTO = opcionaisDTO;
        this.venda = venda;
        this.servico = servico;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public SimpleDateFormat getSdf1() {
        return sdf1;
    }

    public void setSdf1(SimpleDateFormat sdf1) {
        this.sdf1 = sdf1;
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

    public Date getDataTarifaInicio() {
        return dataTarifaInicio;
    }

    public void setDataTarifaInicio(Date dataTarifaInicio) {
        this.dataTarifaInicio = dataTarifaInicio;
    }

    public String getProdutoHorarioSaida() {
        return produtoHorarioSaida;
    }

    public void setProdutoHorarioSaida(String produtoHorarioSaida) {
        this.produtoHorarioSaida = produtoHorarioSaida;
    }

    public String getProdutoHorarioRetorno() {
        return produtoHorarioRetorno;
    }

    public void setProdutoHorarioRetorno(String produtoHorarioRetorno) {
        this.produtoHorarioRetorno = produtoHorarioRetorno;
    }

    public String getPontoSaidaNome() {
        return pontoSaidaNome;
    }

    public void setPontoSaidaNome(String pontoSaidaNome) {
        this.pontoSaidaNome = pontoSaidaNome;
    }

    public String getPontoSaidaEndereco() {
        return pontoSaidaEndereco;
    }

    public void setPontoSaidaEndereco(String pontoSaidaEndereco) {
        this.pontoSaidaEndereco = pontoSaidaEndereco;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public String getClienteCpfOuCnpj() {
        return clienteCpfOuCnpj;
    }

    public void setClienteCpfOuCnpj(String clienteCpfOuCnpj) {
        this.clienteCpfOuCnpj = clienteCpfOuCnpj;
    }

    public String getClienteEndereco() {
        return clienteEndereco;
    }

    public void setClienteEndereco(String clienteEndereco) {
        this.clienteEndereco = clienteEndereco;
    }

    public String getClienteBairro() {
        return clienteBairro;
    }

    public void setClienteBairro(String clienteBairro) {
        this.clienteBairro = clienteBairro;
    }

    public String getClienteCep() {
        return clienteCep;
    }

    public void setClienteCep(String clienteCep) {
        this.clienteCep = clienteCep;
    }

    public String getClienteTelefone() {
        return clienteTelefone;
    }

    public void setClienteTelefone(String clienteTelefone) {
        this.clienteTelefone = clienteTelefone;
    }

    public String getCidadeNome() {
        return cidadeNome;
    }

    public void setCidadeNome(String cidadeNome) {
        this.cidadeNome = cidadeNome;
    }

    public String getEstadoSigla() {
        return estadoSigla;
    }

    public void setEstadoSigla(String estadoSigla) {
        this.estadoSigla = estadoSigla;
    }

    public Date getVendaVencimento() {
        return vendaVencimento;
    }

    public void setVendaVencimento(Date vendaVencimento) {
        this.vendaVencimento = vendaVencimento;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public List<PassageiroDTO> getPassageirosDTO() {
        return passageirosDTO;
    }

    public void setPassageirosDTO(List<PassageiroDTO> passageirosDTO) {
        this.passageirosDTO = passageirosDTO;
    }

    public List<DetalhesProdutoDTO> getDetalhesProdutoDTO() {
        return detalhesProdutoDTO;
    }

    public void setDetalhesProdutoDTO(List<DetalhesProdutoDTO> detalhesProdutoDTO) {
        this.detalhesProdutoDTO = detalhesProdutoDTO;
    }

    public List<OpcionalVendidoDTO> getOpcionaisDTO() {
        return opcionaisDTO;
    }

    public void setOpcionaisDTO(List<OpcionalVendidoDTO> opcionaisDTO) {
        this.opcionaisDTO = opcionaisDTO;
    }

    public String getVenda() {
        return venda;
    }

    public void setVenda(String venda) {
        this.venda = venda;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }
}

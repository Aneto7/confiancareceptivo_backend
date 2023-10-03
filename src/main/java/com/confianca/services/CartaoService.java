package com.confianca.services;

import com.confianca.domain.Cartao;
import com.confianca.repositories.CartaoRepository;
import com.confianca.services.exeptions.DataIntegrityException;
import com.confianca.services.exeptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository repo;

    public Cartao find(Integer id) {
        Optional<Cartao> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cartao.class.getName()));
    }

    public Cartao insert(Cartao obj) {
        obj.setId(null);
        return repo.save(obj);
    }

    public void delete(Integer id) {
        find(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um cartão que possui lançamentos");
        }
    }

    public List<Cartao> findAll() {
        return repo.findAll();
    }

    public Page<Cartao> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public String statusRecebimento(String returnCode) {
        String mensagemCartao = "Aguardando retorno do pagamento";
        if (returnCode.equals("4") || returnCode.equals("6")) {
            mensagemCartao = "Operação realizada com sucesso";
        } else if (returnCode.equals("05")) {
            mensagemCartao = "Não Autorizada";
        } else if (returnCode.equals("57")) {
            mensagemCartao = "Cartão Expirado";
        } else if (returnCode.equals("78")) {
            mensagemCartao = "Cartão Bloqueado";
        } else if (returnCode.equals("99")) {
            mensagemCartao = "Time Out";
        } else if (returnCode.equals("77")) {
            mensagemCartao = "Cartão Cancelado";
        } else if (returnCode.equals("70")) {
            mensagemCartao = "Problemas com o Cartão de Crédito";
        } else {
            mensagemCartao = "Operação realizada com sucesso";
        }
        return mensagemCartao;
    }
}

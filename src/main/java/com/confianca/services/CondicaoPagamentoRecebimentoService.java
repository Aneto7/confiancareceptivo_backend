package com.confianca.services;

import com.confianca.domain.CondicaoPagamentoRecebimento;
import com.confianca.repositories.CondicaoPagamentoRecebimentoRepository;
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
public class CondicaoPagamentoRecebimentoService {

    @Autowired
    private CondicaoPagamentoRecebimentoRepository repo;

    @Autowired
    private HistoricoService historicoService;

    public CondicaoPagamentoRecebimento find(Integer id){
        Optional<CondicaoPagamentoRecebimento> obj = repo.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: " + CondicaoPagamentoRecebimento.class.getName()));
    }

    public CondicaoPagamentoRecebimento insert(CondicaoPagamentoRecebimento obj){
        obj.setId(null);
        obj = repo.save(obj);
        historicoService.insertAny(obj, "Inserir");
        return obj;
    }

    public CondicaoPagamentoRecebimento update(CondicaoPagamentoRecebimento obj) {
        CondicaoPagamentoRecebimento newObj = find(obj.getId());
        updateData(newObj, obj);
        newObj = repo.save(newObj);
        historicoService.insertAny(obj, "Atualizar");
        return newObj;
    }

    public void delete(Integer id){
        find(id);
        try{
            repo.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possível excluir um cartão que possui lançamentos");
        }
    }

    public List<CondicaoPagamentoRecebimento> findAll(){
        return repo.findAll();
    }

    public Page<CondicaoPagamentoRecebimento> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(CondicaoPagamentoRecebimento newObj, CondicaoPagamentoRecebimento obj) {
        newObj.setBandeira(obj.getBandeira());
        newObj.setStatus(obj.getStatus());
        newObj.setNome(obj.getNome());
        newObj.setPercentualEntrada(obj.getPercentualEntrada());
        newObj.setQtdParcelas(obj.getQtdParcelas());
        newObj.setValorEntrada(obj.getValorEntrada());
        newObj.setValorMinimo(obj.getValorMinimo());
        newObj.setTipo(obj.getTipo());
    }
}

package com.confianca.services;

import com.confianca.domain.TipoPagamentoRecebimento;
import com.confianca.repositories.TipoPagamentoRecebimentoRepository;
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
public class TipoPagamentoRecebimentoService {

    @Autowired
    private TipoPagamentoRecebimentoRepository repo;

    @Autowired
    private  HistoricoService historicoService;

    public TipoPagamentoRecebimento find(Integer id){
        Optional<TipoPagamentoRecebimento> obj = repo.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: " + TipoPagamentoRecebimento.class.getName()));
    }

    public TipoPagamentoRecebimento findByNome(String nome){
        Optional<TipoPagamentoRecebimento> obj = repo.findByNome(nome);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+nome+", Tipo: " + TipoPagamentoRecebimento.class.getName()));
    }

    public TipoPagamentoRecebimento insert(TipoPagamentoRecebimento obj){
        obj.setId(null);
        obj = repo.save(obj);
        historicoService.insertAny(obj, "Inserir");
        return obj;
    }

    public TipoPagamentoRecebimento update(TipoPagamentoRecebimento obj) {
        TipoPagamentoRecebimento newObj = find(obj.getId());
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

    public List<TipoPagamentoRecebimento> findAll(){
        return repo.findAll();
    }

    public Page<TipoPagamentoRecebimento> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(TipoPagamentoRecebimento newObj, TipoPagamentoRecebimento obj) {
        newObj.setNome(obj.getNome());
        newObj.setStatus(obj.getStatus());
    }
}

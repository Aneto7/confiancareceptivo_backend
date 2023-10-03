package com.confianca.services;

import com.confianca.domain.TipoFornecedor;
import com.confianca.repositories.TipoFornecedorRepository;
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
public class TipoFornecedorService {

    @Autowired
    private TipoFornecedorRepository repo;

    @Autowired
    private  HistoricoService historicoService;

    public TipoFornecedor find(Integer id){
        Optional<TipoFornecedor> obj = repo.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: " + TipoFornecedor.class.getName()));
    }

    public TipoFornecedor insert(TipoFornecedor obj){
        obj.setId(null);
        obj = repo.save(obj);
        historicoService.insertAny(obj, "Inserir");
        return obj;
    }

    public TipoFornecedor update(TipoFornecedor obj) {
        TipoFornecedor newObj = find(obj.getId());
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

    public List<TipoFornecedor> findAll(){
        return repo.findAll();
    }

    public Page<TipoFornecedor> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(TipoFornecedor newObj, TipoFornecedor obj) {
        newObj.setNome(obj.getNome());
        newObj.setStatus(obj.getStatus());
    }
}

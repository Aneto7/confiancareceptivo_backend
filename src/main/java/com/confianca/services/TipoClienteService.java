package com.confianca.services;

import com.confianca.domain.TipoCliente;
import com.confianca.repositories.TipoClienteRepository;
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
public class TipoClienteService {

    @Autowired
    private TipoClienteRepository repo;

    @Autowired
    private  HistoricoService historicoService;

    public TipoCliente find(Integer id){
        Optional<TipoCliente> obj = repo.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: " + TipoCliente.class.getName()));
    }

    public TipoCliente insert(TipoCliente obj){
        obj.setId(null);
        obj = repo.save(obj);
        historicoService.insertAny(obj, "Inserir");
        return obj;
    }

    public TipoCliente update(TipoCliente obj) {
        TipoCliente newObj = find(obj.getId());
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

    public List<TipoCliente> findAll(){
        return repo.findAll();
    }

    public Page<TipoCliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(TipoCliente newObj, TipoCliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setStatus(obj.getStatus());
        newObj.setDesconto(obj.getDesconto());
    }
}

package com.confianca.services;


import com.confianca.domain.Guia;
import com.confianca.domain.Receita;
import com.confianca.repositories.GuiaRepository;
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
public class GuiaService {

    @Autowired
    private GuiaRepository repo;

    @Autowired
    private  HistoricoService historicoService;

    public Guia find(Integer id){
        Optional<Guia> obj = repo.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: " + Guia.class.getName()));
    }

    public Guia insert(Guia obj){
        obj.setId(null);
        obj = repo.save(obj);
        historicoService.insertAny(obj, "Inserir");
        return obj;
    }

    public Guia update(Guia obj) {
        Guia newObj = find(obj.getId());
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

    public List<Guia> findAll(){
        return repo.findAll();
    }

    public Page<Guia> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(Guia newObj, Guia obj) {
        newObj.setNome(obj.getNome());
        newObj.setCelular(obj.getCelular());
        newObj.setEmail(obj.getEmail());
        newObj.setTelefone(obj.getTelefone());
        newObj.setStatus(obj.getStatus());
        newObj.setCpfOuCnpj(obj.getCpfOuCnpj());
        newObj.setObs(obj.getObs());
    }
}

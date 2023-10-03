package com.confianca.services;

import com.confianca.domain.Pais;
import com.confianca.repositories.PaisRepository;
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
public class PaisService {

    @Autowired
    private PaisRepository repo;

    public Pais find(Integer id){
        Optional<Pais> obj = repo.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: " + Pais.class.getName()));
    }

    public Pais insert(Pais obj){
        obj.setId(null);
        obj.setSigla(obj.getSigla().toUpperCase());
        return repo.save(obj);
    }

    public Pais update(Pais obj) {
        Pais newObj = find(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
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

    public List<Pais> findAll(){
        return repo.findAll();
    }

    public Page<Pais> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(Pais newObj, Pais obj) {
        newObj.setNome(obj.getNome());
        newObj.setSigla(obj.getSigla().toUpperCase());
    }
}

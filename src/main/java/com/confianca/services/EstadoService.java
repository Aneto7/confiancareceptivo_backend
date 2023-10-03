package com.confianca.services;

import com.confianca.domain.Estado;
import com.confianca.repositories.EstadoRepository;
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
public class EstadoService {

    @Autowired
    private EstadoRepository repo;

    public Estado find(Integer id){
        Optional<Estado> obj = repo.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: " + Estado.class.getName()));
    }

    public Estado insert(Estado obj){
        obj.setId(null);
        return repo.save(obj);
    }

    public Estado update(Estado obj) {
        Estado newObj = find(obj.getId());
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

    public List<Estado> findAll(){
        return repo.findAll();
    }

    public List<Estado> findByPaisId(Integer id){
        return repo.findByPaisId(id);
    }

    public Page<Estado> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(Estado newObj, Estado obj) {
        newObj.setNome(obj.getNome());
        newObj.setSigla(obj.getSigla());
        newObj.setPais(obj.getPais());
    }
}

package com.confianca.services;

import com.confianca.domain.Cidade;
import com.confianca.domain.Estado;
import com.confianca.repositories.CidadeRepository;
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
public class CidadeService {

    @Autowired
    private CidadeRepository repo;

    public Cidade find(Integer id){
        Optional<Cidade> obj = repo.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: " + Cidade.class.getName()));
    }

    public Cidade insert(Cidade obj){
        obj.setId(null);
        return repo.save(obj);
    }

    public Cidade update(Cidade obj) {
        Cidade newObj = find(obj.getId());
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

    public List<Cidade> findAll(){
        return repo.findAll();
    }

    public List<Cidade> findByEstadoId(Integer id){
        return repo.findByEstadoId(id);
    }

    public List<Cidade> findByEstadoPaisId(Integer id){
        return repo.findByEstadoPaisId(id);
    }

    public Cidade findByNome(String nome){
        Optional<Cidade> obj = repo.findByNome(nome);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+nome+", Tipo: " + Cidade.class.getName()));
    }

    public Page<Cidade> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(Cidade newObj, Cidade obj) {
        newObj.setNome(obj.getNome());
        newObj.setEstado(obj.getEstado());
    }
}

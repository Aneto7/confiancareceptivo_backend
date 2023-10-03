package com.confianca.services;

import com.confianca.domain.Meta;
import com.confianca.repositories.MetaRepository;
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
public class MetaService {

    @Autowired
    private MetaRepository repo;

    public Meta find(Integer id){
        Optional<Meta> obj = repo.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: " + Meta.class.getName()));
    }

    public Meta insert(Meta obj){
        obj.setId(null);
        return repo.save(obj);
    }

    public Meta update(Meta obj) {
        Meta newObj = find(obj.getId());
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

    public List<Meta> findAll(){
        return repo.findAll();
    }

    public Page<Meta> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(Meta newObj, Meta obj) {
        newObj.setNome(obj.getNome());
        newObj.setStatus(obj.getStatus());
        newObj.setDataFinal(obj.getDataFinal());
        newObj.setDataInicial(obj.getDataInicial());
        newObj.setPercentualAdicionalDeComissao(obj.getPercentualAdicionalDeComissao());
        newObj.setUnidade(obj.getUnidade());
        newObj.setValorMeta(obj.getValorMeta());
    }
}

package com.confianca.services;

import com.confianca.domain.TipoReceita;
import com.confianca.repositories.TipoReceitaRepository;
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
public class TipoReceitaService {

    @Autowired
    private TipoReceitaRepository repo;

    @Autowired
    private  HistoricoService historicoService;

    public TipoReceita find(Integer id){
        Optional<TipoReceita> obj = repo.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: " + TipoReceita.class.getName()));
    }

    public TipoReceita insert(TipoReceita obj){
        obj.setId(null);
        obj = repo.save(obj);
        historicoService.insertAny(obj, "Inserir");
        return obj;
    }

    public TipoReceita update(TipoReceita obj) {
        TipoReceita newObj = find(obj.getId());
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

    public List<TipoReceita> findAll(){
        return repo.findAll();
    }

    public Page<TipoReceita> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(TipoReceita newObj, TipoReceita obj) {
        newObj.setNome(obj.getNome());
        newObj.setStatus(obj.getStatus());
    }
}

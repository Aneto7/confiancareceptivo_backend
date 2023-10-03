package com.confianca.services;

import com.confianca.domain.Tarifario;
import com.confianca.repositories.TarifarioRepository;
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
public class TarifarioService {

    @Autowired
    private TarifarioRepository repo;

    @Autowired
    private  HistoricoService historicoService;

    public Tarifario find(Integer id){
        Optional<Tarifario> obj = repo.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: " + Tarifario.class.getName()));
    }

    public Tarifario insert(Tarifario obj){
        obj.setId(null);
        obj = repo.save(obj);
        historicoService.insertAny(obj, "Inserir");
        return obj;
    }

    public Tarifario update(Tarifario obj) {
        Tarifario newObj = find(obj.getId());
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

    public List<Tarifario> findAll(){
        return repo.findAll();
    }

    public Page<Tarifario> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(Tarifario newObj, Tarifario obj) {
        newObj.setNome(obj.getNome());
        newObj.setStatus(obj.getStatus());
        newObj.setObs(obj.getObs());
        newObj.setDescritivo(obj.getDescritivo());
        newObj.setItinerario(obj.getItinerario());
        newObj.setInformacoes(obj.getInformacoes());
    }
}

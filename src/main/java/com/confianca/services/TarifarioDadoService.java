package com.confianca.services;

import com.confianca.domain.TarifarioDado;
import com.confianca.repositories.TarifarioDadoRepository;
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
public class TarifarioDadoService {

    @Autowired
    private TarifarioDadoRepository repo;

    @Autowired
    private  HistoricoService historicoService;

    public TarifarioDado find(Integer id){
        Optional<TarifarioDado> obj = repo.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: " + TarifarioDado.class.getName()));
    }

    public TarifarioDado insert(TarifarioDado obj){
        obj.setId(null);
        obj = repo.save(obj);
        historicoService.insertAny(obj, "Inserir");
        return obj;
    }

    public TarifarioDado update(TarifarioDado obj) {
        TarifarioDado newObj = find(obj.getId());
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

    public List<TarifarioDado> findAll(){
        return repo.findAll();
    }

    public Page<TarifarioDado> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(TarifarioDado newObj, TarifarioDado obj) {
        newObj.setNome(obj.getNome());
        newObj.setQuantidade(obj.getQuantidade());
        newObj.setValor(obj.getValor());
        newObj.setStatus(obj.getStatus());
        newObj.setObs(obj.getObs());
    }
}

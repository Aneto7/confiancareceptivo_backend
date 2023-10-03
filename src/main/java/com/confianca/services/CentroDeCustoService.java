package com.confianca.services;

import com.confianca.domain.CentroDeCusto;
import com.confianca.domain.Historico;
import com.confianca.repositories.CentroDeCustoRepository;
import com.confianca.services.exeptions.DataIntegrityException;
import com.confianca.services.exeptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CentroDeCustoService {

    @Autowired
    private CentroDeCustoRepository repo;

    @Autowired
    private HistoricoService historicoService;

    public CentroDeCusto find(Integer id){
        Optional<CentroDeCusto> obj = repo.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: " + CentroDeCusto.class.getName()));
    }

    public CentroDeCusto findByNome(String nome){
        Optional<CentroDeCusto> obj = repo.findByNome(nome);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+nome+", Tipo: " + CentroDeCusto.class.getName()));
    }

    public CentroDeCusto insert(CentroDeCusto obj){
        obj.setId(null);
        obj = repo.save(obj);
        historicoService.insertAny(obj, "Inserir");
        return obj;
    }

    public CentroDeCusto update(CentroDeCusto obj) {
        CentroDeCusto newObj = find(obj.getId());
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

    public List<CentroDeCusto> findAll(){
        return repo.findAll();
    }

    public Page<CentroDeCusto> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(CentroDeCusto newObj, CentroDeCusto obj) {
        newObj.setNome(obj.getNome());
        newObj.setStatus(obj.getStatus());
        newObj.setObs(obj.getObs());
    }
}

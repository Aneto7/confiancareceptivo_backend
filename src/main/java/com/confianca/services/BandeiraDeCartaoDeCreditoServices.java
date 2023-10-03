package com.confianca.services;

import com.confianca.domain.BandeiraDeCartaoDeCredito;
import com.confianca.domain.Historico;
import com.confianca.repositories.BandeiraDeCartaoDeCreditoRepository;
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
public class BandeiraDeCartaoDeCreditoServices {

    @Autowired
    private BandeiraDeCartaoDeCreditoRepository repo;

    @Autowired
    private HistoricoService historicoService;

    public BandeiraDeCartaoDeCredito find(Integer id){
        Optional<BandeiraDeCartaoDeCredito> obj = repo.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: " + BandeiraDeCartaoDeCredito.class.getName()));
    }

    public BandeiraDeCartaoDeCredito insert(BandeiraDeCartaoDeCredito obj){
        obj.setId(null);
        obj = repo.save(obj);
        historicoService.insertAny(obj, "Inserir");
        return obj;
    }

    public BandeiraDeCartaoDeCredito update(BandeiraDeCartaoDeCredito obj) {
        BandeiraDeCartaoDeCredito newObj = find(obj.getId());
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

    public List<BandeiraDeCartaoDeCredito> findAll(){
        return repo.findAll();
    }

    public Page<BandeiraDeCartaoDeCredito> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(BandeiraDeCartaoDeCredito newObj, BandeiraDeCartaoDeCredito obj) {
        newObj.setNome(obj.getNome());
        newObj.setSigla(obj.getSigla());
        newObj.setStatus(obj.getStatus());
    }
}

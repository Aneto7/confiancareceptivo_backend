package com.confianca.services;

import com.confianca.domain.DataTarifa;
import com.confianca.repositories.DataTarifaRepository;
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
public class DataTarifaService {

    @Autowired
    private DataTarifaRepository repo;

    public DataTarifa find(Integer id){
        Optional<DataTarifa> obj = repo.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: " + DataTarifa.class.getName()));
    }

    public DataTarifa findByData(Date dataBusca){
        Optional<DataTarifa> obj = repo.findByData(dataBusca);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Data: "+dataBusca+", Tipo: " + DataTarifa.class.getName()));
    }

    public DataTarifa insert(DataTarifa obj){
        obj.setId(null);
        return repo.save(obj);
    }

    public DataTarifa update(DataTarifa obj) {
        DataTarifa newObj = find(obj.getId());
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

    public List<DataTarifa> findAll(){
        return repo.findAll();
    }

    public Page<DataTarifa> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(DataTarifa newObj, DataTarifa obj) {
        newObj.setData(obj.getData());
    }
}

package com.confianca.services;

import com.confianca.domain.OpcionalVendido;
import com.confianca.repositories.OpcionalVendidoRepository;
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
public class OpcionalVendidoService {

    @Autowired
    private OpcionalVendidoRepository repo;

    @Autowired
    private  HistoricoService historicoService;

    public OpcionalVendido find(Integer id){
        Optional<OpcionalVendido> obj = repo.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: " + OpcionalVendido.class.getName()));
    }

    public OpcionalVendido insert(OpcionalVendido obj){
        obj.setId(null);
        obj = repo.save(obj);
        historicoService.insertAny(obj, "Inserir");
        return obj;
    }

    public OpcionalVendido update(OpcionalVendido obj) {
        OpcionalVendido newObj = find(obj.getId());
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

    public List<OpcionalVendido> findAll(){
        return repo.findAll();
    }

    public Page<OpcionalVendido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(OpcionalVendido newObj, OpcionalVendido obj) {
        newObj.setNome(obj.getNome());
        newObj.setStatus(obj.getStatus());
        newObj.setValor(obj.getValor());
        newObj.setQuantidade(obj.getQuantidade());
    }
}

package com.confianca.services;

import com.confianca.domain.Cidade;
import com.confianca.domain.DetalheProduto;
import com.confianca.repositories.DetalheProdutoRepository;
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
public class DetalheProdutoService {

    @Autowired
    private DetalheProdutoRepository repo;

    @Autowired
    private  HistoricoService historicoService;

    public DetalheProduto find(Integer id){
        Optional<DetalheProduto> obj = repo.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: " + DetalheProduto.class.getName()));
    }

    public DetalheProduto insert(DetalheProduto obj){
        obj.setId(null);
        obj = repo.save(obj);
        historicoService.insertAny(obj, "Inserir");
        System.out.println("Imprimir detrtalhe do produto ");
        return obj;
    }

    public DetalheProduto update(DetalheProduto obj) {
        DetalheProduto newObj = find(obj.getId());
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

    public List<DetalheProduto> findAll(){
        return repo.findAll();
    }

    public List<DetalheProduto> findByProdutoId(Integer id){
        return repo.findByProdutoId(id);
    }

    public Page<DetalheProduto> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(DetalheProduto newObj, DetalheProduto obj) {
        newObj.setNome(obj.getNome());
        newObj.setStatus(obj.getStatus());
        newObj.setProduto(obj.getProduto());
        newObj.setDescricao(obj.getDescricao());
    }
}

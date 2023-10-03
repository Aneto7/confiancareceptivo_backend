package com.confianca.services;


import com.confianca.domain.Fornecedor;
import com.confianca.repositories.FornecedorRepository;
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
public class FornecedorService {

    @Autowired
    private FornecedorRepository repo;

    @Autowired
    private  HistoricoService historicoService;

    public Fornecedor find(Integer id){
        Optional<Fornecedor> obj = repo.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: " + Fornecedor.class.getName()));
    }

    public Fornecedor insert(Fornecedor obj){
        obj.setId(null);
        obj = repo.save(obj);
        historicoService.insertAny(obj, "Inserir");
        return obj;
    }

    public Fornecedor update(Fornecedor obj) {
        Fornecedor newObj = find(obj.getId());
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

    public List<Fornecedor> findAll(){
        return repo.findAll();
    }

    public Page<Fornecedor> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(Fornecedor newObj, Fornecedor obj) {
        newObj.setCidade(obj.getCidade());
        newObj.setTipoDespesa(obj.getTipoDespesa());
        newObj.setTipoFornecedor(obj.getTipoFornecedor());
        newObj.setNome(obj.getNome());
        newObj.setRazaoSocial(obj.getRazaoSocial());
        newObj.setCnpj(obj.getCnpj());
        newObj.setInscricaoEstadual(obj.getInscricaoEstadual());
        newObj.setResponsavel(obj.getResponsavel());
        newObj.setTelefoneComercial(obj.getTelefoneComercial());
        newObj.setCelular(obj.getCelular());
        newObj.setEmail(obj.getEmail());
        newObj.setCep(obj.getCep());
        newObj.setBairro(obj.getBairro());
        newObj.setEndereco(obj.getEndereco());
        newObj.setStatus(obj.getStatus());
        newObj.setObservacao(obj.getObservacao());
    }
}

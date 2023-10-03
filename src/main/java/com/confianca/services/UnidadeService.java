package com.confianca.services;

import com.confianca.domain.Unidade;
import com.confianca.repositories.UnidadeRepository;
import com.confianca.services.exeptions.DataIntegrityException;
import com.confianca.services.exeptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnidadeService {

    @Autowired
    private UnidadeRepository repo;

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private  HistoricoService historicoService;


    public Unidade find(Integer id){
        Optional<Unidade> obj = repo.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: " + Unidade.class.getName()));
    }

    public Unidade insert(Unidade obj){
        obj.setId(null);
        obj.setSenha(pe.encode(obj.getSenha()));
        obj = repo.save(obj);
        historicoService.insertAny(obj, "Inserir");
        return obj;
    }

    public Unidade update(Unidade obj) {
        Unidade newObj = find(obj.getId());
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

    public List<Unidade> findAll(){
        return repo.findAll();
    }

    public Page<Unidade> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(Unidade newObj, Unidade obj) {
        newObj.setNome(obj.getNome());
        newObj.setRazaoSocial(obj.getRazaoSocial());
        newObj.setTelefone(obj.getTelefone());
        newObj.setEndereco(obj.getEndereco());
        newObj.setCep(obj.getCep());
        newObj.setCpfOuCnpj(obj.getCpfOuCnpj());
        newObj.setNomeResponsavel(obj.getNomeResponsavel());
        newObj.setEmail(obj.getEmail());
        newObj.setSenha(obj.getSenha());
        newObj.setStatus(obj.getStatus());
        newObj.setObs(obj.getObs());
        newObj.setTelefonePlantao(obj.getTelefonePlantao());
        newObj.setCidade(obj.getCidade());
    }
}

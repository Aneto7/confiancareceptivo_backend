package com.confianca.services;

import com.confianca.domain.DiaDoAno;
import com.confianca.repositories.DiaDoAnoRepository;
import com.confianca.services.exeptions.DataIntegrityException;
import com.confianca.services.exeptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DiaDoAnoService {

    @Autowired
    private DiaDoAnoRepository repo;

    public DiaDoAno find(Integer id){
        Optional<DiaDoAno> obj = repo.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: " + DiaDoAno.class.getName()));
    }

    public DiaDoAno insert(DiaDoAno obj){
        obj.setId(null);
        return repo.save(obj);
    }

    public DiaDoAno insertPeriodo(String dataInico, String dataFim){

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        DiaDoAno obj = new DiaDoAno();

        try {
            Date inicio = sdf.parse(dataInico);
            Date fim = sdf.parse(dataFim);
            fim.setDate(fim.getDate() + 1);
            Date dataCadastro = inicio;
            DiaDoAno objCadastro = new DiaDoAno();

            for (int i = 0; dataCadastro.before(fim); i++){
                DiaDoAno consultaDiaDoAno = repo.findByDiaDoAno(dataCadastro);

                if (consultaDiaDoAno == null){
                    System.out.println("Cadastar novo: " + dataCadastro);
                    objCadastro.setDiaDoAno(dataCadastro);
                    objCadastro.setStatus("Ativo");
                } else {
                    System.out.println("Não cadastrar: " + dataCadastro);
                }
                dataCadastro.setDate(dataCadastro.getDate() + 1);
                System.out.println("Novo data de cadastro"+dataCadastro);
                objCadastro = repo.save(objCadastro);
            }
        return obj;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public DiaDoAno update(DiaDoAno obj) {
        DiaDoAno newObj = find(obj.getId());
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

    public List<DiaDoAno> findAll(){
        return repo.findAll();
    }

    public Page<DiaDoAno> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(DiaDoAno newObj, DiaDoAno obj) {
        newObj.setDiaDoAno(obj.getDiaDoAno());
        newObj.setStatus(obj.getStatus());
    }
}

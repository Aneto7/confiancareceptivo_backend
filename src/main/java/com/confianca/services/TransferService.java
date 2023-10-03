package com.confianca.services;

import com.confianca.domain.Despesa;
import com.confianca.domain.Transfer;
import com.confianca.repositories.TransferRepository;
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
import java.util.*;

@Service
public class  TransferService {

    @Autowired
    private TransferRepository repo;

    @Autowired
    private  HistoricoService historicoService;

    public Transfer find(Integer id){
        Optional<Transfer> obj = repo.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+ id +", Tipo: " + Transfer.class.getName()));
    }

    public Transfer findByServicoId(Integer id){
        Optional<Transfer> obj = repo.findByServicoId(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+ id +", Tipo: " + Transfer.class.getName()));
    }

    public List<Transfer> findByDiahoraBetween(String dataI, String dataF) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dataInicial = formatter.parse(dataI);
        Date dataFinal = formatter.parse(dataF);
        List<Transfer> objTeste = repo.findByDiahoraBetween(dataInicial, dataFinal);
        return objTeste;
    }

    public Transfer insert(Transfer obj){
        System.out.println("Imprindo criação de transfer");
        obj.setId(null);
        obj.setEmissao(new Date());
        obj = repo.save(obj);
        historicoService.insertAny(obj, "Inserir");
        return obj;
    }

    public Transfer update(Transfer obj) {
        Transfer newObj = find(obj.getId());
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

    public List<Transfer> findAll(){
        return repo.findAll();
    }

    public Page<Transfer> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(Transfer newObj, Transfer obj) {
        newObj.setFornecedor(obj.getFornecedor());
        newObj.setPontoDeSaida(obj.getPontoDeSaida());
        newObj.setDiahora(obj.getDiahora());
        newObj.setVeiculo(obj.getVeiculo());
        newObj.setCondutor(obj.getCondutor());
        newObj.setRota(obj.getRota());
        newObj.setDestino(obj.getDestino());
        newObj.setPax(obj.getPax());
        newObj.setCliente(obj.getCliente());
        newObj.setMalai(obj.getMalai());
        newObj.setStatus(obj.getStatus());
        newObj.setTelefone(obj.getTelefone());
    }
}

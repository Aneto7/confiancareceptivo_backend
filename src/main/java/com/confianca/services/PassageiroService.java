package com.confianca.services;

import com.confianca.domain.Passageiro;
import com.confianca.domain.Venda;
import com.confianca.dto.PassageiroConsultaDTO;
import com.confianca.dto.StatusDTO;
import com.confianca.repositories.PassageiroRepository;
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
public class PassageiroService {

    @Autowired
    private PassageiroRepository repo;

    @Autowired
    private  HistoricoService historicoService;

    public Passageiro find(Integer id){
        Optional<Passageiro> obj = repo.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: " + Passageiro.class.getName()));
    }

    public Passageiro insert(Passageiro obj){
        obj.setId(null);
        obj = repo.save(obj);
        historicoService.insertAny(obj, "Inserir");
        return obj;
    }

    public Passageiro update(Passageiro obj) {
        Passageiro newObj = find(obj.getId());
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

    public List<Passageiro> findAll(){
        return repo.findAll();
    }

    public List<Passageiro> findByProdutoVendidoVendaId(Integer id){
        return repo.findByProdutoVendidoVendaId(id);
    }

    public List<Passageiro> findByProdutoVendidoServicoData(Date dataIn){
        return repo.findByProdutoVendidoServicoData(dataIn);
    }

    public List<Passageiro> findByProdutoVendidoServicoDataBetween(Date dataIn, Date dataFi){
        return repo.findByProdutoVendidoServicoDataBetween(dataIn, dataFi);
    }

    public List<PassageiroConsultaDTO> findByDataStatus(String status, String dataI, String dataF) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dataInicial = formatter.parse(dataI);
        Date dataFinal = formatter.parse(dataF);
        return repo.findByStatusData(status, dataInicial, dataFinal);
    }

    public List<PassageiroConsultaDTO> findByData(String dataI, String dataF) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dataInicial = formatter.parse(dataI);
        Date dataFinal = formatter.parse(dataF);
        return repo.findByData(dataInicial, dataFinal);
    }

    public List<StatusDTO> findStatus() {
        return repo.findStatus();
    }

    public Page<Passageiro> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(Passageiro newObj, Passageiro obj) {
        //newObj.setProdutoVendido(obj.getProdutoVendido());
        newObj.setNome(obj.getNome());
        newObj.setNascimento(obj.getNascimento());
        newObj.setCpf(obj.getCpf());
        newObj.setTelefone(obj.getTelefone());
        newObj.setCelular(obj.getCelular());
        newObj.setEmail(obj.getEmail());
        newObj.setStatus(obj.getStatus());
        newObj.setObs(obj.getObs());
    }
}

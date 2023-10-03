package com.confianca.services;

import com.confianca.domain.Cupom;
import com.confianca.domain.ProdutoVendido;
import com.confianca.domain.Venda;
import com.confianca.repositories.CupomRepository;
import com.confianca.services.exeptions.DataIntegrityException;
import com.confianca.services.exeptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CupomService {

    @Autowired
    private CupomRepository repo;

    @Autowired
    private  HistoricoService historicoService;

    public Cupom find(Integer id){
        Optional<Cupom> obj = repo.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: " + Cupom.class.getName()));
    }

    public Cupom insert(Cupom obj){
        obj.setId(null);
        obj = repo.save(obj);
        historicoService.insertAny(obj, "Inserir");
        return obj;
    }

    public Cupom update(Cupom obj) {
        Cupom newObj = find(obj.getId());
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

    public List<Cupom> findAll(){
        return repo.findAll();
    }

    public Page<Cupom> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(Cupom newObj, Cupom obj) {
        newObj.setNome(obj.getNome());
        newObj.setStatus(obj.getStatus());
        newObj.setObs(obj.getObs());
        newObj.setValorDesconto(obj.getValorDesconto());
    }

    public Double valorCupom(Venda obj, ProdutoVendido prod){
        Double cupomValor = 0.0;
        List<Double> valores = new ArrayList<>();

        if (prod.getProduto().getCupom() != null) {
            //cupomProduto = prod.getProduto().getCupom().getValorDesconto();
            valores.add(prod.getProduto().getCupom().getValorDesconto());
        }
        if (prod.getTarifa().getCupom() != null) {
            //cupomTarifa = prod.getTarifa().getCupom().getValorDesconto();
            valores.add(prod.getTarifa().getCupom().getValorDesconto());
        }
        if (obj.getCliente().getCupom() != null) {
            //cupomCliente = obj.getCliente().getCupom().getValorDesconto();
            valores.add(obj.getCliente().getCupom().getValorDesconto());
        }

        System.out.println("imprindo valores: " + valores.size());

        if(valores.size() > 0){
            cupomValor = Collections.max(valores);
        }
        return cupomValor;
    }
}

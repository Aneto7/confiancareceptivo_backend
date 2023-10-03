package com.confianca.services;

import com.confianca.domain.Despesa;
import com.confianca.domain.Pagamento;
import com.confianca.domain.Receita;
import com.confianca.dto.PagamentoDTO;
import com.confianca.dto.StatusDTO;
import com.confianca.repositories.DespesaRepository;
import com.confianca.repositories.PagamentoRepository;
import com.confianca.services.exeptions.DataIntegrityException;
import com.confianca.services.exeptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository repo;
    @Autowired
    private HistoricoService historicoService;

    @Autowired
    private DespesaRepository despesaRepository;

    public Pagamento find(Integer id) {
        Optional<Pagamento> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pagamento.class.getName()));
    }

    public List<Pagamento> findByVenda(Integer id) {
        List<Pagamento> objTeste = repo.findByVendaId(id);
        return objTeste;
    }

    public List<Pagamento> findByVencimentoBetween(String dataI, String dataF) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dataInicial = formatter.parse(dataI);
        Date dataFinal = formatter.parse(dataF);
        List<Pagamento> objTeste = repo.findByVencimentoBetween(dataInicial, dataFinal);
        return objTeste;
    }

    public List<PagamentoDTO> findByPagamentoAcumulada(Integer ano) throws ParseException {
        List<PagamentoDTO> objTeste = repo.findByPagamentoAcumulada(ano);
        return objTeste;
    }

    public Pagamento insert(Pagamento obj) {
        obj.setId(null);
        obj = repo.save(obj);
        historicoService.insertAny(obj, "Inserir");
        return obj;
    }

    public Pagamento update(Pagamento obj, Boolean lancamentos) throws IOException {
        Pagamento newObj = find(obj.getId());
        updateData(newObj, obj);
        newObj = repo.save(newObj);
        if (lancamentos) {
            Despesa despesa = newObj.getDespesa();
            despesa.setStatus(obj.getStatus());
            despesaRepository.save(despesa);
        }
        historicoService.insertAny(obj, "Atualizar");
        return newObj;
    }

    public void delete(Integer id) {
        find(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um cartão que possui lançamentos");
        }
    }

    public List<Pagamento> findAll() {
        return repo.findAll();
    }

    public List<StatusDTO> findStatus(){
        return repo.findStatus();
    }

    public Page<Pagamento> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(Pagamento newObj, Pagamento obj) {
        newObj.setTipo(obj.getTipo());
        newObj.setVenda(obj.getVenda());
        newObj.setNome(obj.getNome());
        newObj.setValor(obj.getValor());
        newObj.setPagamentoData(obj.getPagamentoData());
        newObj.setVencimento(obj.getVencimento());
        newObj.setParcelado(obj.getParcelado());
        newObj.setQuantidadeParcelas(obj.getQuantidadeParcelas());
        newObj.setnParcelas(obj.getnParcelas());
        newObj.setStatus(obj.getStatus());
    }
}

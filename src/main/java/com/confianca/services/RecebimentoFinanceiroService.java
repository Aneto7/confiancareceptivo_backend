package com.confianca.services;

import com.confianca.cielo.Merchant;
import com.confianca.cielo.ecommerce.*;
import com.confianca.cielo.ecommerce.request.CieloRequestException;
import com.confianca.domain.Recebimento;
import com.confianca.domain.RecebimentoFinanceiro;
import com.confianca.domain.Receita;
import com.confianca.dto.RecebimentoDTO;
import com.confianca.dto.StatusDTO;
import com.confianca.repositories.RecebimentoFinanceiroRepository;
import com.confianca.repositories.RecebimentoRepository;
import com.confianca.repositories.ReceitaRepository;
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
import java.text.Format;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class RecebimentoFinanceiroService {

    @Value("${cielo.id}")
    private String merchantId;

    @Value("${cielo.key}")
    private String merchantKey;

    @Autowired
    private RecebimentoFinanceiroRepository repo;

    @Autowired
    private HistoricoService historicoService;

    @Autowired
    private RecebimentoRepository recebimentoRepository;

    @Autowired
    private ReceitaRepository receitaRepository;


    public RecebimentoFinanceiro find(Integer id) {
        Optional<RecebimentoFinanceiro> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + RecebimentoFinanceiro.class.getName()));
    }

    public List<RecebimentoFinanceiro> findByVenda(Integer id) {
        List<RecebimentoFinanceiro> objTeste = repo.findByVendaId(id);
        return objTeste;
    }

    public List<RecebimentoFinanceiro> findByVencimentoBetween(String dataI, String dataF) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dataInicial = formatter.parse(dataI);
        Date dataFinal = formatter.parse(dataF);
        List<RecebimentoFinanceiro> objTeste = repo.findByVencimentoBetween(dataInicial, dataFinal);
        return objTeste;
    }

    public List<RecebimentoDTO> findByRecebimentoFinanceiroAcumulado(Integer ano) throws ParseException {
        List<RecebimentoDTO> objTeste = repo.findByRecebimentoFinanceiroAcumulado(ano);
        return objTeste;
    }

    public RecebimentoFinanceiro insert(RecebimentoFinanceiro obj) {
        obj.setId(null);
        obj = repo.save(obj);
        historicoService.insertAny(obj, "Inserir");
        return obj;
    }

    public RecebimentoFinanceiro update(RecebimentoFinanceiro obj, Boolean lancamentos) throws CieloRequestException, IOException {
        RecebimentoFinanceiro newObj = find(obj.getId());
        if (obj.getStatus().equals("Cancelado") && obj.getIdCartao() != null) {
            cancelarRecebimentoCartao(obj);
        }
        updateData(newObj, obj);
        newObj = repo.save(newObj);
        if(lancamentos){
            Optional<Recebimento> recebimento = recebimentoRepository.findById(newObj.getRecebimento().getId());
            recebimento.get().setStatus(obj.getStatus());
            recebimentoRepository.save(recebimento.get());
            if(recebimento.isPresent()) {
                for (Receita receita : recebimento.get().getReceitas()) {
                    receita.setStatus(obj.getStatus());
                    receitaRepository.save(receita);
                }
            }
        }
        historicoService.insertAny(obj, "Atualizar");
        return newObj;
    }

    public void cancelarRecebimentoCartao(RecebimentoFinanceiro obj) throws CieloRequestException, IOException {

        // E também podemos fazer seu cancelamento, se for o caso
        String valorString = String.format("%.2f", obj.getValorFinal());
        valorString = valorString.replace(",", "");
        int valorInt = Integer.parseInt(valorString);
        String paymentId = obj.getIdCartao();
        Merchant merchant = new Merchant(merchantId, merchantKey);
        SaleResponse sale = new CieloEcommerce(merchant, Environment.SANDBOX).cancelSale(paymentId, valorInt);
        System.out.println("Imprimindo resposta de cancelamento de venda: " + sale.getReturnMessage());
    }

    public void delete(Integer id) {
        find(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um cartão que possui lançamentos");
        }
    }

    public List<RecebimentoFinanceiro> findAll() {
        return repo.findAll();
    }

    public List<StatusDTO> findStatus(){
        return repo.findStatus();
    }

    public Page<RecebimentoFinanceiro> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(RecebimentoFinanceiro newObj, RecebimentoFinanceiro obj) {
        newObj.setTipo(obj.getTipo());
        newObj.setVenda(obj.getVenda());
        newObj.setNome(obj.getNome());
        newObj.setValor(obj.getValor());
        newObj.setRecebimentoData(obj.getRecebimentoData());
        newObj.setVencimento(obj.getVencimento());
        newObj.setParcelado(obj.getParcelado());
        newObj.setQuantidadeParcelas(obj.getQuantidadeParcelas());
        newObj.setnParcelas(obj.getnParcelas());
        newObj.setStatus(obj.getStatus());
    }
}

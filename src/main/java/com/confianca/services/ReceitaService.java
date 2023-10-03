package com.confianca.services;

import com.confianca.domain.*;
import com.confianca.dto.ReceitaDTO;
import com.confianca.dto.ReceitaReciboDTO;
import com.confianca.dto.StatusDTO;
import com.confianca.repositories.RecebimentoFinanceiroRepository;
import com.confianca.repositories.RecebimentoRepository;
import com.confianca.repositories.ReceitaRepository;
import com.confianca.services.exeptions.DataIntegrityException;
import com.confianca.services.exeptions.ObjectNotFoundException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository repo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private HistoricoService historicoService;

    @Autowired
    private TransferService transferService;

    @Autowired
    private RecebimentoRepository recebimentoRepository;

    @Autowired
    private RecebimentoFinanceiroRepository recebimentoFinanceiroRepository;

    public Receita find(Integer id) {
        Optional<Receita> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Receita.class.getName()));
    }

    public List<Receita> findByServico(Integer id) {
        List<Receita> objTeste = repo.findByServicoId(id);
        return objTeste;
    }

    public List<Receita> findByProdutoVendido(Integer id) {
        List<Receita> objTeste = repo.findByProdutoVendidoId(id);
        return objTeste;
    }

    public List<Receita> findByVenda(Integer id) {
        List<Receita> objTeste = repo.findByVendaId(id);
        return objTeste;
    }

    public List<Receita> findByCompetenciaBetween(String dataI, String dataF) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dataInicial = formatter.parse(dataI);
        Date dataFinal = formatter.parse(dataF);
        List<Receita> objTeste = repo.findByCompetenciaBetween(dataInicial, dataFinal);
        return objTeste;
    }

    public List<Receita> findByVencimentoBetween(String dataI, String dataF) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dataInicial = formatter.parse(dataI);
        Date dataFinal = formatter.parse(dataF);
        List<Receita> objTeste = repo.findByVencimentoBetween(dataInicial, dataFinal);
        return objTeste;
    }

    public List<Receita> findByEmissaoBetween(String dataI, String dataF) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        Date dataInicial = formatter.parse(dataI);
        Date dataFinal = formatter.parse(dataF);
        List<Receita> objTeste = repo.findByEmissaoBetween(dataInicial, dataFinal);
        return objTeste;
    }

    public List<ReceitaDTO> findByReceitaAcumulada(Integer ano) throws ParseException {
        List<ReceitaDTO> objTeste = repo.findByReceitaAcumulada(ano);
        return objTeste;
    }

    public List<Receita> findByVencimentoTipoPagamento(String dataI, String dataF, String tipoPagamento) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dataInicial = formatter.parse(dataI);
        Date dataFinal = formatter.parse(dataF);
        List<Receita> receitaList = repo.findByVencimentoBetweenAndTipoPagamentoRecebimentoNomeContains(dataInicial, dataFinal, tipoPagamento);
        return receitaList;
    }

    public List<Receita> findByCompetenciaTipoPagamento(String dataI, String dataF, String tipoPagamento) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dataInicial = formatter.parse(dataI);
        Date dataFinal = formatter.parse(dataF);
        List<Receita> receitaList = repo.findByCompetenciaBetweenAndTipoPagamentoRecebimentoNomeContains(dataInicial, dataFinal, tipoPagamento);
        return receitaList;
    }

    public Receita insert(Receita obj) {
        int i = 1;
        Recebimento recebimentoVenda = new Recebimento(null, obj.getTipoPagamentoRecebimento(), null,
                null, obj.getNome(), obj.getValor(), null, obj.getVencimento(),
                "Não", obj.getQuantidadeParcelas(), 1, obj.getStatus(), new Date());
        recebimentoVenda = recebimentoRepository.save(recebimentoVenda);

        //CRIAR UM RECEBIMENTO FINANCEIRO PARA CADA PARCELA QUANDO FOR CARTÃO DE CRÉDITO E FAZER O CÁLCULO COM DESCONTOS
        // E VALOR REAL A SER RECEBIDO, ISSO PODERÁ SER MANIPULADO NO ADMINISTADOR

        Date data = null;
        String nome = "Avulso";
        if (obj.getServico() != null) {
            data = obj.getServico().getDataIn();
            nome = obj.getServico().getNome();
        } else {
            data = new Date();
        }
        obj.setId(null);

        //ALTERAR VALORES QUANDO TIVER LANÇAMENTO DE PARCELAS A CRIAÇAO DA RECEITA
        obj.setValorFinal(obj.getValor());
        obj.setValorTotal(obj.getValor());
        obj.setRecebimento(recebimentoVenda);
        obj.setEmissao(new Date());

        obj = repo.save(obj);

        String cliente = "";

        if (data == null) {
            data = obj.getServico().getData();
        }
        if (obj.getTipoProduto().getPlanilha()) {
            if (obj.getCliente() != null) {
                cliente = obj.getCliente().getNome();
            }
            Transfer transfer = new Transfer(null, null, null, obj.getServico(), obj.getNome() + " - " + nome,
                    data, null, null, null, null, cliente, cliente, false, "Ativo", null, new Date());
            transferService.insert(transfer);
        }

        while (i <= obj.getQuantidadeParcelas()) {
            Calendar cal = Calendar.getInstance();
            cal.set(obj.getVencimento().getYear() + 1900, obj.getVencimento().getMonth(), obj.getVencimento().getDate());
            cal.add(Calendar.MONTH, i - 1);
            Date dataN = cal.getTime();

            Double valorParcela = obj.getValorFinal() / obj.getQuantidadeParcelas();
            RecebimentoFinanceiro recebimentoFinanceiro = new RecebimentoFinanceiro(null, obj.getCentroDeCusto(), obj.getCliente(),
                    obj.getTipoPagamentoRecebimento(), obj.getTipoReceita(), null, recebimentoVenda, null,
                    obj.getNome(), valorParcela, 0.0, 0.0, valorParcela,
                    obj.getVencimento(), dataN, "Não",
                    obj.getQuantidadeParcelas(), i, obj.getStatus(), new Date());
            recebimentoFinanceiroRepository.save(recebimentoFinanceiro);
            historicoService.insertAny(recebimentoFinanceiro, "Inserir");
            i++;
        }
        historicoService.insertAny(obj, "Inserir");
        return obj;
    }

    public Receita insertVenda(Receita obj) {
        Date data = null;
        String nome = "Avulso";
        if (obj.getServico() != null) {
            data = obj.getServico().getDataIn();
            nome = obj.getServico().getNome();
        } else {
            data = new Date();
        }
        obj.setId(null);
        obj.setEmissao(new Date());
        obj = repo.save(obj);
        if (data == null) {
            data = obj.getServico().getData();
        }
//        if (obj.getTipoProduto().getPlanilha()) {
//            Transfer transfer = new Transfer(null, null, null, obj.getServico(), obj.getNome() + " - " + nome,
//                    data, null, null, null, null, obj.getCliente().getNome(), obj.getCliente().getNome(), false, "Ativo");
//            transferService.insert(transfer);
//        }
        historicoService.insertAny(obj, "Inserir");
        return obj;
    }

    public Receita update(Receita obj, Boolean recebimentos) {
        Receita newObj = find(obj.getId());
        updateData(newObj, obj);
        newObj = repo.save(newObj);
        if (recebimentos) {
            Recebimento recebimento = newObj.getRecebimento();
            recebimento.setStatus(obj.getStatus());
            recebimento.setValor(obj.getValorFinal());
            recebimentoRepository.save(recebimento);
            for (RecebimentoFinanceiro recebimentoFinanceiro : newObj.getRecebimento().getRecebimentoFinanceiros()) {
                recebimentoFinanceiro.setStatus(obj.getStatus());
                recebimentoFinanceiro.setValor(obj.getValor() / obj.getQuantidadeParcelas());
                recebimentoFinanceiro.setValorFinal(obj.getValorFinal() / obj.getQuantidadeParcelas());
                recebimentoFinanceiroRepository.save(recebimentoFinanceiro);
            }
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

    public List<Receita> findAll() {
        return repo.findAll();
    }

    public List<StatusDTO> findStatus() {
        return repo.findStatus();
    }

    public Page<Receita> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(Receita newObj, Receita obj) {
        //newObj.setVenda(obj.getVenda());
        newObj.setServico(obj.getServico());
        newObj.setCentroDeCusto(obj.getCentroDeCusto());
        newObj.setTipoReceita(obj.getTipoReceita());
        newObj.setCliente(obj.getCliente());
        newObj.setTipoProduto(obj.getTipoProduto());
        //newObj.setRecebimento(obj.getRecebimento());
        newObj.setNome(obj.getNome());
        newObj.setValor(obj.getValor());
        newObj.setTaxa(obj.getTaxa());
        newObj.setValorFinal(obj.getValorFinal());
        newObj.setQuantidadeParcelas(obj.getQuantidadeParcelas());
        newObj.setStatus(obj.getStatus());
        newObj.setVencimento(obj.getVencimento());
        newObj.setDataRecebimento(obj.getDataRecebimento());
        newObj.setCompetencia(obj.getCompetencia());
        newObj.setPendente(obj.getPendente());
        newObj.setObs(obj.getObs());
        newObj.setTipoPagamentoRecebimento(obj.getTipoPagamentoRecebimento());
        newObj.setCompetencia(obj.getCompetencia());

    }

    public ResponseEntity recibo(Integer idReceita) throws IOException, JRException {
        List<ReceitaReciboDTO> receita = new ArrayList<>();
        Optional<Receita> receitaBase = repo.findById(idReceita);

        ReceitaReciboDTO receitaReciboDTO = new ReceitaReciboDTO(receitaBase.get());
        receita.add(receitaReciboDTO);

//        if(receitaBase.isPresent()){
//            if(receitaBase.get().getCliente() != null){
//                receita.get(0).setClienteNome(receitaBase.get().getCliente().getNome());
//                receita.get(0).setClienteCpfOuCnpj(receitaBase.get().getCliente().getCnpjOuCpf());
//            } else if(receitaBase.get().getServico().getNomeCliente() != null){
//                receita.get(0).setClienteNome(receitaBase.get().getServico().getNomeCliente());
//            }
//
//            if(receitaBase.get().getServico() != null){
//                receita.get(0).setServicoNome(receitaBase.get().getServico().getNome());
//                receita.get(0).setIdentificador(receitaBase.get().getServico().getIdentificador());
//            }
//        }

        // configurando a fonte
        String fontPath = this.getClass().getResource("/fonts/arial.ttf").getPath();
        Font font = new Font("Arial", Font.BOLD, 10);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("REPORT_FONT", font);

        // compilando o relatório
        File file = ResourceUtils.getFile("classpath:ReciboReceita.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        // preenchendo os dados
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(receita);

        // gerando o relatório em memória
        System.out.println("Imprindo execução");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // exportando o relatório para PDF
        byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "relatorio.pdf");
        headers.setContentLength(pdf.length);

        return new ResponseEntity(pdf, headers, HttpStatus.OK);
    }
}
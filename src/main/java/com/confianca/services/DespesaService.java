package com.confianca.services;

import com.confianca.domain.*;
import com.confianca.dto.DespesaDTO;
import com.confianca.dto.DespesaReciboDTO;
import com.confianca.dto.StatusDTO;
import com.confianca.repositories.DespesaRepository;
import com.confianca.repositories.PagamentoRepository;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository repo;

    @Autowired
    private  HistoricoService historicoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public Despesa find(Integer id){
        Optional<Despesa> obj = repo.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: " + Despesa.class.getName()));
    }

    public List<Despesa> findByFornecedorId(Integer id) {
        List<Despesa> obj = repo.findByFornecedorId(id);
        return obj;
    }

    public List<Despesa> finByServico(Integer id){
        List<Despesa> objTeste = repo.findByServicoId(id);
        return objTeste;
    }

    public List<Despesa> findByVencimentoBetween(String dataI, String dataF) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dataInicial = formatter.parse(dataI);
        Date dataFinal = formatter.parse(dataF);
        List<Despesa> objTeste = repo.findByVencimentoBetween(dataInicial, dataFinal);
        return objTeste;
    }

    public List<Despesa> findByEmissaoBetween(String dataI, String dataF) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dataInicial = formatter.parse(dataI);
        Date dataFinal = formatter.parse(dataF);
        List<Despesa> objTeste = repo.findByEmissaoBetween(dataInicial, dataFinal);
        return objTeste;
    }

    public List<Despesa> findByFornecedorIdAndCompetenciaBetween(Integer id, String dataI, String dataF) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dataInicial = formatter.parse(dataI);
        Date dataFinal = formatter.parse(dataF);
        List<Despesa> objTeste = repo.findByFornecedorIdAndCompetenciaBetween(id, dataInicial, dataFinal);
        return objTeste;
    }

    public List<DespesaDTO> findByDespesaAcumulada(Integer ano) throws ParseException {
        List<DespesaDTO> objTeste = repo.findByDespesaAcumulada(ano);
        return objTeste;
    }

    public List<Despesa> findByVencimentoTipoPagamento(String dataI, String dataF, String tipoPagamento) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dataInicial = formatter.parse(dataI);
        Date dataFinal = formatter.parse(dataF);
        List<Despesa> despesaList = repo.findByVencimentoBetweenAndTipoPagamentoRecebimentoNomeContains(dataInicial, dataFinal, tipoPagamento);
        return despesaList;
    }

    public List<Despesa> findByCompetenciaTipoPagamento(String dataI, String dataF, String tipoPagamento) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dataInicial = formatter.parse(dataI);
        Date dataFinal = formatter.parse(dataF);
        List<Despesa> despesaList = repo.findByCompetenciaBetweenAndTipoPagamentoRecebimentoNomeContains(dataInicial, dataFinal, tipoPagamento);
        return despesaList;
    }

    public Despesa insert(Despesa obj){
        int i = 1;
        obj.setEmissao(new Date());
        obj = repo.save(obj);
        historicoService.insertAny(obj, "Inserir");

        while (i <= obj.getQuantidadeParcelas()) {
            Calendar cal = Calendar.getInstance();
            cal.set(obj.getVencimento().getYear() + 1900, obj.getVencimento().getMonth(),obj.getVencimento().getDate());
            cal.add(Calendar.MONTH, i - 1);
            Date data = cal.getTime();

            Double valorParcela = obj.getValor() / obj.getQuantidadeParcelas();
            Pagamento pagamento = new Pagamento(null, obj.getCentroDeCusto(), obj.getFornecedor(),
                    obj.getTipoPagamentoRecebimento(), obj.getTipoDespesa(), null, obj, null,
                    obj.getNome(), valorParcela, 0.0, 0.0, valorParcela,
                    obj.getDataPagamento(), data, "Não",
                    obj.getQuantidadeParcelas(), i, obj.getStatus(), new Date());
            pagamentoRepository.save(pagamento);
            historicoService.insertAny(pagamento, "Inserir");
            i++;
        }
        return obj;
    }

    public Despesa update(Despesa obj, Boolean pagamentos) {
        Despesa newObj = find(obj.getId());
        updateData(newObj, obj);
        newObj = repo.save(newObj);
        if(pagamentos){
            for (Pagamento pagamento: newObj.getPagamentos()) {
                pagamento.setStatus(obj.getStatus());
                pagamento.setValor(obj.getValor() / obj.getQuantidadeParcelas());
                pagamento.setValorFinal(obj.getValor() / obj.getQuantidadeParcelas());
                pagamentoRepository.save(pagamento);
            }
        }
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

    public List<Despesa> findAll(){
        return repo.findAll();
    }

    public List<StatusDTO> findStatus(){
        return repo.findStatus();
    }

    public Page<Despesa> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(Despesa newObj, Despesa obj) {
        newObj.setNome(obj.getNome());
        newObj.setObs(obj.getObs());
        newObj.setStatus(obj.getStatus());
        newObj.setGuia(obj.getGuia());
        newObj.setCentroDeCusto(obj.getCentroDeCusto());
        newObj.setStatus(obj.getStatus());
        newObj.setServico(obj.getServico());
        newObj.setTipoDespesa(obj.getTipoDespesa());
        newObj.setVencimento(obj.getVencimento());
        newObj.setTipoPagamentoRecebimento(obj.getTipoPagamentoRecebimento());
        newObj.setValor(obj.getValor());
        newObj.setFornecedor(obj.getFornecedor());
        newObj.setCompetencia(obj.getCompetencia());
    }

    public ResponseEntity recibo(Integer idDespesa) throws IOException, JRException {
        List<DespesaReciboDTO> despesa = repo.findByRecibo(idDespesa);
        Optional<Despesa> despesaBase = repo.findById(idDespesa);

        if(despesaBase.isPresent()){
            if(despesaBase.get().getFornecedor() != null){
                despesa.get(0).setFornecedorNome(despesaBase.get().getFornecedor().getNome());
                despesa.get(0).setFornecedorCpfOuCnpj(despesaBase.get().getFornecedor().getCnpj());
            } else if(despesaBase.get().getServico().getNomeCliente() != null){
                despesa.get(0).setFornecedorNome(despesaBase.get().getServico().getNomeCliente());
            }

            if(despesaBase.get().getServico() != null){
                despesa.get(0).setServicoNome(despesaBase.get().getServico().getNome());
                despesa.get(0).setIdentificador(despesaBase.get().getServico().getIdentificador());
            }
        }

        // configurando a fonte
        String fontPath = this.getClass().getResource("/fonts/arial.ttf").getPath();
        Font font = new Font("Arial", Font.BOLD, 10);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("REPORT_FONT", font);

        // compilando o relatório
        File file = ResourceUtils.getFile("classpath:ReciboDespesa.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        // preenchendo os dados
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(despesa);

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

    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = "C:\\Users\\ti\\Desktop";
        List<Despesa> despesa = findAll();

        File file = ResourceUtils.getFile("classpath:ListaReceita.jrxml");
        System.out.println("IMPRIMINDO CAMINHO DO ARQUIVO" + file.getAbsolutePath());
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(despesa);
        Map<String, Object> paramenters = new HashMap<>();
        paramenters.put("createdBy", "Teste");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramenters, dataSource);

        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\recibo.html");
        }

        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\recibo.pdf");
        }

        return "Recibo salvo: " + path;
    }
}

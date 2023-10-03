package com.confianca.services;

import com.confianca.domain.Despesa;
import com.confianca.domain.Receita;
import com.confianca.dto.*;
import com.confianca.security.UserSS;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ServicoService servicoService;
    @Autowired
    private ReceitaService receitaService;
    @Autowired
    private DespesaService despesaService;

    public ResponseEntity<ExtratoDTO> fechamento(String dataInicial, String dataFinal, String cliente, String fornecedor, String tipoProduto)
            throws ParseException, FileNotFoundException, JRException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String nome;

        Double somaReceita = 0.0;
        Double somaDespesa = 0.0;

        if (principal instanceof UserSS) {
            nome = ((UserSS) principal).getUsername();
        } else {
            nome = principal.toString();
        }

        List<DadosClienteFechamentoDTO> listDadosCliente = new ArrayList<>();
        DadosClienteFechamentoDTO dadosCliente = new DadosClienteFechamentoDTO(usuarioService.findByLogin(nome));

        List<FechamentoDTO> fechamentoDTOS = new ArrayList<>();
        fechamentoDTOS = servicoService.findByFechamento(dataInicial, dataFinal, cliente, fornecedor, tipoProduto);
        //fechamentoDTOS = fechamentoDTOS.stream().filter(fechamento -> fechamento.get)

        for (FechamentoDTO fech : fechamentoDTOS) {
            somaReceita = somaReceita + fech.getReceita();
            somaDespesa = somaDespesa + fech.getDespesa();
        }

        dadosCliente.setReceita(somaReceita);
        dadosCliente.setDespesa(somaDespesa);
        dadosCliente.setSaldo(somaReceita - somaDespesa);

        dadosCliente.setFechamentoDTOS(fechamentoDTOS);
        listDadosCliente.add(dadosCliente);

        // configurando a fonte
        String fontPath = this.getClass().getResource("/fonts/arial.ttf").getPath();
        Font font = new Font("Arial", Font.BOLD, 10);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("REPORT_FONT", font);

        // compilando o relatório
        File file = ResourceUtils.getFile("classpath:Fechamento.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        // preenchendo os dados
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listDadosCliente);

        // gerando o relatório em memória
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // exportando o relatório para PDF
        byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "Fechamento.pdf");
        headers.setContentLength(pdf.length);

        return new ResponseEntity(pdf, headers, HttpStatus.OK);
    }

    public ResponseEntity<ExtratoDTO> fechamentoFinanceiro(String dataInicial, String dataFinal, String tipo, String tipoPagamento,
                                                           String fornecedor, String cliente, String pagador)
            throws ParseException, FileNotFoundException, JRException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String nome;
        String tipoPag = "";

        Double somaReceita = 0.0;
        Double somaDespesa = 0.0;

        if (principal instanceof UserSS) {
            nome = ((UserSS) principal).getUsername();
        } else {
            nome = principal.toString();
        }

        if (tipo.equalsIgnoreCase("null")) {
            tipoPag = "Todos";
        } else {
            tipoPag = tipo;
        }

        if (tipoPagamento.equalsIgnoreCase("Todos") || tipoPagamento.equalsIgnoreCase("null")) {
            tipoPagamento = "";
        }

        List<DadosClienteFechamentoDTO> listDadosCliente = new ArrayList<>();
        DadosClienteFechamentoDTO dadosCliente = new DadosClienteFechamentoDTO(usuarioService.findByLogin(nome));
        List<ExtratoDTO> extratoDTOS = new ArrayList<>();

        if (tipoPag.equalsIgnoreCase("Receita") || tipoPag.equalsIgnoreCase("Todos") || tipoPag.equalsIgnoreCase("")) {
            List<Receita> listaReceita =
                    receitaService.findByCompetenciaTipoPagamento(dataInicial, dataFinal, tipoPagamento).stream()
                            .filter(objeto -> !objeto.getStatus().equals("Cancelado"))
                            .collect(Collectors.toList());
            for (Receita receita : listaReceita) {
                if (cliente == null || cliente.isEmpty() || cliente.equals("null")) {
                    ExtratoDTO extratoDTOReceita = new ExtratoDTO(receita);
                    extratoDTOS.add(extratoDTOReceita);

                } else {
                    if (receita.getCliente() != null) {
                        if (receita.getCliente().getNome().equals(cliente)) {
                            ExtratoDTO extratoDTOReceita = new ExtratoDTO(receita);
                            extratoDTOS.add(extratoDTOReceita);
                        }
                    }
                }
            }
        }

        if (tipoPag.equalsIgnoreCase("Despesa") || tipoPag.equalsIgnoreCase("Todos") || tipoPag.equalsIgnoreCase("")) {
            List<Despesa> listaDespesa =
                    despesaService.findByCompetenciaTipoPagamento(dataInicial, dataFinal, tipoPagamento).stream()
                            .filter(objeto -> !objeto.getStatus().equals("Cancelado"))
                            .collect(Collectors.toList());
            for (Despesa despesa : listaDespesa) {
                if (fornecedor == null || fornecedor.isEmpty() || fornecedor.equals("null")) {
                    ExtratoDTO extratoDTODespesa = new ExtratoDTO(despesa);
                    extratoDTOS.add(extratoDTODespesa);
                } else {
                    if (despesa.getFornecedor() != null) {
                        if (despesa.getFornecedor().getNome().equals(fornecedor)) {
                            ExtratoDTO extratoDTODespesa = new ExtratoDTO(despesa);
                            extratoDTOS.add(extratoDTODespesa);
                        }
                    }
                }
            }
        }

        for (ExtratoDTO ext : extratoDTOS) {
            if (ext.getTipo().equals("Receita")) {
                somaReceita = somaReceita + ext.getValor();
            } else {
                somaDespesa = somaDespesa + ext.getValor();
            }
        }

        dadosCliente.setReceita(somaReceita);
        dadosCliente.setDespesa(somaDespesa);
        dadosCliente.setSaldo(somaReceita - somaDespesa);

        extratoDTOS.sort((p1, p2) -> Integer.compare(p1.getId(), p2.getId()));

        dadosCliente.setExtratoDTOS(extratoDTOS);
        listDadosCliente.add(dadosCliente);

        // configurando a fonte
        String fontPath = this.getClass().getResource("/fonts/arial.ttf").getPath();
        Font font = new Font("Arial", Font.BOLD, 10);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("REPORT_FONT", font);

        // compilando o relatório
        File file = ResourceUtils.getFile("classpath:FechamentoTipo.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        // preenchendo os dados
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listDadosCliente);

        // gerando o relatório em memória
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // exportando o relatório para PDF
        byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "FechamentoFinanceiro.pdf");
        headers.setContentLength(pdf.length);

        return new ResponseEntity(pdf, headers, HttpStatus.OK);
    }
}
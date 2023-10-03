package com.confianca.resources;

import com.confianca.domain.Despesa;
import com.confianca.domain.Passageiro;
import com.confianca.domain.Receita;
import com.confianca.domain.Usuario;
import com.confianca.dto.DadosClienteDTO;
import com.confianca.dto.ExtratoDTO;
import com.confianca.dto.FechamentoDTO;
import com.confianca.security.UserSS;
import com.confianca.services.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/relatorio")
public class ReportResource {

    @Autowired
    private ReportService service;
    @Autowired
    private PassageiroService passageiroService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UsuarioService usuarioService;

    private final ExcelReportService reportService;

    public ReportResource(ExcelReportService reportService) {
        this.reportService = reportService;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/passageiro")
    public ResponseEntity<Passageiro> generateReport(@RequestParam String dataIn,
                                                     @RequestParam String dataFi) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        Date dataT = formatter.parse(dataIn);

        Date dataInicial = formatter.parse(dataIn);
        Date dataFinal = formatter.parse(dataFi);

        // Obtenha os dados que você deseja incluir no relatório
        List<Passageiro> passageiros = passageiroService.findByProdutoVendidoServicoDataBetween(dataInicial, dataFinal);

        // Gere o relatório Excel e obtenha o array de bytes
        byte[] excelBytes = reportService.generateExcelReport(passageiros);

        // Envia o arquivo Excel por e-mail
        emailService.sendEmailRelatorioPassageiro(dataFinal, excelBytes);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/fechamentotipo")
    public ResponseEntity<ExtratoDTO> fechamentoFinanceiro(@RequestParam String dataInicial,
                                                           @RequestParam String dataFinal,
                                                           @RequestParam String tipo,
                                                           @RequestParam String tipoPagamento,
                                                           @RequestParam String fornecedor,
                                                           @RequestParam String cliente,
                                                           @RequestParam String pagador) throws ParseException, FileNotFoundException, JRException {
        return service.fechamentoFinanceiro(dataInicial, dataFinal, tipo, tipoPagamento, fornecedor, cliente, pagador);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/fechamento")
    public ResponseEntity<ExtratoDTO> fechamento(@RequestParam String dataInicial,
                                                 @RequestParam String dataFinal,
                                                 @RequestParam String cliente,
                                                 @RequestParam String fornecedor,
                                                 @RequestParam String tipoProduto) throws ParseException, FileNotFoundException, JRException {
        return service.fechamento(dataInicial, dataFinal, cliente, fornecedor, tipoProduto);
    }
}

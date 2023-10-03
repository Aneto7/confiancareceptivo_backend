package com.confianca.services;

import com.confianca.domain.Passageiro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
class ScheduledService {

    @Autowired
    private PassageiroService passageiroService;
    @Autowired
    private ExcelReportService excelReportService;
    @Autowired
    private EmailService emailService;


    @Scheduled(cron = "0 0 5 * * ?") // Executa todos os dias às 5 da manhã
    public void executeDailyReport() {
        envioEmailSeguro();
    }

    public void envioEmailSeguro(){
        // Obtenha os dados que você deseja incluir no relatório
        List<Passageiro> passageiros = passageiroService.findByProdutoVendidoServicoData(new Date());

        // Gere o relatório Excel e obtenha o array de bytes
        byte[] excelBytes = excelReportService.generateExcelReport(passageiros);

        // Envia o arquivo Excel por e-mail
        emailService.sendEmailRelatorioPassageiro(new Date(), excelBytes);
    }
}
package com.confianca.services;

import com.confianca.domain.Passageiro;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ExcelReportService {
    public byte[] generateExcelReport(List<Passageiro> passageiros) {
        try (Workbook workbook = new HSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Relatório de Passageiros");

            // Crie o cabeçalho do relatório
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Apólice");
            headerRow.createCell(1).setCellValue("Estipul Sub");
            headerRow.createCell(2).setCellValue("Segurado");
            headerRow.createCell(3).setCellValue("Data Nascimento");
            headerRow.createCell(4).setCellValue("CPF");
            headerRow.createCell(5).setCellValue("MAC");
            headerRow.createCell(6).setCellValue("IPTP");
            headerRow.createCell(7).setCellValue("DMHA");
            headerRow.createCell(8).setCellValue("Início");
            headerRow.createCell(9).setCellValue("Fim");
            headerRow.createCell(10).setCellValue("Qtde dias");
            headerRow.createCell(11).setCellValue("Custo individual");

            // Preencha os dados dos passageiros
            int rowNum = 1;
            for (Passageiro passageiro : passageiros) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue("");
                row.createCell(1).setCellValue("");
                row.createCell(2).setCellValue(passageiro.getNome());
                row.createCell(3).setCellValue(passageiro.getNascimento());
                row.createCell(4).setCellValue(passageiro.getCpf());
                row.createCell(5).setCellValue(30000.00);
                row.createCell(6).setCellValue(30000.00);
                row.createCell(7).setCellValue(3000.00);
                row.createCell(8).setCellValue(new Date());
                row.createCell(9).setCellValue(new Date());
                row.createCell(10).setCellValue(1);
                row.createCell(11).setCellValue(0.88);

            }

            // Converta o workbook para um array de bytes
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}


package com.confianca.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.confianca.services.exeptions.FileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Service
public class S3Service {

    private Logger LOG = LoggerFactory.getLogger(S3Service.class);

    @Autowired
    private AmazonS3 s3client;

    @Value("${s3.bucket}")
    private String bucketName;

    public URI uploadFile(MultipartFile multipartFile, String exame, String paciente, Integer idFile) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss ");
        String dataTexto = sdf.format(new Date());
        try {
            String fileName = multipartFile.getOriginalFilename();
            InputStream is = multipartFile.getInputStream();
            String contentType = multipartFile.getContentType();
            fileName = exame + dataTexto + "-paciente" + paciente + "-IdExame" + idFile + "-" + fileName;
            return uploadFile(is, fileName, contentType, exame, paciente, idFile);
        } catch (IOException e) {
            throw new RuntimeException("Erro de IO: " + e.getMessage());
        }
    }

    public URI uploadFile(InputStream is, String fileName, String contentType, String exame, String paciente, Integer idFile) {
//        Paciente p = pacienteService.find(Integer.parseInt(paciente));
//        Exame ee = exameService.find(idFile);

        try {
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType(contentType);
            LOG.info("Iniciando upload");
            s3client.putObject(bucketName, fileName, is, meta);
            LOG.info("Upload finalizado");

//            CaminhoArquivo c = new CaminhoArquivo(null,(s3client.getUrl(bucketName, fileName).toURI()).toString(),p,ee);
//
//            ee.getCaminhos().addAll(Arrays.asList(c));
//
//            caminhoArquivoRepository.saveAll(Arrays.asList(c));
//            exameRepository.saveAll(Arrays.asList(ee));

            return s3client.getUrl(bucketName, fileName).toURI();
        } catch (URISyntaxException e) {
            throw new FileException("Erro ao converter URL para URI");
        }
    }
}


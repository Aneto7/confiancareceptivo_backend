package com.confianca.services;

//Excluir

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.confianca.domain.*;
import com.confianca.repositories.ImagemRepository;
import com.confianca.repositories.UploadRepository;
import com.confianca.services.exeptions.DataIntegrityException;
import com.confianca.services.exeptions.FileException;
import com.confianca.services.exeptions.ObjectNotFoundException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FileUploadDownloadService {
    private Logger LOG = LoggerFactory.getLogger(S3Service.class);

    @Autowired
    private AmazonS3 s3client;

    @Autowired
    private Environment env;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ImagemRepository imagemRepository;

    @Autowired
    private UploadService uploadService;

    @Value("${s3.bucket}")
    private String bucketName;

    public Imagem find(Integer id) {
        Optional<Imagem> obj = imagemRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cidade.class.getName()));
    }

    public void deleteFile(Integer idImagem) {
        find(idImagem);
        try {
            imagemRepository.deleteById(idImagem);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir a imagem");
        }
    }

    public URI uploadFile(MultipartFile file, String nome, Integer idProduto) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss ");
        String dataTexto = sdf.format(new Date());
        try {
            String fileName = file.getOriginalFilename();
            InputStream is = file.getInputStream();
            String contentType = file.getContentType();
            Double tamanho = Double.valueOf(file.getSize());
            fileName = nome + dataTexto + "-IdProduto" + idProduto + "-" + fileName;
            return uploadFile(is, fileName, contentType, idProduto, tamanho);
        } catch (IOException e) {
            throw new RuntimeException("Erro de IO: " + e.getMessage());
        }
    }

    public URI uploadFileCliente(MultipartFile file, String nome, Integer idCliente) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss ");
        String dataTexto = sdf.format(new Date());
        try {
            String fileName = file.getOriginalFilename();
            InputStream is = file.getInputStream();
            String contentType = file.getContentType();
            Double tamanho = Double.valueOf(file.getSize());
            fileName = nome + dataTexto + "-IdCliente" + idCliente + "-" + fileName;
            return uploadFileCliente(is, fileName, contentType, idCliente, tamanho);
        } catch (IOException e) {
            throw new RuntimeException("Erro de IO: " + e.getMessage());
        }
    }

    public URI uploadFile(InputStream is, String fileName, String contentType, Integer idProduto, Double tamanho) {
        try {
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType(contentType);
            LOG.info("Iniciando upload");
            s3client.putObject(bucketName, fileName, is, meta);
            LOG.info("Upload finalizado");
            System.out.println("Testendo is no S3Service");

            Produto produto = produtoService.find(idProduto);

            Boolean principal = false;
            List<Imagem> imagens = imagemRepository.findByPrincipalAndProdutoId(true, idProduto);
            if (imagens.isEmpty()) principal = true;

            Imagem imagem = new Imagem(null, produto, null, fileName, contentType,
                    (s3client.getUrl(bucketName, fileName).toURI()).toString(), tamanho, principal);

            imagemRepository.saveAll(Arrays.asList(imagem));
            return s3client.getUrl(bucketName, fileName).toURI();
        } catch (URISyntaxException e) {
            throw new FileException("Erro ao converter URL para URI");
        }
    }

    public URI uploadFileCliente(InputStream is, String fileName, String contentType, Integer idCliente, Double tamanho) {
        try {
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType(contentType);
            LOG.info("Iniciando upload");
            s3client.putObject(bucketName, fileName, is, meta);
            LOG.info("Upload finalizado");
            System.out.println("Testendo is no S3Service");

            Cliente cliente = clienteService.find(idCliente);

            Boolean principal = true;
            List<Imagem> imagens = imagemRepository.findByPrincipalAndClienteId(true, idCliente);

            for (Imagem img:imagens) {
                imagemRepository.deleteById(img.getId());
            }

            Imagem imagem = new Imagem(null, null, cliente, fileName, contentType,
                    (s3client.getUrl(bucketName, fileName).toURI()).toString(), tamanho, principal);

            imagemRepository.saveAll(Arrays.asList(imagem));
            return s3client.getUrl(bucketName, fileName).toURI();
        } catch (URISyntaxException e) {
            throw new FileException("Erro ao converter URL para URI");
        }
    }

    public String uploadFileGeral(MultipartFile file, Integer idFile, String tipo) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            String projectPath = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getAbsolutePath();
            File uploadsDir = new File(projectPath + "/" + tipo);
            uploadsDir.mkdir();
            uploadsDir = new File(projectPath + "/" + tipo + "/" + idFile);
            uploadsDir.mkdir();

            Path fileStorageLocation = Paths.get(projectPath + "/" + tipo + "/" + idFile)
                    .toAbsolutePath().normalize();
            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation,
                    StandardCopyOption.REPLACE_EXISTING);
            Upload upload = new Upload(null, fileName, targetLocation.toString(), idFile, tipo, "Ativo");
            uploadService.insert(upload);
        } catch (Exception ex) {
            System.out.println("Exception:" + ex);
        }

        return fileName;
    }

    public List<String> getFiles(String nome, Integer idProduto) throws IOException, URISyntaxException {
        String projectPath = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getAbsolutePath();
        File uploadsDir = new File(projectPath + "/" + nome + "/" + idProduto);

        return Files.walk(Paths.get(projectPath + "/" + nome + "/" + idProduto))
                .filter(Files::isRegularFile)
                .map(file -> projectPath + '\\' + nome + '\\' + idProduto + '\\' + file.getFileName().toString())
                .collect(Collectors.toList());
    }

    public Resource loadFileAsResource(String arquivo, String tipo, Integer id) throws MalformedURLException, URISyntaxException {
        // Caminho para o arquivo que deseja fazer o download
        String projectPath = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getAbsolutePath();
        Path fileStorageLocation = Paths.get(projectPath + '\\' + tipo + '\\' + id)
                .toAbsolutePath().normalize();
        Path filePath = fileStorageLocation.resolve(arquivo).normalize();

        // Cria o objeto Resource do arquivo
        Resource resource = new FileSystemResource(filePath);

        // Retorna a resposta HTTP com o arquivo
        return resource;
    }

//    public List<String> deleteFiles(String exame, String paciente, Integer idFile, String arquivo) throws IOException, URISyntaxException {
//        String projectPath = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getAbsolutePath();
//        File uploadsDir = new File(projectPath + "/" + exame + "/" + paciente + "/" + idFile + "/" + arquivo);
//        System.out.println(uploadsDir);
//        FileUtils.delete(uploadsDir);
//
//        return null;
//    }
}


package com.confianca.resources;

//Excluir

import com.confianca.domain.UploadFileResponse;
import com.confianca.services.FileUploadDownloadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/arquivos")
@RestController
public class FileUploadDownloadController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadDownloadController.class);

    @Autowired
    private FileUploadDownloadService fileUploadDownloadService;

    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String nome, @RequestParam Integer idProduto) {
        URI uri = fileUploadDownloadService.uploadFile(file, nome, idProduto);
        return ResponseEntity.created(uri).build();
    }

    @PostMapping(value = "/uploadFile/cliente", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFileCliente(@RequestParam("file") MultipartFile file, @RequestParam String nome, @RequestParam Integer idCliente) {
        URI uri = fileUploadDownloadService.uploadFileCliente(file, nome, idCliente);
        return ResponseEntity.created(uri).build();
    }

    @PostMapping(value = "/uploadFileGeral", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UploadFileResponse uploadFileGeral(@RequestParam("file") MultipartFile file, @RequestParam Integer id, @RequestParam String tipo) {
        String fileName = fileUploadDownloadService.uploadFileGeral(file, id, tipo);
        return new UploadFileResponse(fileName);
    }

//    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam Integer id, @RequestParam String tipo) {
//        String fileName = fileUploadDownloadService.uploadFile(file, id, tipo);
//        return new UploadFileResponse(fileName);
//    }

    // Displays the list of uploaded files.
    @GetMapping("/getFiles")
    public List<String> getFiles(@RequestParam String nome, @RequestParam Integer idProduto) throws IOException, URISyntaxException {
        return fileUploadDownloadService.getFiles(nome, idProduto);
    }


//    @DeleteMapping("/deleteFile")
//    public List<String> deleteFile(@RequestParam String exame, @RequestParam String paciente, @RequestParam Integer idFile, @RequestParam String arquivo) throws IOException, URISyntaxException {
//        return fileUploadDownloadService.deleteFiles(exame,paciente, idFile, arquivo);
//    }

    @RequestMapping(value = "/deleteFile/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteFile(@PathVariable Integer id) {
        fileUploadDownloadService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }

    // Downloads a file using filename.
    @GetMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestParam String tipo, @RequestParam Integer id, @RequestParam String arquivo, HttpServletRequest request)
            throws MalformedURLException, URISyntaxException {
        Resource resource = fileUploadDownloadService.loadFileAsResource(arquivo, tipo, id);
        // Try to determine file's content type
        String contentType = null;
       try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}

package com.confianca.services;

import com.confianca.domain.Imagem;
import com.confianca.repositories.ImagemRepository;
import com.confianca.services.exeptions.DataIntegrityException;
import com.confianca.services.exeptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class ImagemService {

    @Autowired
    private ImagemRepository repo;

    public Imagem find(Integer id){
        Optional<Imagem> obj = repo.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: " + Imagem.class.getName()));
    }

    public Imagem insert(Imagem obj){
        obj.setId(null);
        return repo.save(obj);
    }

    public Imagem update(Imagem obj) {
        Imagem newObj = find(obj.getId());
        if(obj.getPrincipal() == true && newObj.getProduto() != null){
            System.out.println("teste: " + obj.getPrincipal());
            List<Imagem> imagems = repo.findByProdutoId(newObj.getProduto().getId());
            for (Imagem img:imagems) {
                img.setPrincipal(false);
                repo.save(img);
            }
        }
        updateData(newObj, obj);
        return repo.save(newObj);
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

    public List<Imagem> findAll(){
        return repo.findAll();
    }

    public Page<Imagem> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(Imagem newObj, Imagem obj) {
        newObj.setNome(obj.getNome());
        newObj.setLocal(obj.getLocal());
        newObj.setTamanho(obj.getTamanho());
        newObj.setPrincipal(obj.getPrincipal());
    }

    public String uploadFile(MultipartFile file, String vinculoFile, Integer idFile) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            String projectPath = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getAbsolutePath();
            File uploadsDir = new File(projectPath +"/" +vinculoFile);
            uploadsDir.mkdir();
            uploadsDir = new File(projectPath +"/" +vinculoFile+"/" +idFile);
            uploadsDir.mkdir();

//            Path fileStorageLocation = Paths.get(env.getProperty("file.upload-dir") +"/" +vinculoFile)
//                    .toAbsolutePath().normalize();
//            Path targetLocation = fileStorageLocation.resolve(fileName);
//            Files.copy(file.getInputStream(), targetLocation,
//                    StandardCopyOption.REPLACE_EXISTING);

            Path fileStorageLocation = Paths.get(projectPath +"/" +vinculoFile+"/" +idFile)
                    .toAbsolutePath().normalize();
            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            System.out.println("Exception:" + ex);
        }
        return fileName;
    }
}

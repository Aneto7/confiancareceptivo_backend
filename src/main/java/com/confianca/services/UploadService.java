package com.confianca.services;

import com.confianca.domain.Upload;
import com.confianca.repositories.UploadRepository;
import com.confianca.services.exeptions.DataIntegrityException;
import com.confianca.services.exeptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UploadService {

    @Autowired
    private UploadRepository repo;

    public Upload find(Integer id) {
        Optional<Upload> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Upload.class.getName()));
    }

    public Upload insertAny(Object obj, String acao) {
        Upload upload = new Upload();
        Class objClass = obj.getClass();
        Field[] fields = objClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            System.out.println("imprimindo nome do campo do objeto: " + field.getName());
//            if (field.getName().equals("id") || field.getName().equals("nome"))
//                try {
//                    Object fieldValue = field.get(obj);
//                    if (field.getName().equals("nome")) {
//                        upload.setNome(fieldValue.toString());
//                    } else if(field.getName().equals("descricao")){
//                        upload.setNome(fieldValue.toString());
//                    }
//                    if (field.getName().equals("id")) {
//                        upload.setIdRegistro(Integer.parseInt(fieldValue.toString()));
//                    }
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
        }
//        upload = new Upload(null, upload.getNome(), upload.getIdRegistro(), upload.getNome(), "Ativo");
        return repo.save(upload);
    }

    public Upload insert(Upload obj) {
        obj.setId(null);
        return repo.save(obj);
    }

    public Upload update(Upload obj) {
        Upload newObj = find(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    public void delete(Integer id) {
        find(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um cartão que possui lançamentos");
        }
    }

    public List<Upload> findAll() {
        return repo.findAll();
    }

    public List<Upload> findByIdRegistroAndObjeto(Integer id, String tipo) {
        return repo.findByIdRegistroAndObjeto(id, tipo);
    }

    public Page<Upload> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(Upload newObj, Upload obj) {
        newObj.setNome(obj.getNome());
        newObj.setStatus(obj.getStatus());
    }
}

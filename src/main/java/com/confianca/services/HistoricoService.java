package com.confianca.services;

import com.confianca.domain.Historico;
import com.confianca.domain.Usuario;
import com.confianca.repositories.HistoricoRepository;
import com.confianca.services.exeptions.DataIntegrityException;
import com.confianca.services.exeptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HistoricoService {

    @Autowired
    private HistoricoRepository repo;

    @Lazy
    @Autowired
    private UsuarioService usuarioService;

    public Historico find(Integer id) {
        Optional<Historico> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Historico.class.getName()));
    }

    public Historico insertAny(Object obj, String acao) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        Historico historico = new Historico();
        Class objClass = obj.getClass();
        Field[] fields = objClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals("id") || field.getName().equals("nome"))
                try {
                    Object fieldValue = field.get(obj);
                    if (field.getName().equals("nome")) {
                        historico.setNome(fieldValue.toString());
                    } else if(field.getName().equals("descricao")){
                        historico.setNome(fieldValue.toString());
                    }
                    if (field.getName().equals("id")) {
                        historico.setIdRegistro(Integer.parseInt(fieldValue.toString()));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
        }

        Usuario usuario = usuarioService.findByLogin(currentUserName);

        historico = new Historico(null, usuario, historico.getIdRegistro(), historico.getNome(), new Date(),
                "Ação " + acao + " registro realizada pelo usuário " + currentUserName + " no item " + obj.getClass().getSimpleName() + " no dia " + new Date().toString(), "Ativo",
                obj.getClass().getSimpleName(), acao, null);
        System.out.println("Imprimindo o nome do usuário logado: " + currentUserName);
        return repo.save(historico);
    }

    public Historico insert(Historico obj) {
        obj.setId(null);
        return repo.save(obj);
    }

    public Historico update(Historico obj) {
        Historico newObj = find(obj.getId());
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

    public List<Historico> findAll() {
        return repo.findAll();
    }

    public List<Historico> findByIdRegistroAndObjeto(Integer id, String obj) {
        return repo.findByIdRegistroAndObjeto(id, obj);
    }

    public Page<Historico> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(Historico newObj, Historico obj) {
        newObj.setNome(obj.getNome());
        newObj.setStatus(obj.getStatus());
        newObj.setObs(obj.getObs());
        newObj.setData(obj.getData());
    }
}

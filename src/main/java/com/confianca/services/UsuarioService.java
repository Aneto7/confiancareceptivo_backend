package com.confianca.services;

import com.confianca.domain.Usuario;
import com.confianca.domain.enums.Perfil;
import com.confianca.repositories.UsuarioRepository;
import com.confianca.services.exeptions.DataIntegrityException;
import com.confianca.services.exeptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private UsuarioRepository repo;

    @Autowired
    private  HistoricoService historicoService;

    public Usuario find(Integer id) {
        Optional<Usuario> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName()));
    }

    public Usuario findByLogin(String login) {
        Usuario obj = repo.findByLogin(login);
        return obj;
    }

    @Transactional
    public Usuario insert(Usuario obj, Integer[] idPerfis) {
        Usuario usr = new Usuario(null, obj.getCliente(), obj.getNome(), obj.getRamal(), obj.getTipo(),
                obj.getPermissao(), obj.getStatus(), obj.getLogin(), pe.encode(obj.getSenha()));
        obj.setId(null);
        usr.setSenha(pe.encode(obj.getSenha()));
        usr = repo.save(usr);
        usr = associate(usr, idPerfis);
        historicoService.insertAny(usr, "Inserir");
        return usr;
    }

    @Transactional
    public Usuario associate(Usuario obj, Integer[] idPerfis){
        Usuario usr = find(obj.getId());
        usr.getPerfisGeral().removeAll(usr.getPerfisGeral());
        System.out.println(usr.getPerfis());
        for (Integer perfil : idPerfis) {
            usr.addPerfil(Perfil.toEnum(perfil));
        }
        usr = repo.save(usr);
        return usr;
    }

    public Usuario update(Usuario obj) {
        Usuario newObj = find(obj.getId());
        updateData(newObj, obj);
        newObj = repo.save(newObj);
        historicoService.insertAny(obj, "Atualizar");
        return newObj;
    }

    public Usuario updateSenha(Usuario obj) {
        Usuario newObj = find(obj.getId());
        newObj.setSenha(obj.getSenha());
        newObj = repo.save(newObj);
        historicoService.insertAny(obj, "Atualizar");
        return newObj;
    }

//    private void updateSenha(Usuario newObj, Usuario obj) {
//        newObj.setSenha(this.pe.encode(obj.getSenha()));
//    }

    public void delete(Integer id) {
        find(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um Usuario que possui pedidos relacionados");
        }
    }

    public List<Usuario> findAll() {
        return repo.findAll();
    }

    public Page<Usuario> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Usuario fromDTO(Usuario obj) {
        return new Usuario(obj.getId(), obj.getCliente(), obj.getNome(), obj.getRamal(), obj.getTipo(), obj.getPermissao(), obj.getStatus(), obj.getLogin(), pe.encode(obj.getSenha()));
    }

    private void updateData(Usuario newObj, Usuario obj) {
        newObj.setCliente(obj.getCliente());
        newObj.setNome(obj.getNome());
        newObj.setTipo(obj.getTipo());
        newObj.setPermissao(obj.getPermissao());
        newObj.setStatus(obj.getStatus());
        newObj.setLogin(obj.getLogin());
        newObj.setRamal(obj.getRamal());
    }

    private void updateNovaSenha(Usuario newObj, Usuario obj) {
        newObj.setSenha(obj.getSenha());
    }

    private void updateDataExcluido(Usuario newObj, Usuario obj) {
        newObj.setStatus(obj.getStatus());
    }

}

package com.confianca.resources;

import com.confianca.domain.BandeiraDeCartaoDeCredito;
import com.confianca.domain.Usuario;
import com.confianca.dto.SenhaDTO;
import com.confianca.dto.SenhaTrocaDTO;
import com.confianca.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/usuario")
public class UsuarioResource {

    @Autowired
    private UsuarioService service;

    @Autowired
    private BCryptPasswordEncoder pe;

    @PreAuthorize("hasAnyRole('ADMIN','CLIENTEAGENCIA')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Usuario> find(@PathVariable Integer id) {
        Usuario obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/login/{login}", method = RequestMethod.GET)
    public ResponseEntity<Usuario> findByLogin(@PathVariable String login) {
        Usuario obj = service.findByLogin(login);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody @Valid Usuario obj, @RequestParam Integer[] idPerfis) {
        System.out.println("imprimindo perfil" + idPerfis[0]);
        obj = service.insert(obj, idPerfis);
        //obj = service.associate(obj, idPerfis);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody Usuario obj, @PathVariable Integer id, @RequestParam Integer[] idPerfis) {
        obj.setId(id);
        System.out.println("Imprimindo usuário atualizado" + obj.getStatus());
        obj = service.update(obj);
        obj = service.associate(obj, idPerfis);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = {"/senha/{id}"}, method = {RequestMethod.PUT})
    public ResponseEntity<Void> update(@RequestBody @Valid SenhaDTO obj, @PathVariable Integer id) {
        Usuario usuarioSistema = service.find(id);
        usuarioSistema.setSenha(pe.encode(obj.getSenha()));
        usuarioSistema = service.updateSenha(usuarioSistema);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = {"/senhaT/{id}"}, method = {RequestMethod.PUT})
    public ResponseEntity<Void> update(@RequestBody @Valid SenhaTrocaDTO obj, @PathVariable Integer id) {
        Usuario usuarioSistema = service.find(id);
        if (pe.matches(obj.getSenha(), usuarioSistema.getSenha())) {
            usuarioSistema.setSenha(pe.encode(obj.getNovaSenha()));
            usuarioSistema = service.updateSenha(usuarioSistema);
        }
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Usuario>> findAll() {
        List<Usuario> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<Usuario>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<Usuario> list = service.findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(list);
    }
}

package com.confianca.resources;

import com.confianca.domain.Servico;
import com.confianca.domain.Venda;
import com.confianca.dto.StatusDTO;
import com.confianca.services.ServicoService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/servico")
public class ServicoResource {

    @Autowired
    private ServicoService service;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Servico> find(@PathVariable Integer id) {
        Servico obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/guia/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Servico>> findByGuia(@PathVariable Integer id) {
        List<Servico> list = service.findByGuia(id);
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public ResponseEntity<List<Servico>> findByDataInBetween(@RequestParam String dataInicial,
                                                             @RequestParam String dataFinal) throws ParseException {
        List<Servico> list = service.findByDataInBetween(dataInicial, dataFinal);
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/datastatus", method = RequestMethod.GET)
    public ResponseEntity<List<Servico>> findByStatusAndDataInBetween(@RequestParam String status,
                                                                      @RequestParam String dataInicial,
                                                                      @RequestParam String dataFinal) throws ParseException {
        List<Servico> list = service.findByStatusAndDataInBetween(status, dataInicial, dataFinal);
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Servico> insert(@RequestBody @Valid Servico obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody Servico obj, @PathVariable Integer id) {
        obj.setId(id);
        System.out.println("imprimindo: " + obj.toString());
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/os/{id}", method = RequestMethod.GET)
    public ResponseEntity exportReport(@PathVariable Integer id) throws IOException, JRException {
        return service.imprimirOs(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Servico>> findAll() {
        List<Servico> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public ResponseEntity<List<StatusDTO>> findStatus() {
        List<StatusDTO> list = service.findStatus();
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<Servico>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<Servico> list = service.findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(list);
    }
}

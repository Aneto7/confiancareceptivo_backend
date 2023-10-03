package com.confianca.resources;

import com.confianca.domain.Receita;
import com.confianca.dto.ReceitaDTO;
import com.confianca.dto.StatusDTO;
import com.confianca.services.ReceitaService;
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
@RequestMapping(value = "/receita")
public class ReceitaResource {

    @Autowired
    private ReceitaService service;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Receita> find(@PathVariable Integer id) {
        Receita obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/servico/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Receita>> finByServico(@PathVariable Integer id) {
        List<Receita> obj = service.findByServico(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/venda/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Receita>> findByVenda(@PathVariable Integer id) {
        List<Receita> obj = service.findByVenda(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public ResponseEntity<List<Receita>> findByVencimentoBetween(@RequestParam String dataInicial,
                                                                 @RequestParam String dataFinal) throws ParseException {
        List<Receita> obj = service.findByVencimentoBetween(dataInicial, dataFinal);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/dataemissao", method = RequestMethod.GET)
    public ResponseEntity<List<Receita>> findByEmissaoBetween(@RequestParam String dataInicial,
                                                                 @RequestParam String dataFinal) throws ParseException {
        List<Receita> obj = service.findByEmissaoBetween(dataInicial, dataFinal);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/acumulado", method = RequestMethod.GET)
    public ResponseEntity<List<ReceitaDTO>> findByReceitaAcumulada(@RequestParam Integer ano) throws ParseException {
        List<ReceitaDTO> list = service.findByReceitaAcumulada(ano);
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody @Valid Receita obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody Receita obj, @PathVariable Integer id, @RequestParam Boolean recebimentos) {
        obj.setId(id);
        obj = service.update(obj, recebimentos);
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
    public ResponseEntity<List<Receita>> findAll() {
        List<Receita> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public ResponseEntity<List<StatusDTO>> findStatus() {
        List<StatusDTO> list = service.findStatus();
        return ResponseEntity.ok().body(list);
    }

//    @RequestMapping(value = "/recibo/{id}", method = RequestMethod.GET)
//    public String exportReport(@PathVariable Integer id) throws JRException, FileNotFoundException {
//        return service.recibo(id);
//    }

//    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/recibo/{id}", method = RequestMethod.GET)
    public ResponseEntity exportReport(@PathVariable Integer id) throws IOException, JRException {
        return service.recibo(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<Receita>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<Receita> list = service.findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(list);
    }
}

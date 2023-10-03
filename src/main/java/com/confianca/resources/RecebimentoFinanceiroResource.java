package com.confianca.resources;

import com.confianca.cielo.ecommerce.request.CieloRequestException;
import com.confianca.domain.RecebimentoFinanceiro;
import com.confianca.dto.RecebimentoDTO;
import com.confianca.dto.StatusDTO;
import com.confianca.services.RecebimentoFinanceiroService;
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
@RequestMapping(value = "/recebimentofinanceiro")
public class RecebimentoFinanceiroResource {

    @Autowired
    private RecebimentoFinanceiroService service;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<RecebimentoFinanceiro> find(@PathVariable Integer id) {
        RecebimentoFinanceiro obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/venda/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<RecebimentoFinanceiro>> findByVenda(@PathVariable Integer id) {
        List<RecebimentoFinanceiro> obj = service.findByVenda(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public ResponseEntity<List<RecebimentoFinanceiro>> findByVencimentoBetween(@RequestParam String dataInicial,
                                                                 @RequestParam String dataFinal) throws ParseException {
        List<RecebimentoFinanceiro> obj = service.findByVencimentoBetween(dataInicial, dataFinal);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/acumulado", method = RequestMethod.GET)
    public ResponseEntity<List<RecebimentoDTO>> findByRecebimentoFinanceiroAcumulado(@RequestParam Integer ano) throws ParseException {
        List<RecebimentoDTO> list = service.findByRecebimentoFinanceiroAcumulado(ano);
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody @Valid RecebimentoFinanceiro obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody RecebimentoFinanceiro obj, @PathVariable Integer id, @RequestParam Boolean lancamentos) throws CieloRequestException, IOException {
        obj.setId(id);
        obj = service.update(obj, lancamentos);
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
    public ResponseEntity<List<RecebimentoFinanceiro>> findAll() {
        List<RecebimentoFinanceiro> list = service.findAll();
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
    public ResponseEntity<Page<RecebimentoFinanceiro>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<RecebimentoFinanceiro> list = service.findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(list);
    }
}

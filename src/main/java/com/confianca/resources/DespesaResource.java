package com.confianca.resources;

import com.confianca.domain.Despesa;
import com.confianca.dto.DespesaDTO;
import com.confianca.dto.StatusDTO;
import com.confianca.services.DespesaService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/despesa")
public class DespesaResource {

    @Autowired
    private DespesaService service;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Despesa> find(@PathVariable Integer id) {
        Despesa obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/fornecedor/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Despesa>> findByFornecedorId(@PathVariable Integer id) {
        List<Despesa> obj = service.findByFornecedorId(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/servico/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Despesa>> finByServico(@PathVariable Integer id) {
        List<Despesa> obj = service.finByServico(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/data", method = RequestMethod.GET)
//    @RequestParam("dataInicial") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataInicial,
    public ResponseEntity<List<Despesa>> findByVencimentoBetween(@RequestParam String dataInicial,
                                                                 @RequestParam String dataFinal) throws ParseException {
        List<Despesa> obj = service.findByVencimentoBetween(dataInicial, dataFinal);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/dataemissao", method = RequestMethod.GET)
    public ResponseEntity<List<Despesa>> findByEmissaoBetween(@RequestParam String dataInicial,
                                                                 @RequestParam String dataFinal) throws ParseException {
        List<Despesa> obj = service.findByEmissaoBetween(dataInicial, dataFinal);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/fornecedormes/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Despesa>> findByFornecedorIdAndCompetenciaBetween(@PathVariable Integer id,
                                                                                 @RequestParam String dataInicial,
                                                                                 @RequestParam String dataFinal) throws ParseException {
        List<Despesa> obj = service.findByFornecedorIdAndCompetenciaBetween(id, dataInicial, dataFinal);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/acumulado", method = RequestMethod.GET)
    public ResponseEntity<List<DespesaDTO>> findByDespesaAcumulada(@RequestParam Integer ano) throws ParseException {
        List<DespesaDTO> list = service.findByDespesaAcumulada(ano);
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody @Valid Despesa obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody Despesa obj, @PathVariable Integer id, @RequestParam Boolean pagamentos) {
        obj.setId(id);
        obj = service.update(obj, pagamentos);
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
    public ResponseEntity<List<Despesa>> findAll() {
        List<Despesa> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public ResponseEntity<List<StatusDTO>> findStatus() {
        List<StatusDTO> list = service.findStatus();
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/recibo/{id}", method = RequestMethod.GET)
    public ResponseEntity exportReport(@PathVariable Integer id) throws IOException, JRException {
        return service.recibo(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<Despesa>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<Despesa> list = service.findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(list);
    }
}

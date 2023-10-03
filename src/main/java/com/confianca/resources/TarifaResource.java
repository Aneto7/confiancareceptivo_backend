package com.confianca.resources;

import com.confianca.domain.*;
import com.confianca.dto.ProdutoVendidoDTO;
import com.confianca.dto.TarifaDTO;
import com.confianca.repositories.DataTarifaRepository;
import com.confianca.repositories.ProdutoRepository;
import com.confianca.services.DataTarifaService;
import com.confianca.services.ProdutoService;
import com.confianca.services.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/tarifa")
public class TarifaResource {

    @Autowired
    private TarifaService service;
    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private DataTarifaService dataTarifaService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<TarifaDTO> find(@PathVariable Integer id) {
        Tarifa obj = service.find(id);
        TarifaDTO newObj = new TarifaDTO(obj);
        return ResponseEntity.ok().body(newObj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<TarifaDTO>> findAll() {
        List<Tarifa> list = service.findAll();
        List<TarifaDTO> listDto = list.stream().map(obj -> new TarifaDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public ResponseEntity<List<TarifaDTO>> findByData(@RequestParam String dataInicial,
                                                   @RequestParam String dataFinal) throws ParseException {
        List<Tarifa> list = service.findByData(dataInicial, dataFinal);
        List<TarifaDTO> listDto = list.stream().map(obj -> new TarifaDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody @Valid Tarifa obj, @RequestParam Integer idProduto, @RequestParam String periodicidade,
                                       @RequestParam String diasSelecionados, @RequestParam String dataInicio, @RequestParam String dataFim) {
        obj.setProduto(produtoService.find(idProduto));
        obj = service.insertVarios(obj, periodicidade, diasSelecionados, dataInicio, dataFim);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody Tarifa obj, @PathVariable Integer id, @RequestParam Integer idProduto) {
        obj.setId(id);
        obj.setProduto(produtoService.find(idProduto));
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
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<Tarifa>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<Tarifa> list = service.findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(list);
    }
}

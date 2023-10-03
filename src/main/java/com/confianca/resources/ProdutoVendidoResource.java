package com.confianca.resources;

import com.confianca.cielo.ecommerce.request.CieloRequestException;
import com.confianca.domain.ProdutoVendido;
import com.confianca.domain.Venda;
import com.confianca.dto.ProdutoVendidoDTO;
import com.confianca.dto.TarifaDTO;
import com.confianca.services.ProdutoVendidoService;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/produtovendido")
public class ProdutoVendidoResource {

    @Autowired
    private ProdutoVendidoService service;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProdutoVendido> find(@PathVariable Integer id) {
        ProdutoVendido obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/proddto/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProdutoVendidoDTO> findDTOById(@PathVariable Integer id) {
        ProdutoVendido obj = service.find(id);
        ProdutoVendidoDTO objDTO = new ProdutoVendidoDTO(obj);
        return ResponseEntity.ok().body(objDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody @Valid ProdutoVendido obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody ProdutoVendido obj, @PathVariable Integer id) throws ParseException {
        obj.setId(id);
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
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ProdutoVendido>> findAll() {
        List<ProdutoVendido> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/produtodtodata/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<ProdutoVendidoDTO>> findAllDTO(@PathVariable Integer id,
                                                           @RequestParam String dataInicial,
                                                           @RequestParam String dataFinal) throws ParseException {
        List<ProdutoVendido> list = service.findByDataProduto(id, dataInicial, dataFinal);
        List<ProdutoVendidoDTO> listDto = list.stream().map(obj -> new ProdutoVendidoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/produtodtodata", method = RequestMethod.GET)
    public ResponseEntity<List<ProdutoVendidoDTO>> findAllData(@RequestParam String dataInicial,
                                                              @RequestParam String dataFinal) throws ParseException {
        List<ProdutoVendido> list = service.findByData(dataInicial, dataFinal);
        List<ProdutoVendidoDTO> listDto = list.stream().map(obj -> new ProdutoVendidoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/teste", method = RequestMethod.GET)
    public ResponseEntity<List<ProdutoVendido>> findTeste() {
        ProdutoVendido teste = new ProdutoVendido();
        List<ProdutoVendido> testeProduto = service.findServicoCadastrado(teste);
        return ResponseEntity.ok().body(testeProduto);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/servico/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<ProdutoVendido>> finByServico(@PathVariable Integer id) {
        List<ProdutoVendido> obj = service.findByServico(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/servicoativo/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<ProdutoVendido>> finByServicoAtivo(@PathVariable Integer id) {
        List<ProdutoVendido> obj = service.findByServicoAtivo(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/venda/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<ProdutoVendido>> findByVenda(@PathVariable Integer id) {
        List<ProdutoVendido> obj = service.findByVenda(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTEAGENCIA')")
    @RequestMapping(value = "/capacidade/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<ProdutoVendido>> finByTariaProduto(@PathVariable Integer id, @RequestParam Integer idProdV) {
        List<ProdutoVendido> obj = service.findByProdutoVendNaoCanceladoVagas(id, idProdV);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/produtodto/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProdutoVendidoDTO> finByProdutoDTO(@PathVariable Integer id) {
        ProdutoVendido obj = service.find(id);
        ProdutoVendidoDTO newObj = new ProdutoVendidoDTO(obj.getId(), obj.getProduto(), obj.getTarifa(), obj.getServico(), obj.getVenda(), obj.getNome(),
                obj.getValor(), obj.getStatus(), obj.getPassageiros(), obj.getOpcionaisVendidos());
        return ResponseEntity.ok().body(newObj);
    }

//    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/voucher/{id}", method = RequestMethod.GET)
    public ResponseEntity exportReport(@PathVariable Integer id) throws IOException, JRException {
        return service.voucher(id);
    }

    @RequestMapping(value = "/voucherenviar/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProdutoVendido> voucherEnviar(@PathVariable Integer id, @RequestParam String email) throws JRException, IOException, CieloRequestException {
        return service.voucherEnviar(id, email);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<ProdutoVendido>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<ProdutoVendido> list = service.findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(list);
    }
}

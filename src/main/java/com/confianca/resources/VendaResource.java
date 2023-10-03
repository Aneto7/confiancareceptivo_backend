package com.confianca.resources;

import com.confianca.cielo.ecommerce.request.CieloRequestException;
import com.confianca.domain.Passageiro;
import com.confianca.domain.Recebimento;
import com.confianca.domain.TipoPagamentoRecebimento;
import com.confianca.domain.Venda;
import com.confianca.dto.StatusDTO;
import com.confianca.services.PassageiroService;
import com.confianca.services.RecebimentoService;
import com.confianca.services.TipoPagamentoRecebimentoService;
import com.confianca.services.VendaService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.URI;
import java.text.ParseException;
import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/venda")
public class VendaResource {

    @Autowired
    private VendaService service;

    @Autowired
    private TipoPagamentoRecebimentoService tipoPagamentoRecebimentoService;

    @Autowired
    private RecebimentoService recebimentoService;

    @Autowired
    private PassageiroService passageiroService;

    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTEAGENCIA')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Venda> find(@PathVariable Integer id) {
        Venda obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTEAGENCIA')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Venda> insert(@RequestBody @Valid Venda obj, @RequestParam Integer qtdParcelas, @RequestParam String tipoRec)
            throws JRException, IOException, CieloRequestException, ParseException {
        TipoPagamentoRecebimento tipo = tipoPagamentoRecebimentoService.findByNome(tipoRec);

        //informação estática aguardando definição se será possível realizar venda parcelada
        String parcelado = "Não";
        if(qtdParcelas > 1) parcelado = "Sim";

        //Criando dados de recebimento da venda, QUANDO CRIAR TODAS AS PERCELAS AQUI?
        Recebimento rec = new Recebimento(null, tipo, null, null, obj.getNome(), null, null,
                obj.getDataVencimento(), parcelado, qtdParcelas, 1, "Ativo", new Date());

        //recebimentoService.insert(rec); //INSERINDO RECEBIMENTO
        //inserir recebimentos
        obj.setRecebimentos(Arrays.asList(rec));
        obj.setValorFinal(0.0);
        obj.setDesconto(obj.getCliente().getTipoCliente().getDesconto());

        //inserir venda
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

        List<Passageiro> passageiros = passageiroService.findByProdutoVendidoVendaId(obj.getId());

        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CLIENTEAGENCIA')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody Venda obj, @PathVariable Integer id) throws CieloRequestException, IOException, ParseException {
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

    @PreAuthorize("hasAnyRole('ADMIN','CLIENTEAGENCIA')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Venda>> findAll() {
        List<Venda> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CLIENTEAGENCIA')")
    @RequestMapping(value = "/agencia/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Venda>> findByAgencia(@PathVariable Integer id,
                                                     @RequestParam Integer idProduto,
                                                     @RequestParam String status,
                                                     @RequestParam String dataInicial,
                                                     @RequestParam String dataFinal) throws ParseException {
        List<Venda> list = service.findByClienteProdutoDataStatus(status, id, idProduto, dataInicial, dataFinal);
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CLIENTEAGENCIA')")
    @RequestMapping(value = "/agencia/datatour/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Venda>> findByAgencia(@PathVariable Integer id,
                                                     @RequestParam Integer idProduto,
                                                     @RequestParam String status,
                                                     @RequestParam String data) throws ParseException {
        List<Venda> list = service.findByClienteProdutoDataTour(status, id, idProduto, data);
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CLIENTEAGENCIA')")
    @RequestMapping(value = "/agencia/passageiro/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Venda>> findByPassageiro(@PathVariable Integer id,
                                                     @RequestParam String passageiro) throws ParseException {
        List<Venda> list = service.findByClientePassageiro(id, passageiro);
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CLIENTEAGENCIA')")
    @RequestMapping(value = "/agencia/idvenda/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Venda>> findByIdVenda(@PathVariable Integer id,
                                                        @RequestParam Integer idVenda) throws ParseException {
        List<Venda> list = service.findByIdVenda(id, idVenda);
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CLIENTEAGENCIA')")
    @RequestMapping(value = "/servico/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Venda>> findByServico(@PathVariable Integer id) throws ParseException {
        List<Venda> list = service.findByProdutosVendidosServicoId(id);
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CLIENTEAGENCIA')")
    @RequestMapping(value = "/produtovendido/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Venda>> findByProdutoVendido(@PathVariable Integer id) throws ParseException {
        List<Venda> list = service.findByProdutosVendidosId(id);
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CLIENTEAGENCIA')")
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public ResponseEntity<List<Venda>> findByData(@RequestParam String dataInicial,
                                                     @RequestParam String dataFinal) throws ParseException {
        List<Venda> list = service.findByDataVencimentoBetween(dataInicial, dataFinal);
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CLIENTEAGENCIA')")
    @RequestMapping(value = "/datastatus", method = RequestMethod.GET)
    public ResponseEntity<List<Venda>> findByDataStatus(@RequestParam String status,
                                                  @RequestParam String dataInicial,
                                                  @RequestParam String dataFinal) throws ParseException {
        List<Venda> list = service.findByStatusAndDataVencimentoBetween(status, dataInicial, dataFinal);
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public ResponseEntity<List<StatusDTO>> findStatus() {
        List<StatusDTO> list = service.findStatus();
        return ResponseEntity.ok().body(list);
    }

    //@PreAuthorize("hasAnyRole('ADMIN', 'CLIENTEAGENCIA')")
    @RequestMapping(value = "/comprovante/{id}", method = RequestMethod.GET)
    public ResponseEntity<Venda> comprovante(@PathVariable Integer id) throws JRException, IOException, CieloRequestException {
        return service.comprovante(id);
    }

    @RequestMapping(value = "/comprovanteenviar/{id}", method = RequestMethod.GET)
    public ResponseEntity<Venda> comprovanteEnviar(@PathVariable Integer id, @RequestParam String email) throws JRException, IOException, CieloRequestException {
        return service.comprovanteEnviar(id, email);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CLIENTEAGENCIA')")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<Venda>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<Venda> list = service.findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CLIENTEAGENCIA')")
    @RequestMapping(value = "helloReport1", method = RequestMethod.GET)
    @ResponseBody
    public void getRpt1(HttpServletResponse response) throws JRException, IOException {
        InputStream jasperStream = this.getClass().getResourceAsStream("/jasperreports/HelloWorld1.jasper");
        Map<String,Object> params = new HashMap<>();
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());

        response.setContentType("application/x-pdf");
        response.setHeader("Content-disposition", "inline; filename=helloWorldReport.pdf");

        final OutputStream outStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    }
}

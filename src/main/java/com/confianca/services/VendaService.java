package com.confianca.services;

import com.confianca.cielo.ecommerce.request.CieloRequestException;
import com.confianca.domain.*;
import com.confianca.dto.*;
import com.confianca.repositories.ProdutoVendidoRepository;
import com.confianca.repositories.ReceitaRepository;
import com.confianca.repositories.VendaRepository;
import com.confianca.services.exeptions.DataIntegrityException;
import com.confianca.services.exeptions.ObjectNotFoundException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Service
public class VendaService {

    @Autowired
    private VendaRepository repo;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ServicoService servicoService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoVendidoService produtoVendidoService;

    @Autowired
    private TransferService transferService;

    @Autowired
    private TarifaService tarifaService;

    @Autowired
    private PassageiroService passageiroService;

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private RecebimentoService recebimentoService;

    @Autowired
    private RecebimentoFinanceiroService recebimentoFinanceiroService;

    @Autowired
    private AuthService authService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private OpcionalVendidoService opcionalVendidoService;

    @Autowired
    private ReceitaService receitaService;

    @Autowired
    private HistoricoService historicoService;

    @Autowired
    private CupomService cupomService;

    @Autowired
    private PagadorService pagadorService;

    @Autowired
    private ProdutoVendidoRepository produtoVendidoRepository;

    public Venda find(Integer id) {
        Optional<Venda> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Venda.class.getName()));
    }

    public Venda insert(Venda obj) throws JRException, IOException, CieloRequestException, ParseException {
        List<ProdutoVendido> listaInserirProdutos = new ArrayList<>();
        Double valorTotal = 0.0;
        Double valorPorProduto = 0.0;
        Double valorOpcionais = 0.0;
        Integer adultos = 0;
        Integer crianca = 0;
        Integer bebe = 0;
        Integer idoso = 0;

        Pagador pagador = obj.getPagador();
        obj.setPagador(pagadorService.insert(pagador));
        obj.setId(null);

        //criar servico de consulta de cliente no cliente service
        obj.setCliente(clienteService.consultaCadastroCliente(obj.getCliente()));

        //criando novo cartão de crédito
        Cartao cartao = new Cartao();

        //consulta se o tipo de recebimento é cartão de crédito para criar recebimentos via cartão de crédito
        if (obj.getRecebimentos().get(0).getTipo().getNome().equals("Cartão de Crédito")) {

            cartao = new Cartao(null, obj.getCartao().getCardNumber(), obj.getCartao().getHolder(),
                    obj.getCartao().getExpirationDate(), obj.getCartao().getSecurityCode(),
                    "false", obj.getCartao().getBrand(), null);

            cartaoService.insert(cartao);

            obj.setCartao(cartao);
            obj.getRecebimentos().get(0).setValor(recebimentoService.validacaoValor(obj));
            obj.getRecebimentos().get(0).setStatus("Aguardando Pagamento");
            //TESTE IMPRIMINDO STATUS DO RECEBIMENTO
        } else {
            obj.setCartao(null);
        }

        //salvando informações da venda
        obj.setEmissao(new Date());
        obj = repo.save(obj);
        historicoService.insertAny(obj, "Inserir");

        //criando rotina para cada produto vendido
        for (ProdutoVendido prod : obj.getProdutosVendidos()) {
            //inserindo informações nos produtos vendidos
            Produto novoProdutoVenda = produtoService.find(prod.getId());

            List<Tarifa> buscaTarifa = tarifaService.findByStatusAndProdutoIdAndDataData(prod.getId(),
                    prod.getTarifa().getData().getData());

            prod.setTarifa(buscaTarifa.get(0));
            prod.setProduto(novoProdutoVenda);
            prod.setVenda(obj);

            //salvando produto vendido
            produtoVendidoService.insert(prod);

            //verificando se há opcionais para cada produto vendido e inserindo as informações
            for (OpcionalVendido opVendido : prod.getOpcionaisVendidos()) {
                opVendido.setProdutoVendido(prod);
                opcionalVendidoService.insert(opVendido);
            }

            bebe = 0;
            crianca = 0;
            adultos = 0;
            idoso = 0;

            //consulta de informações dos passageiros
            for (Passageiro passageiro : prod.getPassageiros()) {
                valorOpcionais = 0.0;

                //validação de data de nascimento para inserir como adulto, criança ou bebê
                Calendar dateOfBirth = new GregorianCalendar();
                dateOfBirth.setTime(passageiro.getNascimento());
                Calendar today = Calendar.getInstance();
                int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
                if (today.before(dateOfBirth)) {
                    age--;
                }
                if (age < 2) {
                    bebe++;
                } else if (age < 11) {
                    crianca++;
                } else if (age < 60) {
                    adultos++;
                } else if (age >= 60) {
                    idoso++;
                }
                passageiro.setProdutoVendido(prod);
                passageiroService.insert(passageiro);
            }

            //buscando valor total dos opcionais
            for (OpcionalVendido opcionalVendido : prod.getOpcionaisVendidos()) {
                valorOpcionais = valorOpcionais + (opcionalVendido.getValor() * opcionalVendido.getQuantidade());
            }

            //rotina de cupons, buscar qual o maior cupom entre produto, tarifa e cliente
            Double cupom = cupomService.valorCupom(obj, prod);

            if (prod.getProduto().getProdutoAlterarValor()) {
                valorPorProduto = prod.getValor();
            } else {
                if (prod.getProduto().getIndividual()) {
                    if (prod.getProduto().getTipoProduto().getNome().equalsIgnoreCase("Transfer")) {
                        valorPorProduto = (prod.getTrechoProduto().getValor() * (1 - (cupom))) + valorOpcionais;
                    } else {
                        valorPorProduto = (prod.getTarifa().getValor() * (1 - (cupom))) + valorOpcionais;
                    }
                } else {
                    valorPorProduto = (((prod.getTarifa().getValor() * adultos)
                            + (prod.getTarifa().getValorCrianca() * crianca)
                            + (prod.getTarifa().getValorBebe() * bebe)
                            + (prod.getTarifa().getValorIdoso() * idoso))
                            * (1 - (cupom)))
                            + valorOpcionais;
                }
            }

//            valorPorProduto = (((prod.getTarifa().getValor() * adultos)
//                    + (prod.getTarifa().getValorCrianca() * crianca)
//                    + (prod.getTarifa().getValorBebe() * bebe))
//                    * (1-(cupomProduto + cupomTarifa)))
//                    + valorOpcionais;

            prod.setValor(valorPorProduto);
            produtoVendidoService.update(prod);
            listaInserirProdutos.add(produtoVendidoService.find(prod.getId()));

            valorTotal = valorTotal + valorPorProduto;
        }
        obj.setValor(valorTotal);
        obj.setDesconto(obj.getCliente().getTipoCliente().getDesconto());

        obj.setValorFinal(valorTotal * (1 - (obj.getCliente().getTipoCliente().getDesconto())));
        obj.setProdutosVendidos(listaInserirProdutos);
        if (obj.getRecebimentos() != null && obj.getRecebimentos().get(0).getTipo().getNome().equals("Cartão de Crédito")) {
            obj = recebimentoService.criandoRecebimentoCartao(obj);
        } else {
            obj = recebimentoService.criandoRecebimentoFaturado(obj);
        }

        update(obj);
        if (obj.getRecebimentos() != null && obj.getRecebimentos().get(0).getTipo().getNome().equals("Cartão de Crédito")) {
            obj.setCartao(null);
            obj = repo.save(obj);
            historicoService.insertAny(obj, "Inserir");
            cartao.setVendas(null);
            cartaoService.delete(cartao.getId());
        }
        return obj;
    }

    public Venda update(Venda obj) throws CieloRequestException, IOException, ParseException {
        Venda newObj = find(obj.getId());
        if (!newObj.getStatus().equals(obj.getStatus())) {
            for (ProdutoVendido prod : obj.getProdutosVendidos()) {
                prod.setStatus(obj.getStatus());
                produtoVendidoService.update(prod);
                for (OpcionalVendido opcionais : prod.getOpcionaisVendidos()) {
                    opcionais.setStatus(obj.getStatus());
                    opcionalVendidoService.update(opcionais);
                }
                for (Passageiro pass : prod.getPassageiros()) {
                    pass.setStatus(obj.getStatus());
                    passageiroService.update(pass);
                }
                if (prod.getProduto().getIndividual()) {
                    Servico servicoObj = servicoService.find(prod.getServico().getId());
                    servicoObj.setStatus(obj.getStatus());
                    servicoService.update(servicoObj);

                    Transfer transferObj = transferService.findByServicoId(prod.getServico().getId());
                    if (transferObj != null) {
                        transferObj.setStatus(obj.getStatus());
                        transferService.update(transferObj);
                    }

                }
            }
            List<Receita> receitasListadas = receitaService.findByVenda(obj.getId());
            for (Receita recei : receitasListadas) {
                recei.setStatus(obj.getStatus());
                receitaService.update(recei, false);
            }

            List<Recebimento> recebimentosListados = recebimentoService.findByVenda(obj.getId());
            for (Recebimento receb : recebimentosListados) {
                receb.setStatus(obj.getStatus());
                recebimentoService.update(receb);
            }

            List<RecebimentoFinanceiro> recebimentosFinanceirosListados = recebimentoFinanceiroService.findByVenda(obj.getId());
            for (RecebimentoFinanceiro receb : recebimentosFinanceirosListados) {
                receb.setStatus(obj.getStatus());
                recebimentoFinanceiroService.update(receb, false);
            }
        }
        updateData(newObj, obj);
        newObj = repo.save(newObj);
        historicoService.insertAny(obj, "Atualizar");
        return newObj;
    }

    public void delete(Integer id) {
        find(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um cartão que possui lançamentos");
        }
    }

    public List<Venda> findAll() {
        return repo.findAll();
    }

    public List<Venda> findByClienteProdutoDataStatus(String status, Integer idCliente, Integer idProduto, String dataI, String dataF) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dataInicial = formatter.parse(dataI);
        Date dataFinal = formatter.parse(dataF);
        List<Venda> vendas = new ArrayList<>();
        if (idProduto == 0) {
            vendas = repo.findByStatusContainingAndClienteIdAndDataVencimentoBetween(
                    status, idCliente, dataInicial, dataFinal);
        } else {
            vendas = repo.findByStatusContainingAndProdutosVendidosProdutoIdAndClienteIdAndDataVencimentoBetween(
                    status, idProduto, idCliente, dataInicial, dataFinal);
        }
        return vendas;
    }

    public List<Venda> findByClienteProdutoDataTour(String status, Integer idCliente, Integer idProduto, String data) throws ParseException {
        String dataInicial = data + " 00:00:00";
        String dataFinal = data + " 23:59:59";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dataIni = formatter.parse(dataInicial);
        Date dataFin = formatter.parse(dataFinal);
        List<Venda> vendas = new ArrayList<>();
        if (idProduto == 0) {
            vendas = repo.findByStatusContainingAndClienteIdAndProdutosVendidosServicoDataInBetween(
                    status, idCliente, dataIni, dataFin);
        } else {
            vendas = repo.findByStatusContainingAndProdutosVendidosProdutoIdAndClienteIdAndProdutosVendidosServicoDataInBetween(
                    status, idProduto, idCliente, dataIni, dataFin);
        }
        return vendas;
    }

    public List<Venda> findByClientePassageiro(Integer idCliente, String passageiro) throws ParseException {
        return repo.findByClienteIdAndProdutosVendidosPassageirosNomeContaining(idCliente, passageiro);
    }

    public List<Venda> findByIdVenda(Integer idCliente, Integer idVenda) throws ParseException {
        return repo.findByIdAndClienteId(idVenda, idCliente);
    }

    public List<Venda> findByProdutosVendidosServicoId(Integer idServico) throws ParseException {
        return repo.findByProdutosVendidosServicoId(idServico);
    }

    public List<Venda> findByProdutosVendidosId(Integer idProdutoVendido) throws ParseException {
        return repo.findByProdutosVendidosId(idProdutoVendido);
    }

    public List<Venda> findByStatusAndDataVencimentoBetween(String status, String dataI, String dataF) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dataInicial = formatter.parse(dataI);
        Date dataFinal = formatter.parse(dataF);
        return repo.findByStatusAndDataVencimentoBetween(status, dataInicial, dataFinal);
    }

    public List<Venda> findByDataVencimentoBetween(String dataI, String dataF) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dataInicial = formatter.parse(dataI);
        Date dataFinal = formatter.parse(dataF);
        return repo.findByDataVencimentoBetween(dataInicial, dataFinal);
    }

    public List<StatusDTO> findStatus() {
        return repo.findStatus();
    }

    public Page<Venda> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(Venda newObj, Venda obj) {
        newObj.setCliente(obj.getCliente());
        newObj.setUsuario(obj.getUsuario());
        newObj.setNome(obj.getNome());
        newObj.setValor(obj.getValor());
        newObj.setStatus(obj.getStatus());
        newObj.setPagamento(obj.getPagamento());
        newObj.setDataPagamento(obj.getDataPagamento());
        newObj.setDataVencimento(obj.getDataVencimento());
        newObj.setComprovante(obj.getComprovante());
        newObj.setObs(obj.getObs());
        newObj.setRecebimentos(null);
        newObj.setProdutosVendidos(null);
    }

    public ResponseEntity comprovante(Integer id) throws IOException, JRException {
        Optional<Venda> venda = repo.findById(id);

        List<ComprovanteVendaDTO> comprovantesVenda = repo.findByComprovante(id);
        List<ProdutoVendidoVendaDTO> produtosVendidos = new ArrayList<>();
        List<OpcionalVendidoDTO> opcionaisVendidos = new ArrayList<>();

        for (ProdutoVendido produtoVendido : venda.get().getProdutosVendidos()) {
            ProdutoVendidoVendaDTO produtoVendidoDTO = new ProdutoVendidoVendaDTO(produtoVendido);
            produtosVendidos.add(produtoVendidoDTO);
            for (OpcionalVendido opcionalVendido : produtoVendido.getOpcionaisVendidos()) {
                OpcionalVendidoDTO opcionalVendidoDTO = new OpcionalVendidoDTO(opcionalVendido);
                opcionaisVendidos.add(opcionalVendidoDTO);
            }
        }

        for (ComprovanteVendaDTO comprovanteVendaDTO : comprovantesVenda) {
            List<ProdutoVendidoVendaDTO> produtosVendidosVendaDTO = new ArrayList<>();
            for (ProdutoVendidoVendaDTO produtoVendidoVendaDTO : produtosVendidos) {
                produtosVendidosVendaDTO.add(produtoVendidoVendaDTO);
            }
            List<OpcionalVendidoDTO> opcionaisVendidosDTO = new ArrayList<>();
            for (OpcionalVendidoDTO opcionalVendidoDTO : opcionaisVendidos) {
                opcionaisVendidosDTO.add(opcionalVendidoDTO);
            }
            comprovanteVendaDTO.setProdutosVendidosDTO(produtosVendidosVendaDTO);
            comprovanteVendaDTO.setOpcionaisDTO(opcionaisVendidosDTO);
        }

        // configurando a fonte
        String fontPath = this.getClass().getResource("/fonts/arial.ttf").getPath();
        Font font = new Font("Arial", Font.BOLD, 10);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("REPORT_FONT", font);
        //parameters.put("passageiros", passageiros);

        // compilando o relatório
        File file = ResourceUtils.getFile("classpath:ComprovVenda.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        // preenchendo os dados
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(comprovantesVenda);

        // gerando o relatório em memória
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // exportando o relatório para PDF
        byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "relatorio.pdf");
        headers.setContentLength(pdf.length);

        return new ResponseEntity(pdf, headers, HttpStatus.OK);
    }

    public ResponseEntity comprovanteEnviar(Integer id, String email) throws IOException, JRException {
        Optional<Venda> venda = repo.findById(id);

        System.out.println("imprimindo email: " + email);

        List<ComprovanteVendaDTO> comprovantesVenda = repo.findByComprovante(id);
        List<ProdutoVendidoVendaDTO> produtosVendidos = new ArrayList<>();
        List<OpcionalVendidoDTO> opcionaisVendidos = new ArrayList<>();
        List<byte[]> pdfsVouchers = new ArrayList<>();

        for (ProdutoVendido produtoVendido : venda.get().getProdutosVendidos()) {
            byte[] pdfProdutoVendido = voucherEmailVenda(produtoVendido.getId());
            pdfsVouchers.add(pdfProdutoVendido);
            ProdutoVendidoVendaDTO produtoVendidoDTO = new ProdutoVendidoVendaDTO(produtoVendido);
            produtosVendidos.add(produtoVendidoDTO);
            for (OpcionalVendido opcionalVendido : produtoVendido.getOpcionaisVendidos()) {
                OpcionalVendidoDTO opcionalVendidoDTO = new OpcionalVendidoDTO(opcionalVendido);
                opcionaisVendidos.add(opcionalVendidoDTO);
            }
        }

        for (ComprovanteVendaDTO comprovanteVendaDTO : comprovantesVenda) {
            List<ProdutoVendidoVendaDTO> produtosVendidosVendaDTO = new ArrayList<>();
            for (ProdutoVendidoVendaDTO produtoVendidoVendaDTO : produtosVendidos) {
                produtosVendidosVendaDTO.add(produtoVendidoVendaDTO);
            }
            List<OpcionalVendidoDTO> opcionaisVendidosDTO = new ArrayList<>();
            for (OpcionalVendidoDTO opcionalVendidoDTO : opcionaisVendidos) {
                opcionaisVendidosDTO.add(opcionalVendidoDTO);
            }
            comprovanteVendaDTO.setProdutosVendidosDTO(produtosVendidosVendaDTO);
            comprovanteVendaDTO.setOpcionaisDTO(opcionaisVendidosDTO);
        }

        // configurando a fonte
        String fontPath = this.getClass().getResource("/fonts/arial.ttf").getPath();
        Font font = new Font("Arial", Font.BOLD, 10);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("REPORT_FONT", font);
        //parameters.put("passageiros", passageiros);

        // compilando o relatório
        File file = ResourceUtils.getFile("classpath:ComprovVenda.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        // preenchendo os dados
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(comprovantesVenda);

        // gerando o relatório em memória
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // exportando o relatório para PDF
        byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

        emailService.sendEmailComprovanteVenda(venda.get(), email, pdf, pdfsVouchers);

        return ResponseEntity.ok().body(venda);
    }

    public byte[] voucherEmailVenda(Integer idProdutoVendido) throws IOException, JRException {
        ProdutoVendido prodVendido = produtoVendidoService.find(idProdutoVendido);

//        List<ProdutoVendidoVoucherDTO> produtoVendido = produtoVendidoRepository.findByVoucher(idProdutoVendido);
        List<ProdutoVendidoVoucherDTO> produtoVendido = new ArrayList<>();
        ProdutoVendidoVoucherDTO pVVDTO = new ProdutoVendidoVoucherDTO(prodVendido);
        produtoVendido.add(pVVDTO);

        List<PassageiroDTO> passageiros = new ArrayList<>();
        List<DetalhesProdutoDTO> detalhesProdutoDTO = new ArrayList<>();
        List<OpcionalVendidoDTO> opcionaisVendidosDTO = new ArrayList<>();

        for (Passageiro passageiro : prodVendido.getPassageiros()) {
            PassageiroDTO passageiroDTO = new PassageiroDTO(passageiro);
            passageiros.add(passageiroDTO);
        }

        for (DetalheProduto detalheProduto : prodVendido.getProduto().getDetalhesProdutos()) {
            DetalhesProdutoDTO dProdutoDTO = new DetalhesProdutoDTO(detalheProduto);
            detalhesProdutoDTO.add(dProdutoDTO);
        }

        for (OpcionalVendido opcional : prodVendido.getOpcionaisVendidos()) {
            OpcionalVendidoDTO opcionalDTO = new OpcionalVendidoDTO(opcional);
            opcionaisVendidosDTO.add(opcionalDTO);
        }

        for (ProdutoVendidoVoucherDTO pvDTO : produtoVendido) {
            List<PassageiroDTO> passageirosDTO = new ArrayList<>();
            for (PassageiroDTO passageiroDTO : passageiros) {
                passageirosDTO.add(passageiroDTO);
            }
            List<DetalhesProdutoDTO> detalhesProdutoDTOS = new ArrayList<>();
            for (DetalhesProdutoDTO detalhes : detalhesProdutoDTO) {
                detalhesProdutoDTOS.add(detalhes);
            }
            List<OpcionalVendidoDTO> opcionaisDTO = new ArrayList<>();
            for (OpcionalVendidoDTO opcionalDTO : opcionaisVendidosDTO) {
                opcionaisDTO.add(opcionalDTO);
            }
            pvDTO.setPassageirosDTO(passageirosDTO);
            pvDTO.setDetalhesProdutoDTO(detalhesProdutoDTOS);
            pvDTO.setOpcionaisDTO(opcionaisDTO);
        }

        // configurando a fonte
        String fontPath = this.getClass().getResource("/fonts/arial.ttf").getPath();
        Font font = new Font("Arial", Font.BOLD, 10);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("REPORT_FONT", font);

        // compilando o relatório
        File file = ResourceUtils.getFile("classpath:VoucherProdutoVendido.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        // preenchendo os dados
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(produtoVendido);

        // gerando o relatório em memória
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // exportando o relatório para PDF
        byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

        return pdf;
    }

}

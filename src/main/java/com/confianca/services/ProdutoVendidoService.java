package com.confianca.services;

import com.confianca.domain.*;
import com.confianca.dto.*;
import com.confianca.repositories.ProdutoVendidoRepository;
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
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Service
public class ProdutoVendidoService {
    @Autowired
    private ProdutoVendidoRepository repo;
    @Autowired
    private PassageiroService passageiroService;
    @Autowired
    private TransferService transferService;
    @Autowired
    private ServicoService servicoService;
    @Autowired
    private HistoricoService historicoService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ReceitaService receitaService;

    public ProdutoVendido find(Integer id) {
        Optional<ProdutoVendido> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + ProdutoVendido.class.getName()));
    }

    public List<ProdutoVendido> findByServico(Integer id) {
        List<ProdutoVendido> objTeste = repo.findByServicoId(id);
        return objTeste;
    }

    public List<ProdutoVendido> findByServicoAtivo(Integer id) {
        List<ProdutoVendido> objTeste = repo.findByServicoAtivo(id, "Cancelado");
        return objTeste;
    }

    public List<ProdutoVendido> findByVenda(Integer id) {
        List<ProdutoVendido> objTeste = repo.findByVendaId(id);
        return objTeste;
    }

    public List<ProdutoVendido> findByTariaProduto(Integer idTarifa, Integer idProdV) {
        List<ProdutoVendido> objTeste = repo.findByTarifaIdAndProdutoId(idTarifa, idProdV);
        return objTeste;
    }

    public List<ProdutoVendido> findByProdutoVendNaoCanceladoVagas(Integer idTarifa, Integer idProdV) {
        List<ProdutoVendido> objTeste = repo.findByProdutoVend(idTarifa, idProdV, "Cancelado");
        return objTeste;
    }

    public List<ProdutoVendido> findServicoCadastrado(ProdutoVendido obj) {
        List<ProdutoVendido> objTeste = repo.findByTarifaDataIdAndProdutoId(obj.getTarifa().getData().getId(), obj.getProduto().getId());
        return objTeste;
    }

    public List<ProdutoVendido> findByDataProduto(Integer idProduto, String dataI, String dataF) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dataInicial = formatter.parse(dataI);
        Date dataFinal = formatter.parse(dataF);
        List<ProdutoVendido> objTeste = repo.findByProdutoIdAndTarifaDataDataBetween(idProduto, dataInicial, dataFinal);
        return objTeste;
    }

    public List<ProdutoVendido> findByData(String dataI, String dataF) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dataInicial = formatter.parse(dataI);
        Date dataFinal = formatter.parse(dataF);
        List<ProdutoVendido> objTeste = repo.findByTarifaDataDataBetween(dataInicial, dataFinal);
        return objTeste;
    }


    public ProdutoVendido insert(ProdutoVendido obj) {
        obj.setId(null);
        obj = repo.save(obj);
        historicoService.insertAny(obj, "Inserir");
        return obj;
    }

    public ProdutoVendido update(ProdutoVendido obj) throws ParseException {
        ProdutoVendido newObj = find(obj.getId());
        String pontoSaida = null;
        updateData(newObj, obj);
        for (Passageiro pass : obj.getPassageiros()) {
            passageiroService.update(pass);
        }
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

    public List<ProdutoVendido> findAll() {
        return repo.findAll();
    }

    public Page<ProdutoVendido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(ProdutoVendido newObj, ProdutoVendido obj) throws ParseException {
        obj.setVenda(newObj.getVenda());
        if (!newObj.getTarifa().getId().equals(obj.getTarifa().getId())) {
            obj = updateAlterarData(obj);
        }
        //newObj.setServico(obj.getServico());

        //newObj.setProduto(obj.getProduto());
        newObj.setTarifa(obj.getTarifa());
        //newObj.setVenda(obj.getVenda());
        newObj.setNome(obj.getNome());
        newObj.setValor(obj.getValor());
        newObj.setStatus(obj.getStatus());
        newObj.setDataIda(obj.getDataIda());
        newObj.setDataVolta(obj.getDataVolta());
        newObj.setIdaEVolta(obj.getIdaEVolta());
    }

    private ProdutoVendido updateAlterarData(ProdutoVendido obj) throws ParseException {
        Servico servicoRecebimento = new Servico();

        List<ProdutoVendido> consultProdV = findServicoCadastrado(obj);
        if (consultProdV.isEmpty()) {
            List<Servico> servicoRec = servicoService.findByDataInAndProdutoId(obj.getTarifa().getData().getData(), obj.getProduto().getId());
            if (!servicoRec.isEmpty()) {
                servicoRecebimento = servicoRec.get(0);
            } else {
                servicoRecebimento = null;
            }
        } else {
            servicoRecebimento = consultProdV.get(0).getServico();
        }

        if (servicoRecebimento != null && obj.getProduto().getIndividual() == false) {
            servicoRecebimento.getProdutosVendidos().add(obj);

            servicoService.update(servicoRecebimento);

            List<Receita> receitas = receitaService.findByProdutoVendido(obj.getId());
            for (Receita receita:receitas) {
                receita.setServico(servicoRecebimento);
                receitaService.update(receita, false);
            }

            obj.setServico(servicoRecebimento);
            repo.save(obj);
        } else {

            //Criando o identificador do servico
            String identificadorServico = obj.getTarifa().getData().getData().toString();
            identificadorServico = "." + identificadorServico.substring(5, 7) + "/" + identificadorServico.substring(2, 4);
            identificadorServico = servicoService.findParteIdentificador(identificadorServico) + identificadorServico;

            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date data = obj.getTarifa().getData().getData();

            if (obj.getProduto().getTipoProduto().getNome().equalsIgnoreCase("Transfer")) {
                data = obj.getDataIda();
            } else {
                data = obj.getTarifa().getData().getData();
            }

            List<ProdutoVendido> listProdV = Arrays.asList(obj);
            servicoRecebimento = new Servico(null, null, obj.getProduto().getTipoProduto(), obj.getServico().getCliente(),
                    obj.getServico().getCliente().getNome(), obj.getProduto().getNome(),
                    obj.getTarifa().getData().getData(), data,
                    null, identificadorServico, "Ativo", null, null, null, listProdV,
                    obj.getProduto(), new Date());

            servicoRecebimento = servicoService.insert(servicoRecebimento);

            List<Receita> receitas = receitaService.findByProdutoVendido(obj.getId());
            for (Receita receita:receitas) {
                receita.setServico(servicoRecebimento);
                receitaService.update(receita, false);
            }

            obj.setServico(servicoRecebimento);
            repo.save(obj);

            Transfer transfer = new Transfer(null, null, obj.getProduto().getPontoDeSaida(),
                    obj.getServico(),
                    obj.getServico().getCliente().getNome() + " - " + obj.getProduto().getNome() + " - " + obj.getTarifa().getData().getData(),
                    data, null, null, null, obj.getProduto().getNome(),
                    obj.getPassageiros().get(0).getNome(), obj.getServico().getCliente().getNome(),
                    false, "Ativo", null, new Date());
            transferService.insert(transfer);
        }
        return obj;

    }


    public ResponseEntity voucher(Integer idProdutoVendido) throws IOException, JRException {
        Optional<ProdutoVendido> prodVendido = repo.findById(idProdutoVendido);

//        List<ProdutoVendidoVoucherDTO> produtoVendido = repo.findByVoucher(idProdutoVendido);
        List<ProdutoVendidoVoucherDTO> produtoVendido = new ArrayList<>();
        ProdutoVendidoVoucherDTO pVVDTO = new ProdutoVendidoVoucherDTO(prodVendido.get());
        produtoVendido.add(pVVDTO);

        List<PassageiroDTO> passageiros = new ArrayList<>();
        List<DetalhesProdutoDTO> detalhesProdutoDTO = new ArrayList<>();
        List<OpcionalVendidoDTO> opcionaisVendidosDTO = new ArrayList<>();

        for (Passageiro passageiro : prodVendido.get().getPassageiros()) {
            PassageiroDTO passageiroDTO = new PassageiroDTO(passageiro);
            passageiros.add(passageiroDTO);
        }

        if (!prodVendido.get().getProduto().getDetalhesProdutos().isEmpty()) {
            for (DetalheProduto detalheProduto : prodVendido.get().getProduto().getDetalhesProdutos()) {
                DetalhesProdutoDTO dProdutoDTO = new DetalhesProdutoDTO(detalheProduto);
                detalhesProdutoDTO.add(dProdutoDTO);
            }
        }

        if (!prodVendido.get().getOpcionaisVendidos().isEmpty()) {
            for (OpcionalVendido opcional : prodVendido.get().getOpcionaisVendidos()) {
                OpcionalVendidoDTO opcionalDTO = new OpcionalVendidoDTO(opcional);
                opcionaisVendidosDTO.add(opcionalDTO);
            }
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
        //parameters.put("passageiros", passageiros);

        // compilando o relatório
        File file = ResourceUtils.getFile("classpath:VoucherProdutoVendido.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        // preenchendo os dados
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(produtoVendido);

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

    public ResponseEntity voucherEnviar(Integer idProdutoVendido, String email) throws IOException, JRException {
        Optional<ProdutoVendido> prodVendido = repo.findById(idProdutoVendido);

//        List<ProdutoVendidoVoucherDTO> produtoVendido = repo.findByVoucher(idProdutoVendido);
        List<ProdutoVendidoVoucherDTO> produtoVendido = new ArrayList<>();
        ProdutoVendidoVoucherDTO pVVDTO = new ProdutoVendidoVoucherDTO(prodVendido.get());
        produtoVendido.add(pVVDTO);

        List<PassageiroDTO> passageiros = new ArrayList<>();
        List<DetalhesProdutoDTO> detalhesProdutoDTO = new ArrayList<>();
        List<OpcionalVendidoDTO> opcionaisVendidosDTO = new ArrayList<>();

        for (Passageiro passageiro : prodVendido.get().getPassageiros()) {
            PassageiroDTO passageiroDTO = new PassageiroDTO(passageiro);
            passageiros.add(passageiroDTO);
        }

        for (DetalheProduto detalheProduto : prodVendido.get().getProduto().getDetalhesProdutos()) {
            DetalhesProdutoDTO dProdutoDTO = new DetalhesProdutoDTO(detalheProduto);
            detalhesProdutoDTO.add(dProdutoDTO);
        }

        for (OpcionalVendido opcional : prodVendido.get().getOpcionaisVendidos()) {
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
        List<byte[]> pdfsVouchers = new ArrayList<>();
        pdfsVouchers.add(pdf);

        emailService.sendEmailVoucher(prodVendido.get(), email, pdf);

        return ResponseEntity.ok().body(produtoVendido);
    }
}

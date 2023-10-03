package com.confianca.services;

import com.confianca.domain.*;
import com.confianca.dto.FechamentoDTO;
import com.confianca.dto.PassageiroDTO;
import com.confianca.dto.ServicoDTO;
import com.confianca.dto.StatusDTO;
import com.confianca.repositories.ServicoRepository;
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
import org.springframework.security.access.method.P;
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
public class ServicoService {
    private String identificadorBase;

    @Autowired
    private ServicoRepository repo;

    @Autowired
    private HistoricoService historicoService;

    public Servico find(Integer id) {
        Optional<Servico> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Servico.class.getName()));
    }

    public List<Servico> findByGuia(Integer id) {
        return repo.findByGuiaId(id);
    }

    public Optional<Servico> findByIdentificador(String identificador) {
        return repo.findByIdentificador(identificador);
    }

    public String findParteIdentificador(String parteIdentificado) {
        System.out.println("IMPRIMINDO PARTE DO IDENTIFICADOR " + parteIdentificado);
        List<Servico> obj = repo.findParteIdentificador("%" + parteIdentificado + "%");
        Integer idList = 0;
        String numeroidentificador = "";
        if (obj.isEmpty()) {
            idList = 1;
        } else {
            String parteId = obj.get(0).getIdentificador();
            System.out.println("TESTE IMPRIMINDO IDENTIFICADOR: " + parteId);
            if (parteId.length() == 6) {
                idList = 1;
                System.out.println("TESTE IMPRIMINDO SE NAO TIVER IFLIST: " + idList);
            } else {
                idList = Integer.parseInt(parteId.substring(0, parteId.length() - 6)) + 1;
            }
        }

        if (idList < 10) {
            numeroidentificador = "00" + idList.toString();
        } else if (idList < 100) {
            numeroidentificador = "0" + idList.toString();
        } else {
            numeroidentificador = idList.toString();
        }

        return numeroidentificador;
    }

    public List<Servico> findByDataInBetween(String dataI, String dataF) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dataInicial = formatter.parse(dataI);
        Date dataFinal = formatter.parse(dataF);
        return repo.findByDataInBetween(dataInicial, dataFinal);
    }

    public List<Servico> findByStatusAndDataInBetween(String status, String dataI, String dataF) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dataInicial = formatter.parse(dataI);
        Date dataFinal = formatter.parse(dataF);
        return repo.findByStatusAndDataInBetween(status, dataInicial, dataFinal);
    }

    public List<Servico> findByDataInAndProdutoId(Date dataIn, Integer idProduto) throws ParseException {
        return repo.findByDataInAndProdutoId(dataIn, idProduto);
    }

    public List<FechamentoDTO> findByFechamento(String dataI, String dataF, String cliente, String fornecedor, String tipoProduto) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dataInicial = formatter.parse(dataI + " 00:00:00");
        Date dataFinal = formatter.parse(dataF + " 23:59:59");

        List<FechamentoDTO> fechamentoDTOS = new ArrayList<>();

        List<Servico> servicos = repo.findByDataInBetween(dataInicial, dataFinal);
        Double somaReceita = 0.0;
        Double somaDespesa = 0.0;
        for (Servico serv : servicos) {
            if (!serv.getStatus().equalsIgnoreCase("Cancelado")) {
                somaReceita = 0.0;
                somaDespesa = 0.0;
                for (Receita rece : serv.getReceitas()) {
                    if (!rece.getStatus().equalsIgnoreCase("Cancelado")) {
                        somaReceita = somaReceita + rece.getValor();
                    }
                }
                for (Despesa desp : serv.getDespesas()) {
                    if (!desp.getStatus().equalsIgnoreCase("Cancelado")) {
                        somaDespesa = somaDespesa + desp.getValor();
                    }
                }
                fechamentoDTOS.add(new FechamentoDTO(serv, somaReceita, somaDespesa));
            }
        }
        return fechamentoDTOS;
    }

    public Servico insert(Servico obj) {
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        String identificadorServico = form.format(obj.getDataIn()).toString();
        identificadorServico = "." + identificadorServico.substring(5, 7) + "/" + identificadorServico.substring(2, 4);
        identificadorServico = findParteIdentificador(identificadorServico) + identificadorServico;
        obj.setId(null);
        obj.setEmissao(new Date());
        obj.setIdentificador(identificadorServico);
        repo.save(obj);
        historicoService.insertAny(obj, "Inserir");
        return obj;
    }

    public Servico update(Servico obj) {
        Servico newObj = find(obj.getId());

        if (obj.getDataIn().getYear() != newObj.getDataIn().getYear() || obj.getDataIn().getMonth() != newObj.getDataIn().getMonth()) {
            SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
            String identificadorServico = form.format(obj.getDataIn()).toString();
            identificadorServico = "." + identificadorServico.substring(5, 7) + "/" + identificadorServico.substring(2, 4);
            identificadorServico = findParteIdentificador(identificadorServico) + identificadorServico;
            obj.setIdentificador(identificadorServico);
        } else {
            obj.setIdentificador(newObj.getIdentificador());
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

    public List<Servico> findAll() {
        return repo.findAll();
    }

    public List<StatusDTO> findStatus() {
        return repo.findStatus();
    }

    public Page<Servico> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(Servico newObj, Servico obj) {
        newObj.setGuia(obj.getGuia());
        newObj.setTipoProduto(obj.getTipoProduto());
        newObj.setCliente(obj.getCliente());
        newObj.setNomeCliente(obj.getNomeCliente());
        newObj.setNome(obj.getNome());
        newObj.setData(obj.getData());
        newObj.setDataIn(obj.getDataIn());
        newObj.setDataOut(obj.getDataOut());
        newObj.setIdentificador(obj.getIdentificador());
        newObj.setStatus(obj.getStatus());
        newObj.setOrdemServico(obj.getOrdemServico());
    }

    public ResponseEntity imprimirOs(Integer idServico) throws IOException, JRException {
        Optional<Servico> servico = repo.findById(idServico);

        List<ServicoDTO> servicosDTO = repo.findByOS(idServico);
        List<PassageiroDTO> passDTO = new ArrayList<>();
        for (ProdutoVendido produtoVendido : servico.get().getProdutosVendidos()) {
            for (Passageiro passageiro : produtoVendido.getPassageiros()) {
                PassageiroDTO passageiroDTO = new PassageiroDTO(passageiro);
                passDTO.add(passageiroDTO);
            }
        }
        for (ServicoDTO servDto : servicosDTO) {
            servDto.setPassageirosDTO(passDTO);
            if (servico.get().getProduto() != null) {
                servDto.setProduto(servico.get().getProduto().getNome());
                servDto.setObs(servico.get().getProduto().getObs());

            }
            if (!passDTO.isEmpty() && passDTO != null) {
                servDto.setClienteNome(passDTO.get(0).getNome());
                System.out.println("Imprimindo nome do passageiro: " + passDTO.get(0).getNome());
            }
        }

        // configurando a fonte
        String fontPath = this.getClass().getResource("/fonts/arial.ttf").getPath();
        Font font = new Font("Arial", Font.BOLD, 10);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("REPORT_FONT", font);

        // compilando o relatório
        File file = ResourceUtils.getFile("classpath:OS.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        // preenchendo os dados
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(servicosDTO);

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
}

package com.confianca.services;

import com.confianca.domain.DataTarifa;
import com.confianca.domain.ProdutoVendido;
import com.confianca.domain.Tarifa;
import com.confianca.domain.Venda;
import com.confianca.repositories.TarifaRepository;
import com.confianca.services.exeptions.DataIntegrityException;
import com.confianca.services.exeptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class TarifaService {

    @Autowired
    private TarifaRepository repo;

    @Autowired
    private DataTarifaService dataTarifaService;

    @Autowired
    private  HistoricoService historicoService;

    private Boolean possuiVenda;
    private Boolean eDiaSelecionado;
    private Boolean possuiTarifa;

    public Tarifa find(Integer id) {
        Optional<Tarifa> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Tarifa.class.getName()));
    }

    public List<Tarifa> findByStatusAndProdutoIdAndDataData(Integer produtoId, Date data) {
        List<Tarifa> obj = repo.findByStatusAndProdutoIdAndDataData("Ativo", produtoId, data);
        return obj;
    }

    public List<Tarifa> findByData(String dataI, String dataF) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dataInicial = formatter.parse(dataI);
        Date dataFinal = formatter.parse(dataF);
        List<Tarifa> obj = repo.findByDataDataBetween(dataInicial, dataFinal);
        return obj;
    }

    public Tarifa insert(Tarifa obj) {
        obj.setId(null);
        obj = repo.save(obj);
        historicoService.insertAny(obj, "Inserir");
        return obj;
    }

    public Tarifa insertVarios(Tarifa obj, String periodicidade, String diasSelecionados, String dataInicio, String dataFim) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date inicio = sdf.parse(dataInicio);
            Date fim = sdf.parse(dataFim);
            Calendar cal = Calendar.getInstance();
            cal.setTime(fim);
            cal.add(Calendar.DAY_OF_MONTH, 1);
            fim = cal.getTime();
            for (int i = 0; inicio.before(fim); i++) {
                LocalDate localDate = LocalDate.of(inicio.getYear() + 1900, inicio.getMonth() + 1, inicio.getDate());
                List<Tarifa> tarifasList = repo.findByStatusAndProdutoIdAndDataData("Ativo", obj.getProduto().getId(), inicio);
                String diaDaSemana = String.valueOf(localDate.getDayOfWeek());

                if (diaDaSemana == "SUNDAY") diaDaSemana = "Domingo";
                if (diaDaSemana == "MONDAY") diaDaSemana = "Segunda";
                if (diaDaSemana == "TUESDAY") diaDaSemana = "Terça";
                if (diaDaSemana == "WEDNESDAY") diaDaSemana = "Quarta";
                if (diaDaSemana == "THURSDAY") diaDaSemana = "Quinta";
                if (diaDaSemana == "FRIDAY") diaDaSemana = "Sexta";
                if (diaDaSemana == "SATURDAY") diaDaSemana = "Sábado";

                eDiaSelecionado = false;
                if (periodicidade.equals("semanal")) {
                    List<String> diasDaSemanaSelecionados = Arrays.asList(diasSelecionados.split(","));
                    for (String dia : diasDaSemanaSelecionados) {
                        if (dia.equals(diaDaSemana)) eDiaSelecionado = true;
                    }
                } else if (periodicidade.equals("mensal")) {
                    List<String> diasDoMesSelecionados = Arrays.asList(diasSelecionados.split(","));
                    for (String dia : diasDoMesSelecionados) {
                        if (Integer.parseInt(dia) == inicio.getDate()) eDiaSelecionado = true;
                    }
                    //System.out.println("TESTE SE O DIA É SELECIONADO: " + eDiaSelecionado + " - DIA DO MES: " + inicio.getDate());
                } else {
                    eDiaSelecionado = true;
                    //System.out.println("TESTANDO IMPRESSÃO EM TODOS OS DIAS");
                }

                if (eDiaSelecionado) {
                    possuiTarifa = false;
                    for (Tarifa tarifaEspecifica : tarifasList) {
                        if (tarifaEspecifica.getId() != null) {
                            possuiTarifa = true;
                            possuiVenda = false;
                            for (ProdutoVendido produtoVendido : tarifaEspecifica.getProdutosVendidos()) {
                                if (produtoVendido.getId() != null) {
                                    possuiVenda = true;
                                }
                            }
                            if (!possuiVenda) {
                                Tarifa atualizarTarifa = obj;
                                DataTarifa dataParaSalvar = dataTarifaService.findByData(inicio);
                                atualizarTarifa.setId(tarifaEspecifica.getId());
                                atualizarTarifa.setData(dataParaSalvar);
                                atualizarTarifa = repo.save(atualizarTarifa);
                                historicoService.insertAny(atualizarTarifa, "Atualizar");
                            } else {
                                Tarifa inativarTarifa = find(tarifaEspecifica.getId());
                                inativarTarifa.setStatus("Inativo");
                                inativarTarifa = repo.save(inativarTarifa);
                                historicoService.insertAny(inativarTarifa, "Atualizar");
                                Tarifa novaTarifa = obj;
                                DataTarifa dataParaSalvar = dataTarifaService.findByData(inicio);
                                novaTarifa.setId(i + 999999999);
                                novaTarifa.setData(dataParaSalvar);
                                novaTarifa = repo.save(novaTarifa);
                                historicoService.insertAny(novaTarifa, "Inserir");
                            }
                        }
                    }
                    if (!possuiTarifa) {
                        Tarifa novaTarifa1 = obj;
                        DataTarifa dataParaSalvar = dataTarifaService.findByData(inicio);
                        novaTarifa1.setId(i + 999999999);
                        novaTarifa1.setData(dataParaSalvar);
                        novaTarifa1 = repo.save(novaTarifa1);
                        historicoService.insertAny(novaTarifa1, "Inserir");
                    } else {
                        System.out.println("TESTE POSSUI TARIFA");
                    }
                } else {
                    for (Tarifa tarifaEspecifica : tarifasList) {
                        Tarifa inativarTarifaSemSelecao = find(tarifaEspecifica.getId());
                        inativarTarifaSemSelecao.setStatus("Inativo");
                        inativarTarifaSemSelecao = repo.save(inativarTarifaSemSelecao);
                        historicoService.insertAny(inativarTarifaSemSelecao, "Atualizar");
                    }
                }

                cal = Calendar.getInstance();
                cal.setTime(inicio);
                cal.add(Calendar.DAY_OF_MONTH, 1);
                inicio = cal.getTime();
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public Tarifa update(Tarifa obj) {
        possuiVenda = false;
        Tarifa newObj = find(obj.getId());
        for (ProdutoVendido produtoVendido : newObj.getProdutosVendidos()) {
            if (produtoVendido.getId() != null) {
                possuiVenda = true;
            }
        }
        if (possuiVenda) {
            newObj.setStatus("Inativo");
            repo.save(newObj);
            obj.setId(null);
            repo.save(obj);
        } else {
            updateData(newObj, obj);
            repo.save(newObj);
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

    public List<Tarifa> findAll() {
        return repo.findAll(Sort.by(Sort.Direction.ASC, "data.data"));
    }

    public Page<Tarifa> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(Tarifa newObj, Tarifa obj) {
        newObj.setNome(obj.getNome());
        newObj.setValor(obj.getValor());
        newObj.setValorCrianca(obj.getValorCrianca());
        newObj.setValorBebe(obj.getValorBebe());
        newObj.setValorIdoso(obj.getValorIdoso());
        newObj.setStatus(obj.getStatus());
        newObj.setCapacidade(obj.getCapacidade());
        newObj.setIdadeLimite(obj.getIdadeLimite());
        newObj.setObs(obj.getObs());

        newObj.setCupom(obj.getCupom());
        newObj.setProduto(obj.getProduto());
        newObj.setData(obj.getData());
    }
}

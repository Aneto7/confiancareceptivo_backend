package com.confianca.services;

import com.confianca.domain.*;
import com.confianca.dto.ProdutoDTO;
import com.confianca.repositories.ProdutoRepository;
import com.confianca.services.exeptions.DataIntegrityException;
import com.confianca.services.exeptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repo;

    @Autowired
    private  HistoricoService historicoService;

    @Autowired
    private UsuarioService usuarioService;

    public Produto find(Integer id) {
        Optional<Produto> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
    }

    public List<Produto> findByFornecedorId(Integer id) {
        List<Produto> obj = repo.findByFornecedorId(id);
        return obj;
    }

    public Produto insert(Produto obj) {
        obj.setId(null);
        PontoDeSaida pontoDeSaida = new PontoDeSaida();
        Cidade cidade = new Cidade();
        Estado estado = new Estado();
        Pais pais = new Pais();

        if(obj.getPontoDeSaida() != null) pontoDeSaida = obj.getPontoDeSaida();
        if(obj.getCidade() != null){
            cidade = obj.getCidade();
            estado = obj.getCidade().getEstado();
            pais = obj.getCidade().getEstado().getPais();
        }
        obj.setTags(Arrays.asList(obj.getHorarioSaida(), pontoDeSaida.getNome(), obj.getPeriodicidade(),
                obj.getStatus(), cidade.getNome(), estado.getNome(),
                pais.getNome(), obj.getNome()));
        obj = repo.save(obj);
        historicoService.insertAny(obj, "Inserir");
        return obj;
    }

    public Produto update(Produto obj) {
        Produto newObj = find(obj.getId());
        String pontoSaida = null;
        if (obj.getPontoDeSaida() != null) pontoSaida = obj.getPontoDeSaida().getNome();
        String cidadeNome = null;
        if (obj.getCidade() != null) cidadeNome = obj.getCidade().getNome();
        String estadoNome = null;
        if (obj.getCidade() != null) estadoNome = obj.getCidade().getEstado().getNome();
        String paisNome = null;
        if (obj.getCidade() != null) paisNome = obj.getCidade().getEstado().getPais().getNome();


        List<String> tags = Arrays.asList(obj.getHorarioSaida(), pontoSaida, obj.getPeriodicidade(), obj.getStatus(), cidadeNome, estadoNome, paisNome);
        newObj.setTags(new ArrayList<>(tags));
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

    public List<Produto> findAll() {
        return repo.findAll();
    }

    public List<ProdutoDTO> findAllDTO() {
        return repo.findAllDTO();
    }

    public List<Produto> findByClientesNome() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario userLogado = usuarioService.findByLogin(authentication.getName());
        List<Produto> prodsEnvio = new ArrayList<>();
        List<Produto> prods = repo.findAll();
        for (Produto prod:prods) {
            if(prod.getClientes().isEmpty()){
                prodsEnvio.add(prod);
            } else {
                for (Cliente cli:prod.getClientes()) {
                    if(cli.getNome().equals(userLogado.getCliente().getNome())){
                        prodsEnvio.add(prod);
                    }
                }
            }

        }
        return prodsEnvio;
    }

    public Page<Produto> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(Produto newObj, Produto obj) {
        newObj.setFornecedor(obj.getFornecedor());
        newObj.setCidade(obj.getCidade());
        newObj.setPontoDeSaida(obj.getPontoDeSaida());
        newObj.setCupom(obj.getCupom());
        newObj.setTipoProduto(obj.getTipoProduto());
        newObj.setNome(obj.getNome());
        newObj.setAnotacao(obj.getAnotacao());
        newObj.setHorarioSaida(obj.getHorarioSaida());
        newObj.setHorarioRetorno(obj.getHorarioRetorno());
        newObj.setLocalSaida(obj.getLocalSaida());
        newObj.setValor(obj.getValor());
        newObj.setCapacidade(obj.getCapacidade());
        newObj.setPeriodicidade(obj.getPeriodicidade());
        newObj.setStatus(obj.getStatus());
        newObj.setObs(obj.getObs());
        newObj.setOpcionais(obj.getOpcionais());
        newObj.setIndividual(obj.getIndividual());
        newObj.setProdutoAlterarValor(obj.getProdutoAlterarValor());
        newObj.setClientes(obj.getClientes());
    }
}

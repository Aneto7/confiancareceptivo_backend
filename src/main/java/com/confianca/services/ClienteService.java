package com.confianca.services;

import com.confianca.domain.Cliente;
import com.confianca.domain.Historico;
import com.confianca.repositories.ClienteRepository;
import com.confianca.services.exeptions.DataIntegrityException;
import com.confianca.services.exeptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    @Autowired
    private HistoricoService historicoService;

    public Cliente find(Integer id) {
        Optional<Cliente> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    public Cliente findByCnpjOuCpf(String cpfOuCnpj) {
        Cliente obj = repo.findByCnpjOuCpf(cpfOuCnpj);
        return obj;
    }

    public Cliente insert(Cliente obj) {
        obj.setId(null);
        obj = repo.save(obj);
        historicoService.insertAny(obj, "Inserir");
        return obj;
    }

    public Cliente update(Cliente obj) {
        Cliente newObj = find(obj.getId());
        updateData(newObj, obj);
        newObj = repo.save(newObj);
        historicoService.insertAny(newObj, "Atualizar");
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

    public List<Cliente> findAll() {
        return repo.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setTipoCliente(obj.getTipoCliente());
        newObj.setCidade(obj.getCidade());
        newObj.setUnidade(obj.getUnidade());
        newObj.setCupom(obj.getCupom());
        newObj.setNome(obj.getNome());
        newObj.setCnpjOuCpf(obj.getCnpjOuCpf());
        newObj.setEmail(obj.getEmail());
        newObj.setNascimento(obj.getNascimento());
        newObj.setTelefone(obj.getTelefone());
        newObj.setCelular(obj.getCelular());
        newObj.setCep(obj.getCep());
        newObj.setBairro(obj.getBairro());
        newObj.setEndereco(obj.getEndereco());
        newObj.setClassificacao(obj.getClassificacao());
        newObj.setStatus(obj.getStatus());
        newObj.setObs(obj.getObs());
    }

    public Cliente consultaCadastroCliente(Cliente obj) {
        //consultando se usuário logado é um cliente cadastrado
        Cliente clienteConsulta = findByCnpjOuCpf(obj.getCnpjOuCpf());

        if (clienteConsulta == null) {
            //ROTINA DE CADASTRO DE CLIENTE COMO USUÁRIO QUANDO ELE NÃO ESTÁ LOGADO
            //clienteService.insert(clienteCadastro);
            //obj.setCliente(clienteCadastro);
            return null;
        } else {
            return obj;
        }
    }

}

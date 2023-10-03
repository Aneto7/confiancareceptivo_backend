package com.confianca.repositories;

import com.confianca.domain.Imagem;
import com.confianca.domain.ProdutoVendido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ImagemRepository extends JpaRepository <Imagem,Integer> {

    @Transactional(readOnly=true)
    public List<Imagem> findByProdutoId(Integer idProduto);

    @Transactional(readOnly=true)
    public List<Imagem> findByPrincipalAndProdutoId(Boolean principal, Integer idProduto);

    @Transactional(readOnly=true)
    public List<Imagem> findByPrincipalAndClienteId(Boolean principal, Integer idCliente);

}

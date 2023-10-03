package com.confianca.repositories;

import com.confianca.domain.CentroDeCusto;
import com.confianca.domain.Produto;
import com.confianca.dto.ProdutoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository <Produto,Integer> {

    @Transactional(readOnly=true)
    public List<Produto> findByFornecedorId(Integer id);

    @Transactional(readOnly=true)
    @Query("SELECT NEW com.confianca.dto.ProdutoDTO " +
            "(obj) FROM Produto obj")
    public List<ProdutoDTO> findAllDTO();

}

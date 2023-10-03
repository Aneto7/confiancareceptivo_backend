package com.confianca.repositories;

import com.confianca.domain.TrechoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TrechoProdutoRepository extends JpaRepository <TrechoProduto,Integer> {

    @Transactional(readOnly=true)
    public List<TrechoProduto> findByProdutoId(Integer id);
}

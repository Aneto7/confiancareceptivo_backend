package com.confianca.repositories;

import com.confianca.domain.TipoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoProdutoRepository extends JpaRepository <TipoProduto,Integer> {

}

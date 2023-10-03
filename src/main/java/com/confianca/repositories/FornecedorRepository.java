package com.confianca.repositories;

import com.confianca.domain.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository extends JpaRepository <Fornecedor,Integer> {

}

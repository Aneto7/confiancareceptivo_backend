package com.confianca.repositories;

import com.confianca.domain.TipoCliente;
import com.confianca.domain.TipoFornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoFornecedorRepository extends JpaRepository <TipoFornecedor,Integer> {

}

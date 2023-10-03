package com.confianca.repositories;

import com.confianca.domain.TipoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoClienteRepository extends JpaRepository <TipoCliente,Integer> {

}

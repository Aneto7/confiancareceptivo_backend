package com.confianca.repositories;

import com.confianca.domain.OpcionalVendido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpcionalVendidoRepository extends JpaRepository <OpcionalVendido,Integer> {

}

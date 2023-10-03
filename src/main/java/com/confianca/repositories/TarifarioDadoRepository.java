package com.confianca.repositories;

import com.confianca.domain.TarifarioDado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarifarioDadoRepository extends JpaRepository<TarifarioDado, Integer> {

}

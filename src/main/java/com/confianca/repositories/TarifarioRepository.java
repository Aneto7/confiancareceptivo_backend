package com.confianca.repositories;

import com.confianca.domain.Tarifario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarifarioRepository extends JpaRepository<Tarifario, Integer> {

}

package com.confianca.repositories;

import com.confianca.domain.PontoDeSaida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PontoDeSaidaRepository extends JpaRepository <PontoDeSaida,Integer> {

}

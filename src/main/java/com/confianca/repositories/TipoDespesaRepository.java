package com.confianca.repositories;

import com.confianca.domain.TipoDespesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDespesaRepository extends JpaRepository <TipoDespesa,Integer> {

}

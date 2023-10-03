package com.confianca.repositories;

import com.confianca.domain.TipoDespesa;
import com.confianca.domain.TipoReceita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoReceitaRepository extends JpaRepository <TipoReceita,Integer> {

}

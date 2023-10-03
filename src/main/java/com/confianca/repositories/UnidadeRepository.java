package com.confianca.repositories;

import com.confianca.domain.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadeRepository extends JpaRepository <Unidade,Integer> {

}

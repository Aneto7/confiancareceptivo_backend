package com.confianca.repositories;

import com.confianca.domain.Guia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuiaRepository extends JpaRepository <Guia,Integer> {

}

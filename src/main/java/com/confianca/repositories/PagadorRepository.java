package com.confianca.repositories;

import com.confianca.domain.Pagador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagadorRepository extends JpaRepository <Pagador,Integer> {

}

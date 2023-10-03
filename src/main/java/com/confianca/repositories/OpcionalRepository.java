package com.confianca.repositories;

import com.confianca.domain.Opcional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpcionalRepository extends JpaRepository <Opcional,Integer> {

}

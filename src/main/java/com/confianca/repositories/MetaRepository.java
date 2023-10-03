package com.confianca.repositories;

import com.confianca.domain.Meta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetaRepository extends JpaRepository <Meta,Integer> {

}

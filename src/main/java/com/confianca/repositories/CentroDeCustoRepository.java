package com.confianca.repositories;

import com.confianca.domain.CentroDeCusto;
import com.confianca.domain.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CentroDeCustoRepository extends JpaRepository<CentroDeCusto,Integer> {

    @Transactional(readOnly=true)
    public Optional<CentroDeCusto> findByNome(String nome);
}

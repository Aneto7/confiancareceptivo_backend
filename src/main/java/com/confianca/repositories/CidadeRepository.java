package com.confianca.repositories;

import com.confianca.domain.Cidade;
import com.confianca.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CidadeRepository extends JpaRepository <Cidade,Integer> {

    @Transactional(readOnly=true)
    public List<Cidade> findByEstadoId(Integer id);

    @Transactional(readOnly=true)
    public List<Cidade> findByEstadoPaisId(Integer id);

    @Transactional(readOnly=true)
    public Optional<Cidade> findByNome(String nome);
}

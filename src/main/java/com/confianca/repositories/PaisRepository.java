package com.confianca.repositories;

import com.confianca.domain.Cidade;
import com.confianca.domain.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaisRepository extends JpaRepository <Pais,Integer> {

    @Transactional(readOnly=true)
    public Optional<Pais> findByNome(String nome);

    @Transactional(readOnly=true)
    @Query("SELECT MAX(obj.id) FROM Pais obj")
    public Optional<Pais> findIdMaximo();

}

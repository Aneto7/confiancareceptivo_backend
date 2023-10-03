package com.confianca.repositories;

import com.confianca.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Integer> {

    @Transactional(readOnly = true)
    public List<Tarifa> findByStatusAndProdutoIdAndDataData(String status, Integer produtoId, Date data);

    @Transactional(readOnly=true)
    public List<Tarifa> findByDataDataBetween(Date dataInicial, Date dataFinal);
}

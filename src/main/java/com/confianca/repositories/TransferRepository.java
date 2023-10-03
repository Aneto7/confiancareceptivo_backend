package com.confianca.repositories;

import com.confianca.domain.Despesa;
import com.confianca.domain.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Integer> {

    @Transactional(readOnly = true)
    public List<Transfer> findByDiahoraBetween(Date dataInicial, Date dataFinal);

    @Transactional(readOnly = true)
    public Optional<Transfer> findByServicoId(Integer idServico);
}

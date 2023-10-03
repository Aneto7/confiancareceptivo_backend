package com.confianca.repositories;

import com.confianca.domain.Cliente;
import com.confianca.domain.TipoPagamentoRecebimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface TipoPagamentoRecebimentoRepository extends JpaRepository <TipoPagamentoRecebimento,Integer> {

    @Transactional(readOnly=true)
    public Optional<TipoPagamentoRecebimento> findByNome(String nome);

}

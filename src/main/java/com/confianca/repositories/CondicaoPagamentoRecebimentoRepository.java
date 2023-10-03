package com.confianca.repositories;

import com.confianca.domain.CondicaoPagamentoRecebimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CondicaoPagamentoRecebimentoRepository extends JpaRepository <CondicaoPagamentoRecebimento,Integer> {

}

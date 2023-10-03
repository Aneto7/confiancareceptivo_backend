package com.confianca.repositories;

import com.confianca.domain.Recebimento;
import com.confianca.domain.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RecebimentoRepository extends JpaRepository <Recebimento,Integer> {

    @Transactional(readOnly=true)
    public List<Recebimento> findByVendaId(Integer idVenda);

}

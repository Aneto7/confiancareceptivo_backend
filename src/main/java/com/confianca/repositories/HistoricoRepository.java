package com.confianca.repositories;

import com.confianca.domain.Historico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface HistoricoRepository extends JpaRepository <Historico,Integer> {

    @Transactional(readOnly=true)
    public List<Historico> findByIdRegistroAndObjeto(Integer id, String obj);

}

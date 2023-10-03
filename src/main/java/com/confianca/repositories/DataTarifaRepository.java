package com.confianca.repositories;

import com.confianca.domain.DataTarifa;
import com.confianca.domain.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DataTarifaRepository extends JpaRepository <DataTarifa,Integer> {

    @Transactional(readOnly = true)
    public Optional<DataTarifa> findByData(Date dataBusca);

}

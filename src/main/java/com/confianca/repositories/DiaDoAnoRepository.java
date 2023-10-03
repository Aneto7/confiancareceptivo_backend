package com.confianca.repositories;

import com.confianca.domain.DiaDoAno;
import com.confianca.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Repository
public interface DiaDoAnoRepository extends JpaRepository <DiaDoAno,Integer> {

    @Transactional(readOnly=true)
//    @Query("SELECT obj FROM DiaDoAno obj WHERE obj.diaDoAno = :diaDoAno")
    public DiaDoAno findByDiaDoAno(Date diaDoAno);
}

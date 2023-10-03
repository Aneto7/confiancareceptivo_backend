package com.confianca.repositories;

import com.confianca.domain.ProdutoVendido;
import com.confianca.domain.Upload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UploadRepository extends JpaRepository <Upload,Integer> {

    @Transactional(readOnly=true)
    public List<Upload> findByIdRegistroAndObjeto(Integer id, String tipo);

}

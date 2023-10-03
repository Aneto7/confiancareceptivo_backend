package com.confianca.repositories;

import com.confianca.domain.Cliente;
import com.confianca.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository <Cliente,Integer> {

    @Transactional(readOnly=true)
    public Cliente findByEmail(String email);

    @Transactional(readOnly=true)
    public Cliente findByCnpjOuCpf(String cnpjOuCpf);

}

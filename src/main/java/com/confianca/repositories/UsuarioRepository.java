package com.confianca.repositories;

import com.confianca.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario,Integer> {

    @Transactional(readOnly=true)
    public Usuario findByLogin(String login);

    @Transactional(readOnly=true)
    public Usuario findByLoginAndStatus(String login, String status);

    @Transactional(readOnly=true)
    public Usuario findByNome(String nome);

}

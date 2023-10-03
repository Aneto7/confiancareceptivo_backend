package com.confianca.services;

import com.confianca.domain.Usuario;
import com.confianca.domain.enums.Perfil;
import com.confianca.repositories.UsuarioRepository;
import com.confianca.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private Collection<? extends GrantedAuthority> authorities;
    private Set<Perfil> perfis;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByLoginAndStatus(email, "Ativo");

        if (usuario == null) {
            throw new UsernameNotFoundException(email);
        }
        perfis = usuario.getPerfis();
        authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList());
        return new UserSS(usuario.getId(), usuario.getLogin(), usuario.getSenha(), authorities);
    }
}

package com.perrosv43.app.servicios;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.perrosv43.app.entidades.Usuario;
import com.perrosv43.app.repositorios.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository userRepository;

    public UsuarioService() {
        super();
    }

    // API

    @Override
    public UserDetails loadUserByUsername(final String username) {
        final Usuario user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), true, true, true, true, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
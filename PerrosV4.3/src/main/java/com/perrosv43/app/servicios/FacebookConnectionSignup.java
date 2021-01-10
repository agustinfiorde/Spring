package com.perrosv43.app.servicios;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;

import com.perrosv43.app.entidades.Usuario;
import com.perrosv43.app.repositorios.UsuarioRepository;

@Service
public class FacebookConnectionSignup implements ConnectionSignUp {

    @Autowired
    private UsuarioRepository userRepository;

    @Override
    public String execute(Connection<?> connection) {
        System.out.println("signup === ");
        final Usuario user = new Usuario();
        user.setUsername(connection.getDisplayName());
        user.setPassword(randomAlphabetic(8));
        userRepository.save(user);
        return user.getUsername();
    }

}

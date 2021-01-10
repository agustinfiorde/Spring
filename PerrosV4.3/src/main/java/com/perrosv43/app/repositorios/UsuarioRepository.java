package com.perrosv43.app.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perrosv43.app.entidades.Usuario;

@Repository("userRepository")
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByUsername(final String username);

}
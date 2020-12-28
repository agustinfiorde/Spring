package com.perrosv4.app.repositorios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.perrosv4.app.entidades.Perro;
import com.perrosv4.app.entidades.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

	@Query("SELECT a from Usuario a WHERE a.nombre = :nombre")
	public Perro findByFileName(@Param("nombre") String nombre);

	@Query("SELECT a from Usuario a ORDER BY a.apellido DESC")
	public Page<Usuario> searchAll(Pageable pageable);

	@Query("SELECT a from Usuario a WHERE a.activo = true ORDER BY a.apellido")
	public List<Usuario> searchAssets();

	@Query("SELECT a from Usuario a WHERE a.activo = true AND a.nombre LIKE :q OR a.apellido LIKE :q OR a.dni LIKE :q OR a.rol LIKE :q ORDER BY a.apellido DESC")
	public Page<Usuario> searchAll(Pageable pageable, @Param("q") String q);

}

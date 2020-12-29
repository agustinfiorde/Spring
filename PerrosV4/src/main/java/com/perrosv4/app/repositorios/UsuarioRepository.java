package com.perrosv4.app.repositorios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.perrosv4.app.entidades.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

	@Query("SELECT a from Usuario a WHERE a.activo = true ORDER BY a.nombre")
	public List<Usuario> searchAssets();

	@Query("SELECT a from Usuario a WHERE a.activo = true ORDER BY a.nombre")
	public Page<Usuario> searchAssets(Pageable pageable);

	@Query("SELECT a from Usuario a WHERE a.activo = true AND a.nombre LIKE :q OR a.apellido LIKE :q OR a.dni LIKE :q OR a.rol LIKE :q ORDER BY a.apellido DESC")
	public Page<Usuario> searchByParam(Pageable pageable, @Param("q") String q);

	@Query("SELECT a from Usuario a WHERE a.activo = true AND a.nombre LIKE :q OR a.apellido LIKE :q OR a.dni LIKE :q OR a.rol LIKE :q ORDER BY a.apellido DESC")
	public List<Usuario> searchByParam(@Param("q") String q);

}

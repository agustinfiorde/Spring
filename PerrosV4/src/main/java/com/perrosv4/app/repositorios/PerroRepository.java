package com.perrosv4.app.repositorios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.perrosv4.app.entidades.Perro;

@Repository
public interface PerroRepository extends JpaRepository<Perro, String> {

	@Query("SELECT a from Perro a WHERE a.nombre = :nombre")
	public Perro findByFileName(@Param("nombre") String nombre);

	@Query("SELECT a from Perro a ORDER BY a.nombre DESC")
	public Page<Perro> searchAll(Pageable pageable);

	@Query("SELECT a from Perro a WHERE a.activo = true ORDER BY a.nombre")
	public List<Perro> searchAssets();

	@Query("SELECT a from Perro a WHERE a.activo = true AND a.nombre LIKE :q OR a.apodo LIKE :q OR a.raza LIKE :q ORDER BY a.nombre DESC")
	public Page<Perro> searchAll(Pageable pageable, @Param("q") String q);

}

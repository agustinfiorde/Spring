package com.perrosv1.app.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.perrosv1.app.entidades.Perro;

@Repository
public interface PerroRepository extends JpaRepository<Perro, String>{

	@Query("SELECT a from Perro a WHERE a.activo = true ")
	public List<Perro> buscarActivos();
	
}
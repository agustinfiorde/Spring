package com.perrosv31.app.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.perrosv31.app.entidades.Foto;

@Repository
public interface FotoRepository extends JpaRepository<Foto, String>{

	@Query("SELECT a from Foto a WHERE a.activo = true ")
	public List<Foto> buscarActivos();
	
}
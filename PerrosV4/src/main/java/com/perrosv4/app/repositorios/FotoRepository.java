package com.perrosv4.app.repositorios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.perrosv4.app.entidades.Foto;

@Repository
public interface FotoRepository extends JpaRepository<Foto, String> {

	@Query("SELECT a from Foto a WHERE a.fileName = :fileName")
	public Foto findByFileName(@Param("fileName") String fileName);

	@Query("SELECT a from Foto a WHERE a.activo = true ORDER BY a.fileName")
	public List<Foto> searchAssets();

	@Query("SELECT a from Foto a WHERE a.activo = true ORDER BY a.fileName")
	public Page<Foto> searchAssets(Pageable pageable);

	@Query("SELECT a from Foto a WHERE a.activo = true AND a.fileName LIKE :q OR a.uri LIKE :q ORDER BY a.fileName DESC")
	public Page<Foto> searchByParam(Pageable pageable, @Param("q") String q);

	@Query("SELECT a from Foto a WHERE a.activo = true AND a.fileName LIKE :q OR a.uri LIKE :q ORDER BY a.fileName DESC")
	public List<Foto> searchByParam(@Param("q") String q);

}

package com.perrosv11.app.repositorios;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.perrosv11.app.entidades.Perro;

@Repository
public interface PerroRepository extends MongoRepository<Perro, String>{

	@Query("{ 'activo' : true}")
	public List<Perro> buscarActivos();
	
}
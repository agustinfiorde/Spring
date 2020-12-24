package com.perrosv13.app.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perrosv13.app.entidades.Dato;

@Repository
public interface DatoRepository extends JpaRepository<Dato, String>{
	
}
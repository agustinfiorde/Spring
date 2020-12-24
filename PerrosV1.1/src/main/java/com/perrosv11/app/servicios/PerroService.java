package com.perrosv11.app.servicios;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.perrosv11.app.entidades.Perro;
import com.perrosv11.app.repositorios.PerroRepository;

@Service
public class PerroService {

	@Autowired
	private PerroRepository perroRepository;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Perro guardar(String nombre, String apodo, String foto, String raza) throws Exception {

		validar(nombre, apodo, foto, raza);

		Perro entidad = new Perro();

		entidad.setNombre(nombre);
		entidad.setApodo(apodo);
		entidad.setFoto(foto);
		entidad.setRaza(raza);
		entidad.setActivo(true);
		entidad.setCreado(new Date());

		return perroRepository.insert(entidad);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Perro alta(String id) {

		Perro entidad = perroRepository.findById(id).get();

		entidad.setActivo(true);
		return perroRepository.save(entidad);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Perro baja(String id) {

		Perro entidad = perroRepository.findById(id).get();

		entidad.setActivo(false);
		return perroRepository.save(entidad);
	}

	@Transactional(readOnly = true)
	public List<Perro> listarActivos() {
		return perroRepository.buscarActivos();
	}

	@Transactional(readOnly = true)
	public List<Perro> listarTodos() {
		return perroRepository.findAll();
	}

	public void validar(String nombre, String apodo, String foto, String raza) throws Exception {

		if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
			throw new Exception();
		}
		
		if (apodo == null || apodo.isEmpty() || apodo.contains("  ")) {
			throw new Exception();
		}
		
		if (foto == null || foto.isEmpty() || foto.contains("  ")) {
			throw new Exception();
		}
		
		if (raza == null || raza.isEmpty() || raza.contains("  ")) {
			throw new Exception();
		}
	}
}

package com.perrosv2.app.servicios;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.perrosv2.app.entidades.Perro;
import com.perrosv2.app.errores.WebException;
import com.perrosv2.app.repositorios.PerroRepository;

@Service
public class PerroService {

	@Autowired
	private PerroRepository perroRepository;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Perro guardar(String nombre, String apodo, String foto, String raza) throws WebException {

		validar(nombre, apodo, foto, raza);

		Perro entidad = new Perro();

		entidad.setNombre(nombre);
		entidad.setApodo(apodo);
		entidad.setFoto(foto);
		entidad.setRaza(raza);
		entidad.setActivo(true);
		entidad.setCreado(new Date());

		return perroRepository.save(entidad);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Perro alta(String id) {

		Perro entidad = perroRepository.getOne(id);

		entidad.setActivo(true);
		return perroRepository.save(entidad);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Perro baja(String id) {

		Perro entidad = perroRepository.getOne(id);

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

	public void validar(String nombre, String apodo, String foto, String raza) throws WebException {

		if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
			throw new WebException("Nombre invalido");
		}
		
		if (apodo == null || apodo.isEmpty() || apodo.contains("  ")) {
			throw new WebException("Apodo invalido");
		}
		
		if (foto == null || foto.isEmpty() || foto.contains("  ")) {
			throw new WebException("Foto invalida");
		}
		
		if (raza == null || raza.isEmpty() || raza.contains("  ")) {
			throw new WebException("Raza invalida");
		}
	}
}

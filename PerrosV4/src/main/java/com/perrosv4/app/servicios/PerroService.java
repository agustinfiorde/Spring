package com.perrosv4.app.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.perrosv4.app.convertidores.PerroConverter;
import com.perrosv4.app.entidades.Perro;
import com.perrosv4.app.errores.WebException;
import com.perrosv4.app.modelos.PerroModel;
import com.perrosv4.app.repositorios.PerroRepository;

public class PerroService implements ServiceInterface<PerroModel, Perro> {

	private PerroRepository perroRepository;
	private PerroConverter perroConverter;

	@Autowired
	public PerroService(PerroRepository perroRepository, PerroConverter perroConverter) {
		this.perroRepository = perroRepository;
		this.perroConverter = perroConverter;
	}

	@Override
	public Perro guardar(PerroModel m) throws WebException {
		return null;
	}

	@Override
	public Perro eliminar(String id) throws WebException {
		return null;
	}

	@Override
	public Perro alta(String id) throws WebException {
		return null;
	}

	@Override
	public Perro baja(String id) throws WebException {
		return null;
	}

	@Override
	public List<Perro> listarActivos() {
		return null;
	}

	@Override
	public Page<Perro> listarTodos(Pageable paginable, String q) {
		return null;
	}

	@Override
	public Page<Perro> lsitarTodos(Pageable paginable) {
		return null;
	}

	@Override
	public Optional<Perro> buscarPorId(String id) {
		return null;
	}

	@Override
	public Perro getOne(String id) {
		return null;
	}

	@Override
	public void validar(PerroModel m) throws WebException {
//		if (m.getFileName() == null || m.getFileName().isEmpty() || m.getFileName().equals("")) {
//			throw new WebException("La actividad tiene que tener un director o responsable");
//		}
	}

}

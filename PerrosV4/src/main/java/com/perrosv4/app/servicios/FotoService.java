package com.perrosv4.app.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.perrosv4.app.convertidores.FotoConverter;
import com.perrosv4.app.entidades.Foto;
import com.perrosv4.app.errores.WebException;
import com.perrosv4.app.modelos.FotoModel;
import com.perrosv4.app.repositorios.FotoRepository;

@Service
public class FotoService implements ServiceInterface<FotoModel, Foto> {

	private FotoRepository fotoRepository;
	private FotoConverter fotoConverter;

	@Autowired
	public FotoService(FotoRepository fotoRepository, FotoConverter fotoConverter) {
		this.fotoRepository = fotoRepository;
		this.fotoConverter = fotoConverter;
	}

	@Override
	public Foto guardar(FotoModel m) throws WebException {
		return null;
	}

	@Override
	public Foto eliminar(String id) throws WebException {
		return null;
	}

	@Override
	public Foto alta(String id) throws WebException {
		return null;
	}

	@Override
	public Foto baja(String id) throws WebException {
		return null;
	}

	@Override
	public List<Foto> listarActivos() {
		return null;
	}

	@Override
	public Page<Foto> listarTodos(Pageable paginable, String q) {
		return null;
	}

	@Override
	public Page<Foto> lsitarTodos(Pageable paginable) {
		return null;
	}

	@Override
	public Optional<Foto> buscarPorId(String id) {
		return null;
	}

	@Override
	public Foto getOne(String id) {
		return null;
	}

	@Override
	public void validar(FotoModel m) throws WebException {
		if (m.getFileName() == null || m.getFileName().isEmpty() || m.getFileName().equals("")) {
			throw new WebException("La actividad tiene que tener un director o responsable");
		}

	}

}

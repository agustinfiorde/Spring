package com.perrosv22.app.servicios;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.perrosv22.app.convertidores.PerroConverter;
import com.perrosv22.app.entidades.Perro;
import com.perrosv22.app.errores.WebException;
import com.perrosv22.app.modelos.PerroModel;
import com.perrosv22.app.repositorios.PerroRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PerroService {

	private final PerroRepository perroRepository;
	private final PerroConverter perroConverter;

	public Perro guardar(PerroModel m) throws Exception {

		validar(m);

		Perro entidad = perroConverter.modelToEntity(m);

		if (entidad.getCreado() != null) {
			entidad.setEditado(new Date());
		} else {
			entidad.setActivo(true);
			entidad.setCreado(new Date());
		}

		return perroRepository.save(entidad);
	}

	public void eliminar(String id) throws Exception {
		Perro entidad = perroRepository.getOne(id);
		perroRepository.delete(entidad);
	}

	public Perro alta(String id) throws Exception {
		Perro entidad = perroRepository.getOne(id);
		entidad.setActivo(true);
		return perroRepository.save(entidad);
	}

	public Perro baja(String id) throws Exception {
		Perro entidad = perroRepository.getOne(id);
		entidad.setActivo(false);
		return perroRepository.save(entidad);
	}

	public List<Perro> listarTodos() {
		return perroRepository.findAll();
	}

	public List<Perro> listarActivos() {
		return perroRepository.searchAssets();
	}

	public Page<Perro> listarActivos(Pageable paginable) {
		return perroRepository.searchAssets(paginable);
	}

	public Page<Perro> buscarPorParametro(Pageable paginable, String q) {
		return perroRepository.searchByParam(paginable, q);
	}

	public List<Perro> buscarPorParametro(String q) {
		return perroRepository.searchByParam(q);
	}

	public Optional<Perro> buscarPorId(String id) {
		return perroRepository.findById(id);
	}

	public Perro getOne(String id) {
		return perroRepository.getOne(id);
	}

	public void validar(PerroModel m) throws WebException {

		if (m.getNombre() == null || m.getNombre().isEmpty() || m.getNombre().equals("")) {
			throw new WebException("El Perro tiene que tener un Nombre");
		}

		if (m.getApodo() == null || m.getApodo().isEmpty() || m.getApodo().equals("")) {
			throw new WebException("El Perro tiene que tener un Apodo");
		}

		if (m.getRaza() == null || m.getRaza().isEmpty() || m.getRaza().equals("")) {
			throw new WebException("El Perro tiene que tener una Raza");
		}

		if (m.getFoto() == null) {
			throw new WebException("El Perro tiene que tener una Foto");
		}

	}
	
	public PerroModel pasarAtributos(PerroModel source, PerroModel target) {
		
		BeanUtils.copyProperties(source, target, "id", "creado", "editado", "activo");
		
		return target;
	}

}

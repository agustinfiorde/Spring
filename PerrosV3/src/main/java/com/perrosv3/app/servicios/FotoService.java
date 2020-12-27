package com.perrosv3.app.servicios;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.perrosv3.app.entidades.Foto;
import com.perrosv3.app.repositorios.FotoRepository;

@Service
public class FotoService {

	@Autowired
	private FotoRepository fotoRepository;

	public Foto multiPartToEntity(MultipartFile file) throws Exception {
		if (file.getSize() == 0) {
			throw new Exception("Debe tener al menos un logo generico");
		}

		try {
			Foto photo = new Foto();
			photo.setMime(file.getContentType());
			photo.setName(file.getName());
			photo.setContent(file.getBytes());
			return photo;
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Foto guardar(Foto entidad) throws Exception {
		
		if (entidad.getCreado() != null) {
			entidad.setEditado(new Date());
		} else {
			entidad.setActivo(true);
			entidad.setCreado(new Date());
		}

		return fotoRepository.save(entidad);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Foto alta(String id) {

		Foto entidad = fotoRepository.getOne(id);
		entidad.setActivo(true);
		return fotoRepository.save(entidad);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Foto baja(String id) {

		Foto entidad = fotoRepository.getOne(id);
		entidad.setActivo(false);
		return fotoRepository.save(entidad);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminar(String id) {

		Foto entidad = fotoRepository.getOne(id);
		fotoRepository.delete(entidad);
	}

	@Transactional(readOnly = true)
	public Foto getOne(String id) {
		return fotoRepository.getOne(id);
	}

	@Transactional(readOnly = true)
	public List<Foto> listarActivos() {
		return fotoRepository.buscarActivos();
	}

	@Transactional(readOnly = true)
	public List<Foto> listarTodos() {
		return fotoRepository.findAll();
	}

}

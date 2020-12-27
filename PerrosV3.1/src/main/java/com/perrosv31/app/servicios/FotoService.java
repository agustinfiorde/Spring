package com.perrosv31.app.servicios;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.perrosv31.app.entidades.Foto;
import com.perrosv31.app.repositorios.FotoRepository;

@Service
public class FotoService {

	@Autowired
	private FotoRepository fotoRepository;

	@Value("${fiordex.carpeta.fotos}")
	private String carpetaFotos;

	public String cargarFoto(MultipartFile file) throws Exception {
		try {
			String uuidName = UUID.randomUUID().toString() + "."
					+ FilenameUtils.getExtension(file.getOriginalFilename());

			Path rootPath = Paths.get(carpetaFotos).resolve(uuidName);
			Path rootAbsolutePath = rootPath.toAbsolutePath();

			Files.copy(file.getInputStream(), rootAbsolutePath);

			return uuidName;
		} catch (Exception e) {
			System.out.println("Error al cargar la foto");
			throw new Exception();
		}
	}

	public Foto actualizarFoto(MultipartFile multipartFile, String id) throws Exception {
		try {

			Foto foto = fotoRepository.getOne(id);
			
			Path rootPath = Paths.get(carpetaFotos).resolve(foto.getFileName()).toAbsolutePath();
			File file = rootPath.toFile();

			if (file.exists() && file.canRead()) {
				
				if (file.delete()) {
					foto.setFileName(cargarFoto(multipartFile));
					return foto;
				}
			}
			return null;
		} catch (Exception e) {
			System.out.println("Error al actualizar la foto");
			throw new Exception("Error al actualizar la foto");
		} 
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminar(String id) throws Exception {

		Foto foto = fotoRepository.getOne(id);

		Path rootPath = Paths.get(carpetaFotos).resolve(foto.getFileName()).toAbsolutePath();

		File file = rootPath.toFile();

		if (file.exists() && file.canRead()) {
			if (file.delete()) {
				fotoRepository.delete(foto);
			}
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

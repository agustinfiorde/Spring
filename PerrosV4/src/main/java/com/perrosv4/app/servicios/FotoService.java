package com.perrosv4.app.servicios;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.perrosv4.app.convertidores.FotoConverter;
import com.perrosv4.app.entidades.Foto;
import com.perrosv4.app.excepciones.ValidationError;
import com.perrosv4.app.modelos.FotoModel;
import com.perrosv4.app.repositorios.FotoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FotoService implements ServiceInterface<FotoModel, Foto> {

	@Value("${fiordex.carpeta.fotos}")
	private String carpetaFotos;

	private final FotoRepository fotoRepository;
	private final FotoConverter fotoConverter;

	public String cargarFoto(MultipartFile file) throws Exception {

		try {
			String uuidName = UUID.randomUUID().toString() + "."
					+ FilenameUtils.getExtension(file.getOriginalFilename());

			Path rootPath = Paths.get(carpetaFotos).resolve(uuidName);
			Path rootAbsolutePath = rootPath.toAbsolutePath();

			Files.copy(file.getInputStream(), rootAbsolutePath);

			return uuidName;
		} catch (Exception e) {
			throw new Exception("Error al cargar la foto");
		}

	}

	public Foto actualizarFoto(MultipartFile multipartFile, String id) throws Exception {

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

	}

	@Override
	public Foto guardar(FotoModel m) throws ValidationError {

		validar(m);

		Foto e = fotoConverter.modelToEntity(m);

		if (e.getCreado() != null) {
			e.setEditado(new Date());
		} else {
			e.setActivo(true);
			e.setCreado(new Date());
		}

		return fotoRepository.save(e);
	}

	@Override
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

	@Override
	public Foto alta(String id) {

		Foto entidad = fotoRepository.getOne(id);
		entidad.setActivo(true);
		return fotoRepository.save(entidad);

	}

	@Override
	public Foto baja(String id) {

		Foto entidad = fotoRepository.getOne(id);
		entidad.setActivo(false);
		return fotoRepository.save(entidad);

	}

	@Override
	public List<Foto> listarTodos() {
		return fotoRepository.findAll();
	}

	@Override
	public List<Foto> listarActivos() {
		return fotoRepository.searchAssets();
	}

	@Override
	public Page<Foto> listarActivos(Pageable paginable) {
		return fotoRepository.searchAssets(paginable);
	}

	@Override
	public Page<Foto> buscarPorParametro(Pageable paginable, String q) {
		return fotoRepository.searchByParam(paginable, q);
	}

	@Override
	public List<Foto> buscarPorParametro(String q) {
		return fotoRepository.searchByParam(q);
	}

	@Override
	public Optional<Foto> buscarPorId(String id) {
		return fotoRepository.findById(id);
	}

	@Override
	public Foto getOne(String id) {
		return fotoRepository.getOne(id);
	}

	@Override
	public void validar(FotoModel m) throws ValidationError {
		if (m.getFileName() == null || m.getFileName().isEmpty() || m.getFileName().equals("")) {
			throw new ValidationError("La Foto tiene que estar presente");
		}

	}

	@Override
	public FotoModel pasarAtributos(FotoModel source, FotoModel target) {
		// TODO Auto-generated method stub
		return null;
	}

}

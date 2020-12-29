package com.perrosv4.app.servicios;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.perrosv4.app.convertidores.UsuarioConverter;
import com.perrosv4.app.entidades.Usuario;
import com.perrosv4.app.enums.Rol;
import com.perrosv4.app.excepciones.ValidationError;
import com.perrosv4.app.modelos.UsuarioModel;
import com.perrosv4.app.repositorios.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UsuarioService implements ServiceInterface<UsuarioModel, Usuario> {

	private final UsuarioRepository usuarioRepository;
	private final UsuarioConverter usuarioConverter;

	@Override
	public Usuario guardar(UsuarioModel m) throws Exception {

		validar(m);

		Usuario entidad = usuarioConverter.modelToEntity(m);

		if (entidad.getCreado() != null) {
			entidad.setEditado(new Date());
		} else {
			entidad.setActivo(true);
			entidad.setCreado(new Date());
		}

		return usuarioRepository.save(entidad);
	}

	@Override
	public void eliminar(String id) throws Exception {
		Usuario entidad = usuarioRepository.getOne(id);
		usuarioRepository.delete(entidad);
	}

	@Override
	public Usuario alta(String id) throws Exception {
		Usuario entidad = usuarioRepository.getOne(id);
		entidad.setActivo(true);
		return usuarioRepository.save(entidad);
	}

	@Override
	public Usuario baja(String id) throws Exception {
		Usuario entidad = usuarioRepository.getOne(id);
		entidad.setActivo(false);
		return usuarioRepository.save(entidad);
	}

	@Override
	public List<Usuario> listarTodos() {
		return usuarioRepository.findAll();
	}

	@Override
	public List<Usuario> listarActivos() {
		return usuarioRepository.searchAssets();
	}

	@Override
	public Page<Usuario> listarActivos(Pageable paginable) {
		return usuarioRepository.searchAssets(paginable);
	}

	@Override
	public Page<Usuario> buscarPorParametro(Pageable paginable, String q) {
		return usuarioRepository.searchByParam(paginable, q);
	}

	@Override
	public List<Usuario> buscarPorParametro(String q) {
		return usuarioRepository.searchByParam(q);
	}

	@Override
	public Optional<Usuario> buscarPorId(String id) {
		return usuarioRepository.findById(id);
	}

	@Override
	public Usuario getOne(String id) {
		return usuarioRepository.getOne(id);
	}

	@Override
	public void validar(UsuarioModel m) throws ValidationError {

		if (m.getNombre() == null || m.getNombre().isEmpty() || m.getNombre().equals("")) {
			throw new ValidationError("El Usuario tiene que tener un nombre");
		}

		if (m.getNombre() == null || m.getNombre().isEmpty() || m.getNombre().equals("")) {
			throw new ValidationError("El Usuario tiene que tener un apellido");
		}

		if (m.getDni() == null || m.getDni().isEmpty() || m.getDni().equals("")) {
			throw new ValidationError("El Usuario tiene que tener un DNI");
		}

		if (m.getRol() == null || m.getRol() != Rol.ADMIN || m.getRol() != Rol.USUARIO) {
			throw new ValidationError("El Usuario tiene que tener un DNI");
		}

		if (m.getFotoPerfil() == null) {
			throw new ValidationError("El Usuario tiene que tener una Foto de perfil");
		}

		if (m.getNacimiento() == null || m.getNacimiento().equals("")) {
			throw new ValidationError("El Usuario tiene que tener una fecha de nacimiento");
		}

	}

}

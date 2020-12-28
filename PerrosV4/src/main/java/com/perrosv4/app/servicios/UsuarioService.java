package com.perrosv4.app.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.perrosv4.app.convertidores.UsuarioConverter;
import com.perrosv4.app.entidades.Usuario;
import com.perrosv4.app.errores.WebException;
import com.perrosv4.app.modelos.UsuarioModel;
import com.perrosv4.app.repositorios.UsuarioRepository;

public class UsuarioService implements ServiceInterface<UsuarioModel, Usuario> {

	private UsuarioRepository usuarioRepository;
	private UsuarioConverter usuarioConverter;

	@Autowired
	public UsuarioService(UsuarioRepository usuarioRepository, UsuarioConverter usuarioConverter) {
		this.usuarioRepository = usuarioRepository;
		this.usuarioConverter = usuarioConverter;
	}

	@Override
	public Usuario guardar(UsuarioModel m) throws WebException {
		return null;
	}

	@Override
	public Usuario eliminar(String id) throws WebException {
		return null;
	}

	@Override
	public Usuario alta(String id) throws WebException {
		return null;
	}

	@Override
	public Usuario baja(String id) throws WebException {
		return null;
	}

	@Override
	public List<Usuario> listarActivos() {
		return null;
	}

	@Override
	public Page<Usuario> listarTodos(Pageable paginable, String q) {
		return null;
	}

	@Override
	public Page<Usuario> lsitarTodos(Pageable paginable) {
		return null;
	}

	@Override
	public Optional<Usuario> buscarPorId(String id) {
		return null;
	}

	@Override
	public Usuario getOne(String id) {
		return null;
	}

	@Override
	public void validar(UsuarioModel m) throws WebException {
//		if (m.getFileName() == null || m.getFileName().isEmpty() || m.getFileName().equals("")) {
//			throw new WebException("La actividad tiene que tener un director o responsable");
//		}
	}

}

package com.perrosv22.app.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.perrosv22.app.convertidores.UsuarioConverter;
import com.perrosv22.app.entidades.Usuario;
import com.perrosv22.app.enums.Rol;
import com.perrosv22.app.errores.WebException;
import com.perrosv22.app.modelos.UsuarioModel;
import com.perrosv22.app.repositorios.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UsuarioService implements UserDetailsService {

	private final UsuarioRepository usuarioRepository;
	private final UsuarioConverter usuarioConverter;

	public Usuario guardar(UsuarioModel m) throws WebException {

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

	public void eliminar(String id) throws Exception {
		Usuario entidad = usuarioRepository.getOne(id);
		usuarioRepository.delete(entidad);
	}

	public Usuario alta(String id) throws Exception {
		Usuario entidad = usuarioRepository.getOne(id);
		entidad.setActivo(true);
		return usuarioRepository.save(entidad);
	}

	public Usuario baja(String id) throws Exception {
		Usuario entidad = usuarioRepository.getOne(id);
		entidad.setActivo(false);
		return usuarioRepository.save(entidad);
	}

	public List<Usuario> listarTodos() {
		return usuarioRepository.findAll();
	}

	public List<Usuario> listarActivos() {
		return usuarioRepository.searchAssets();
	}

	public Page<Usuario> listarActivos(Pageable paginable) {
		return usuarioRepository.searchAssets(paginable);
	}

	public Page<Usuario> buscarPorParametro(Pageable paginable, String q) {
		return usuarioRepository.searchByParam(paginable, q);
	}

	public List<Usuario> buscarPorParametro(String q) {
		return usuarioRepository.searchByParam(q);
	}

	public Optional<Usuario> buscarPorId(String id) {
		return usuarioRepository.findById(id);
	}

	public Usuario getOne(String id) {
		return usuarioRepository.getOne(id);
	}

	public void validar(UsuarioModel m) throws WebException {

		if (m.getNombre() == null || m.getNombre().isEmpty() || m.getNombre().equals("")) {
			throw new WebException("El Usuario tiene que tener un nombre");
		}

		if (m.getNombre() == null || m.getNombre().isEmpty() || m.getNombre().equals("")) {
			throw new WebException("El Usuario tiene que tener un apellido");
		}

		if (m.getDni() == null || m.getDni().isEmpty()) {
			throw new WebException("El Usuario tiene que tener un DNI");
		}

		if (m.getRol() == null || !validarRol(m.getRol())) {
			throw new WebException("El Usuario tiene que tener un rol valido");
		}

		if (m.getNacimientoString() == null || m.getNacimientoString().equals("")) {
			throw new WebException("El Usuario tiene que tener una fecha de nacimiento");
		}

		if (m.getEmail() == null || m.getEmail().isEmpty()) {
			throw new WebException("El Usuario tiene que tener un email");
		}

		if (usuarioRepository.buscarPorEmail(m.getEmail()) != null) {
			throw new WebException("El email ya esta en uso");
		}

		if (m.getClave() == null || m.getClave().isEmpty() || m.getClave().length() < 6 || m.getClave().length() > 12) {
			throw new WebException("La clave tiene que ser distinta a nada y tiene que tener entre 6 y 12 caracteres");
		}
	}

	public boolean validarRol(Rol rol) {

		boolean var = false;
		for (Rol r : Rol.values()) {
			var = var || r.equals(rol) ? true : false;
		}
		return var;
	}

	public void lilith() {

		if (usuarioRepository.buscarPorEmail("asd@asd.com") == null) {
			Usuario user = new Usuario();
			user.setDni("35555555");
			user.setEmail("asd@asd.com");
			user.setNombre("Pepe");
			user.setApellido("Honguito");
			user.setRol(Rol.ADMIN);
			user.setActivo(true);
			user.setClave(new BCryptPasswordEncoder().encode("asdasdasd"));
			usuarioRepository.save(user);
		}
	}

	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Usuario user = usuarioRepository.buscarPorEmail(email);

		if (user != null) {
			List<GrantedAuthority> permissions = new ArrayList<>();
			GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + user.getRol().toString());
			permissions.add(p);
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session = attr.getRequest().getSession(true);
			session.setAttribute("usuario", user);
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getClave(),
					permissions);
		}
		return null;
	}

}

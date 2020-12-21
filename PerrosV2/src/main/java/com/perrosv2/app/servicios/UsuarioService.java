package com.perrosv2.app.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.perrosv2.app.entidades.Usuario;
import com.perrosv2.app.enums.Rol;
import com.perrosv2.app.errores.WebException;
import com.perrosv2.app.repositorios.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Usuario guardar(String nombre, String apellido, String email, String clave, String rol) throws WebException {

		validar(nombre, apellido, email, clave, rol);

		Usuario entidad = new Usuario();

		entidad.setNombre(nombre);
		entidad.setApellido(apellido);
		entidad.setEmail(email);
		entidad.setClave(new BCryptPasswordEncoder().encode(clave));
		entidad.setRol(Rol.valueOf(rol));
		entidad.setActivo(true);
		entidad.setCreado(new Date());

		return usuarioRepository.save(entidad);
	}

	public void validar(String nombre, String apellido, String email, String clave, String rol) throws WebException {

		if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
			throw new WebException("Debe tener un nombre valido");
		}

		if (apellido == null || apellido.isEmpty() || apellido.contains("  ")) {
			throw new WebException("Debe tener un apellido valido");
		}

		if (email == null || email.isEmpty() || email.contains("  ")) {
			throw new WebException("Debe tener un email valido");
		}

		if (usuarioRepository.buscarPorEmail(email) != null) {
			throw new WebException("El Email ya esta en uso");
		}

		if (clave == null || clave.isEmpty() || clave.contains("  ") || clave.length() < 8 || clave.length() > 12) {
			throw new WebException("Debe tener una clave valida");
		}

		if (!Rol.ADMIN.toString().equals(rol) && !Rol.USUARIO.toString().equals(rol)) {
			throw new WebException("Debe tener rol valido");
		}

	}

	@Override
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

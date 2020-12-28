package com.perrosv4.app.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perrosv4.app.convertidores.UsuarioConverter;
import com.perrosv4.app.modelos.UsuarioModel;
import com.perrosv4.app.servicios.UsuarioService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/usuario")
public class UsuarioController implements ControllerInterface<UsuarioModel> {

	private UsuarioService usuarioService;
	private UsuarioConverter usuarioConverter;

	@Autowired
	public UsuarioController(UsuarioService usuarioService, UsuarioConverter usuarioConverter) {
		this.usuarioService = usuarioService;
		this.usuarioConverter = usuarioConverter;
	}

	@Override
	public ResponseEntity<?> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<UsuarioModel> getOne(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<UsuarioModel> create(UsuarioModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<UsuarioModel> edit(UsuarioModel model, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<UsuarioModel> delete(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}

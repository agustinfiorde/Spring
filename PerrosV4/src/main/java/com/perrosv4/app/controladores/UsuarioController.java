package com.perrosv4.app.controladores;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.perrosv4.app.convertidores.FotoConverter;
import com.perrosv4.app.convertidores.UsuarioConverter;
import com.perrosv4.app.entidades.Foto;
import com.perrosv4.app.entidades.Usuario;
import com.perrosv4.app.modelos.FotoModel;
import com.perrosv4.app.modelos.UsuarioModel;
import com.perrosv4.app.servicios.FotoService;
import com.perrosv4.app.servicios.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/api/usuario")
public class UsuarioController implements ControllerInterface<UsuarioModel> {

	private Map<String, Object> response;
	private final UsuarioService usuarioService;
	private final UsuarioConverter usuarioConverter;
	private final FotoService fotoService;
	private final FotoConverter fotoConverter;

	@Override
	public ResponseEntity<?> list() throws Exception {
		response = new HashMap<>();
		response.put("usuarios", usuarioService.listarTodos());
		return ResponseEntity.status(200).body(response);
	}

	@Override
	public ResponseEntity<?> getOne(String id) throws Exception {
		response = new HashMap<>();
		response.put("usuario", usuarioService.getOne(id));
		return ResponseEntity.status(200).body(response);
	}

	@Override
	public ResponseEntity<?> create(UsuarioModel model, MultipartFile file) throws Exception {

		response = new HashMap<>();
		
		Foto foto = fotoService.getOne(model.getIdFotoPerfil());

		FotoModel fotoModel = fotoConverter.entityToModel(foto);

		model.setFotoPerfil(fotoModel);

		Usuario usuario = usuarioService.guardar(model);

		response.put("usuario", usuario);
		response.put("message", "Exito al cargar el usuario");
		return ResponseEntity.status(200).body(response);

	}

	@Override
	public ResponseEntity<?> edit(UsuarioModel model, MultipartFile file) throws Exception {
		response = new HashMap<>();
		Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(model.getId());

		if (usuarioOpt.isPresent() && usuarioOpt.get().isActivo()) {

			Foto foto = fotoService.getOne(model.getIdFotoPerfil());

			FotoModel fotoModel = fotoConverter.entityToModel(foto);

			model.setFotoPerfil(fotoModel);

			UsuarioModel target= usuarioConverter.entityToModel(usuarioOpt.get());
			
			Usuario usuario = usuarioService.guardar(usuarioService.pasarAtributos(model, target));


			response.put("message", "Exito al actualizar el usuario");
			response.put("usuario", usuario);
			return ResponseEntity.status(200).body(response);

		} else {
			response.put("message", "No existe ese usuario en los registros");
			return ResponseEntity.status(200).body(response);
		}
	}

	@Override
	public ResponseEntity<?> delete(String id) throws Exception {
		response = new HashMap<>();
		usuarioService.eliminar(id);
		response.put("message", "Eliminacion exitosa del usuario con el id " + id);
		return ResponseEntity.status(200).body(response);
	}

}

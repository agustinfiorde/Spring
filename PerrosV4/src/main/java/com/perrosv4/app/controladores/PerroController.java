package com.perrosv4.app.controladores;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.perrosv4.app.convertidores.FotoConverter;
import com.perrosv4.app.convertidores.PerroConverter;
import com.perrosv4.app.entidades.Foto;
import com.perrosv4.app.entidades.Perro;
import com.perrosv4.app.modelos.FotoModel;
import com.perrosv4.app.modelos.PerroModel;
import com.perrosv4.app.servicios.FotoService;
import com.perrosv4.app.servicios.PerroService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/api/perro")
public class PerroController implements ControllerInterface<PerroModel> {
	
	private HashMap<String, Object> response;
	private final PerroService perroService;
	private final PerroConverter perroConverter;
	private final FotoService fotoService;
	private final FotoConverter fotoConverter;

	@Override
	public ResponseEntity<?> list() throws Exception {
		response = new HashMap<>();
		response.put("perros", perroService.listarTodos());
		return ResponseEntity.status(200).body(response);
	}

	@Override
	public ResponseEntity<?> getOne(String id) throws Exception {
		response = new HashMap<>();
		response.put("perro", perroService.getOne(id));
		return ResponseEntity.status(200).body(response);
	}

	@PostMapping("/test")
	public ResponseEntity<?> test (@RequestBody Object o) {
		return ResponseEntity.status(200).body(o);
	}
	
	@Override
	public ResponseEntity<?> create(PerroModel model, MultipartFile file) throws Exception {

		response = new HashMap<>();
		Foto foto = fotoService.getOne(model.getIdFotoPerfil());

		FotoModel fotoModel = fotoConverter.entityToModel(foto);

		model.setFotoPerfil(fotoModel);

		Perro perro = perroService.guardar(model);

		response.put("perro", perro);
		response.put("message", "Exito al cargar el perro");
		return ResponseEntity.status(200).body(response);
	}

	@Override
	public ResponseEntity<?> edit(PerroModel model, MultipartFile file) throws Exception {
		
		response = new HashMap<>();
		Optional<Perro> perroOpt = perroService.buscarPorId(model.getId());

		if (perroOpt.isPresent() && perroOpt.get().isActivo()) {

			Foto foto = fotoService.getOne(model.getIdFotoPerfil());

			FotoModel fotoModel = fotoConverter.entityToModel(foto);

			model.setFotoPerfil(fotoModel);

			PerroModel target= perroConverter.entityToModel(perroOpt.get());
			
			target = perroService.pasarAtributos(model, target);
			
			Perro perro = perroService.guardar(target);


			response.put("message", "Exito al actualizar el Perro");
			response.put("perro", perro);
			return ResponseEntity.status(200).body(response);

		} else {
			response.put("message", "No existe ese usuario en los registros");
			return ResponseEntity.status(200).body(response);
		}
	}

	@Override
	public ResponseEntity<?> delete(String id) throws Exception {
		perroService.eliminar(id);
		response.put("message", "Eliminacion exitosa del perro con el id " + id);
		return ResponseEntity.status(200).body(response);
	}

}

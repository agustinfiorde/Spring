package com.perrosv4.app.controladores;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.perrosv4.app.convertidores.FotoConverter;
import com.perrosv4.app.entidades.Foto;
import com.perrosv4.app.excepciones.ResourceNotFoundException;
import com.perrosv4.app.servicios.FotoService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/api/foto")
public class FotoController {

	private Map<String, Object> response = new HashMap<>();
	private Foto foto;
	private final FotoService fotoService;
	private final FotoConverter fotoConverter;

	@GetMapping
	public ResponseEntity<?> list() {
		return null;
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable String id) {

		response.put("foto", fotoService.getOne(id));
		return ResponseEntity.status(200).body(response);
	}

	@PostMapping
	public ResponseEntity<?> loadPhoto(@RequestParam MultipartFile file) throws Exception {

		if (!file.isEmpty()) {
			foto = new Foto();
			foto.setFileName(fotoService.cargarFoto(file));
			foto = fotoService.guardar(fotoConverter.entityToModel(foto));
			response.put("foto", foto);
			response.put("message", "Registro Exitoso");
			return ResponseEntity.status(200).body(response);
		} else {
			throw new Exception("Debes seleccionar una foto para poder cargarla");
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> edit(@PathVariable String id, @RequestParam MultipartFile file) throws Exception {

		if (id == null) {
			throw new Exception("si deseas actualizar una foto debes preseleccionar que foto quieres cambiar");
		}

		if (file.isEmpty()) {
			throw new Exception("si deseas actualizar una foto debes haber cargado una foto");
		} else {

			Optional<Foto> optionalFoto = fotoService.buscarPorId(id);
			if (optionalFoto.isPresent()) {
				foto = fotoService.actualizarFoto(file, id);
				foto = fotoService.guardar(fotoConverter.entityToModel(foto));
				response.put("foto", foto);
				response.put("message", "Modificacion Exitosa");
				return ResponseEntity.status(200).body(response);
			} else {
				throw new ResourceNotFoundException("No se encontro ninguna foto bajo el id :" + id);
			}
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) {
		fotoService.eliminar(id);
		response.put("message", "Eliminacion exitosa de la foto con el id " + id);
		return ResponseEntity.status(200).body(response);
	}

}

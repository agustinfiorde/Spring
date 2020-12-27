package com.perrosv3.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.perrosv3.app.entidades.Foto;
import com.perrosv3.app.servicios.FotoService;

@Controller
@RequestMapping("/foto")
public class FotoController {

	@Autowired
	private FotoService fotoService;

	@GetMapping("/load/{id}")
	public ResponseEntity<byte[]> cargarFoto(@PathVariable String id) {
		Foto photo = fotoService.getOne(id);
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		return new ResponseEntity<>(photo.getContent(), headers, HttpStatus.OK);
	}

	@GetMapping("/lista")
	public String lista(ModelMap modelo) {
		List<Foto> todos = fotoService.listarTodos();
		modelo.addAttribute("fotos", todos);
		return "list-foto";
	}

	@GetMapping("/formulario")
	public String formulario(ModelMap modelo, @RequestParam(required = false) String id) {

		
		Foto foto = fotoService.getOne(id);
		if (!id.equals("")) {
			modelo.addAttribute("foto", foto);
		} else {
			modelo.addAttribute("foto", null);
		}

		return "form-foto";
	}

	@PostMapping("/registro")
	public String guardar(ModelMap modelo, @RequestParam(required = false) String id,
			@RequestParam MultipartFile file) {

		try {

			if (id != null) {
				
				Foto fotoFromWeb = fotoService.multiPartToEntity(file);
				Foto fotoFromDB = fotoService.getOne(id);
				fotoFromDB.setId(id);
				fotoFromDB.setContent(fotoFromWeb.getContent());
				fotoFromDB.setName(fotoFromWeb.getName());
				fotoFromDB.setMime(fotoFromWeb.getMime());
				fotoService.guardar(fotoFromDB);
				
			} else {
				fotoService.guardar(fotoService.multiPartToEntity(file));
			}

			modelo.put("exito", "Registro exitoso");
			return "form-foto";
		} catch (Exception e) {
			modelo.put("error", "Si deseas cambiar la foto seleccionar otra foto");
			Foto foto = fotoService.getOne(id);
			modelo.addAttribute("foto", foto);
			return "form-foto";
		}
	}

	@GetMapping("/baja/{id}")
	public String baja(@PathVariable String id) {

		try {
			fotoService.baja(id);
			return "redirect:/foto/lista";
		} catch (Exception e) {
			return "redirect:/";
		}
	}

	@GetMapping("/alta/{id}")
	public String alta(@PathVariable String id) {

		try {
			fotoService.alta(id);
			return "redirect:/foto/lista";
		} catch (Exception e) {
			return "redirect:/";
		}
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable String id) {

		try {
			fotoService.eliminar(id);
			return "redirect:/foto/lista";
		} catch (Exception e) {
			return "redirect:/";
		}
	}

}

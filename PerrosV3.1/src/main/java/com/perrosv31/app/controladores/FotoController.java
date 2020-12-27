package com.perrosv31.app.controladores;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.perrosv31.app.entidades.Foto;
import com.perrosv31.app.servicios.FotoService;

@Controller
@RequestMapping("/foto")
public class FotoController {

	@Autowired
	private FotoService fotoService;
	
	@Value("${fiordex.carpeta.fotos}")
	private String carpetaFotos;

	@GetMapping("/load/{fileName:.+}")
	public ResponseEntity<Resource> cargarFoto(@PathVariable String fileName) {

		Path pathFoto = Paths.get(carpetaFotos).resolve(fileName).toAbsolutePath();
		Resource recurso = null;
		try {
			
			recurso = new UrlResource(pathFoto.toUri());
			if (!recurso.exists() || !recurso.isReadable()) {
				throw new RuntimeException("Error: no se puede cargar la imagen " + pathFoto.toString());
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
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
	public String guardar(ModelMap modelo, @RequestParam(required = false) String id, @RequestParam MultipartFile file) {

		Foto foto = null;
		try {
			
			if (file.getSize() == 0) {
				
				modelo.put("error", "Si deseas cambiar la foto seleccionar otra foto");
				foto = fotoService.getOne(id);
				modelo.addAttribute("foto", foto);
				return "form-foto";
				
			} else if (id != null) {
				
				foto = fotoService.actualizarFoto(file, id);
				foto = fotoService.guardar(foto);
				
			} else {
				
				foto= new Foto();
				foto.setFileName(fotoService.cargarFoto(file));
				foto = fotoService.guardar(foto);
				
			}
			
			modelo.addAttribute("foto", foto);
			modelo.put("exito", "Registro exitoso");
			return "form-foto";

		} catch (Exception e) {
			
			if (id!=null) {
				modelo.put("error", "Si deseas cambiar la foto debes seleccionar otra foto");
				foto = fotoService.getOne(id);
				modelo.addAttribute("foto", foto);
				return "form-foto";
			} else {
				modelo.put("error", "Si deseas cargar una foto debes seleccionar una foto");
				modelo.addAttribute("foto", null);
				return "form-foto";
			}
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

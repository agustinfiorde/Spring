package com.perrosv2.app.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.perrosv2.app.errores.WebException;
import com.perrosv2.app.servicios.UsuarioService;


@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;


	@GetMapping("/registro")
	public String formulario() {
		return "form-usuario";
	}

	@PostMapping("/registro")
	public String guardar(ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido,
			@RequestParam String email, @RequestParam String clave, @RequestParam String rol) {

		try {
			usuarioService.guardar(nombre, apellido, email, clave, rol);

			modelo.put("exito", "registro exitoso");
			return "form-usuario";
		} catch (WebException e) {
			modelo.put("error", e.getMessage());
			return "form-usuario";
		}
	}

}

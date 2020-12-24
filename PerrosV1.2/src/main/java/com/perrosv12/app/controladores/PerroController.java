package com.perrosv12.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.perrosv12.app.entidades.Perro;
import com.perrosv12.app.servicios.NotificacionService;
import com.perrosv12.app.servicios.PerroService;

@Controller
@RequestMapping("/perro")
public class PerroController {

	@Autowired
	private PerroService perroService;

	@Autowired
	private NotificacionService notificacionService;

	@GetMapping("/lista")
	public String lista(ModelMap modelo) {

		List<Perro> todos = perroService.listarTodos();

		modelo.addAttribute("perros", todos);

		return "list-perro";
	}

	@GetMapping("/registro")
	public String formulario() {
		return "form-perro";
	}

	@PostMapping("/registro")
	public String guardar(ModelMap modelo, @RequestParam String nombre, @RequestParam String apodo,
			@RequestParam String foto, @RequestParam String raza) {

		try {
			perroService.guardar(nombre, apodo, foto, raza);

			modelo.put("exito", "Registro exitoso");
			return "form-perro";
		} catch (Exception e) {
			modelo.put("error", "Falto algun dato");
			return "form-perro";
		}
	}

	@GetMapping("/baja/{id}")
	public String baja(ModelMap modelo, @PathVariable String id) {

		try {
			perroService.baja(id);
			return "redirect:/perro/lista";
		} catch (Exception e) {
			return "redirect:/";
		}

	}

	@GetMapping("/alta/{id}")
	public String alta(ModelMap modelo, @PathVariable String id) {

		try {
			perroService.alta(id);
			return "redirect:/perro/lista";
		} catch (Exception e) {
			return "redirect:/";
		}
	}

	@PostMapping("/mailsender/{id}")
	public String enviarMail(@PathVariable String id, @RequestParam String mail) {

		try {
			notificacionService.notificar(id, mail);
			return "redirect:/";
		} catch (Exception e) {
			return "redirect:/";
		}
	}

}

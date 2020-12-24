package com.perrosv13.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.perrosv13.app.entidades.Dato;
import com.perrosv13.app.entidades.Perro;
import com.perrosv13.app.servicios.DatoService;
import com.perrosv13.app.servicios.PerroService;

@Controller
@RequestMapping("/")
public class MainController {

	@Autowired
	private PerroService perroService;

	@Autowired
	private DatoService datoService;

	@GetMapping("/")
	public String index(ModelMap modelo) {

		List<Perro> perrosActivos = perroService.listarActivos();

		Dato dato = datoService.obtener();

		if (dato != null) {
			modelo.addAttribute("dato", dato);
		}

		modelo.addAttribute("perros", perrosActivos);

		return "index";
	}

}

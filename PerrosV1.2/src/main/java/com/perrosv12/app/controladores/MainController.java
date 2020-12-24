package com.perrosv12.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.perrosv12.app.entidades.Perro;
import com.perrosv12.app.servicios.PerroService;

@Controller
@RequestMapping("/")
public class MainController {

	@Autowired
	private PerroService perroService;
	
	@GetMapping("/")
	public String index(ModelMap modelo) {
		
		List<Perro> perrosActivos = perroService.listarActivos();
		
		modelo.addAttribute("perros", perrosActivos);
		
		return "index";
	}
	
}

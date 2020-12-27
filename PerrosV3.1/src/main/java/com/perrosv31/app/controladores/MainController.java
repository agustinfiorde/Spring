package com.perrosv31.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.perrosv31.app.entidades.Foto;
import com.perrosv31.app.servicios.FotoService;

@Controller
@RequestMapping("/")
public class MainController {

	@Autowired
	private FotoService fotoService;
	
	@GetMapping("/")
	public String index(ModelMap modelo) {
		
		List<Foto> perrosActivos = fotoService.listarActivos();
		modelo.addAttribute("fotos", perrosActivos);
		return "index";
	}
	
}

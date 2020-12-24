package com.perrosv12.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.perrosv12.app.entidades.Perro;
import com.perrosv12.app.servicios.PerroService;

@Controller
@RequestMapping("/mail")
public class MailController {

	@Autowired
	private PerroService perroService;
	
	@RequestMapping
	public String generador(ModelMap model) {
		
		List<Perro> perrosActivos = perroService.listarActivos();
		
		model.addAttribute("perros", perrosActivos);
		
		return "mail-template";

	}

}

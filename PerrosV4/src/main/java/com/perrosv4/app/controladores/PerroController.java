package com.perrosv4.app.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perrosv4.app.convertidores.PerroConverter;
import com.perrosv4.app.modelos.PerroModel;
import com.perrosv4.app.servicios.PerroService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/perro")
public class PerroController implements ControllerInterface<PerroModel>{

	private PerroService perroService;
	private PerroConverter perroConverter;

	@Autowired
	public PerroController(PerroService perroService, PerroConverter perroConverter) {
		this.perroService = perroService;
		this.perroConverter = perroConverter;
	}
	
	@Override
	public ResponseEntity<?> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<PerroModel> getOne(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<PerroModel> create(PerroModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<PerroModel> edit(PerroModel model, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<PerroModel> delete(String id) {
		// TODO Auto-generated method stub
		return null;
	}



}

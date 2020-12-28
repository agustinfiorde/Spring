package com.perrosv4.app.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perrosv4.app.convertidores.FotoConverter;
import com.perrosv4.app.modelos.FotoModel;
import com.perrosv4.app.servicios.FotoService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/foto")
public class FotoController implements ControllerInterface<FotoModel> {

	private FotoService fotoService;
	private FotoConverter perroConverter;

	@Autowired
	public FotoController(FotoService fotoService, FotoConverter perroConverter) {
		this.fotoService = fotoService;
		this.perroConverter = perroConverter;
	}

	@Override
	public ResponseEntity<?> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<FotoModel> getOne(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<FotoModel> create(FotoModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<FotoModel> edit(FotoModel model, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<FotoModel> delete(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}

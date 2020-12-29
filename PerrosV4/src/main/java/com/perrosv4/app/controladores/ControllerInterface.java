package com.perrosv4.app.controladores;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public interface ControllerInterface <M extends Object>{

	Map<String, Object> response = new HashMap<>();
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	@GetMapping
	public ResponseEntity<?> list();

	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable String id);

	@PostMapping
	public ResponseEntity<?> create(@RequestBody M model);

	@PutMapping("/{id}")
	public ResponseEntity<?> edit(@RequestBody M model, @PathVariable String id);

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id);

}

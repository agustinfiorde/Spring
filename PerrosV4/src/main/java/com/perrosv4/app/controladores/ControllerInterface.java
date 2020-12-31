package com.perrosv4.app.controladores;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public interface ControllerInterface<M extends Object> {

	final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	@GetMapping
	public ResponseEntity<?> list() throws Exception;

	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable String id) throws Exception;

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody M model, @RequestParam(required = false) MultipartFile file)
			throws Exception;

	@PutMapping
	public ResponseEntity<?> edit(@Valid @RequestBody M model, @RequestParam(required = false) MultipartFile file)
			throws Exception;

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) throws Exception;

}

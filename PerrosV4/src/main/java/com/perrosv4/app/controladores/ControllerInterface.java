package com.perrosv4.app.controladores;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ControllerInterface <M extends Object>{

	@GetMapping
	public ResponseEntity<?> list();

	@GetMapping("/{id}")
	public ResponseEntity<M> getOne(@PathVariable String id);

	@PostMapping
	public ResponseEntity<M> create(@RequestBody M model);

	@PutMapping("/{id}")
	public ResponseEntity<M> edit(@RequestBody M model, @PathVariable String id);

	@DeleteMapping("/{id}")
	public ResponseEntity<M> delete(@PathVariable String id);

}

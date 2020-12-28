package com.perrosv4.app.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.perrosv4.app.errores.WebException;

public interface ServiceInterface<M extends Object, E extends Object> {

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public abstract E guardar(M m) throws WebException;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public abstract E eliminar(String id) throws WebException;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public abstract E alta(String id) throws WebException;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public abstract E baja(String id) throws WebException;

	@Transactional(readOnly = true)
	public abstract List<E> listarActivos();

	@Transactional(readOnly = true)
	public abstract Page<E> listarTodos(Pageable paginable, String q);

	@Transactional(readOnly = true)
	public abstract Page<E> lsitarTodos(Pageable paginable);

	@Transactional(readOnly = true)
	public abstract Optional<E> buscarPorId(String id);

	@Transactional(readOnly = true)
	public abstract E getOne(String id);
	
	public void validar(M m) throws WebException;
	
}

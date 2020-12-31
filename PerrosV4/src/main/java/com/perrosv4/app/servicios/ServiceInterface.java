package com.perrosv4.app.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.perrosv4.app.excepciones.ValidationError;
import com.perrosv4.app.modelos.UsuarioModel;

public interface ServiceInterface<M extends Object, E extends Object> {

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public abstract E guardar(M m) throws Exception;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public abstract void eliminar(String id) throws Exception;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public abstract E alta(String id) throws Exception;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public abstract E baja(String id) throws Exception;

	@Transactional(readOnly = true)
	public abstract List<E> listarTodos();

	@Transactional(readOnly = true)
	public abstract List<E> listarActivos();

	@Transactional(readOnly = true)
	public abstract Page<E> listarActivos(Pageable paginable);

	@Transactional(readOnly = true)
	public abstract Page<E> buscarPorParametro(Pageable paginable, String q);

	@Transactional(readOnly = true)
	public abstract List<E> buscarPorParametro(String q);

	@Transactional(readOnly = true)
	public abstract Optional<E> buscarPorId(String id);

	@Transactional(readOnly = true)
	public abstract E getOne(String id);

	public void validar(M m) throws ValidationError;
	
	public M pasarAtributos(M source, M target);

}

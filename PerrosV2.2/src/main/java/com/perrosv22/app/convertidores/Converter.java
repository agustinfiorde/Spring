package com.perrosv22.app.convertidores;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.perrosv22.app.errores.WebException;

public abstract class Converter<M extends Object, E extends Object> {
	
	public abstract E modelToEntity(M m) throws WebException;

	public abstract M entityToModel(E e) throws WebException;
	
	public abstract List<E> modelsToEntities(List<M> m) throws WebException;
	
	public abstract List<M> entitiesToModels(List<E> e) throws WebException;
	
	protected Log log;

	public Converter() {
		this.log = LogFactory.getLog(getClass());
	}

}

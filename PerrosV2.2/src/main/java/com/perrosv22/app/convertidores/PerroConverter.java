package com.perrosv22.app.convertidores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.perrosv22.app.entidades.Perro;
import com.perrosv22.app.entidades.Usuario;
import com.perrosv22.app.errores.WebException;
import com.perrosv22.app.modelos.PerroModel;
import com.perrosv22.app.repositorios.PerroRepository;
import com.perrosv22.app.repositorios.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Component("PerroConverter")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PerroConverter extends Converter<PerroModel, Perro> {

	private final PerroRepository perroRepository;
	private final UsuarioRepository usuarioRepository;
	private final UsuarioConverter usuarioConverter;

	public PerroModel entityToModel(Perro entity) throws WebException {
		PerroModel model = new PerroModel();
		try {

			
			if (entity.getUsuario() != null) {
				model.setIdUsuario(entity.getUsuario().getId());
				model.setUsuario(usuarioConverter.entityToModel(usuarioRepository.getOne(entity.getUsuario().getId())));
			}

			BeanUtils.copyProperties(entity, model);

		} catch (Exception e) {
			throw new WebException("Error al convertir la entidad " + entity.toString() + " a modelo");
		}

		return model;
	}

	public Perro modelToEntity(PerroModel model) throws WebException {

		Perro entity;

		if (model.getId() != null && !model.getId().isEmpty()) {
			entity = perroRepository.getOne(model.getId());
		} else {
			entity = new Perro();
		}

		try {

			Usuario entityUser = null;
			if (model.getIdUsuario() != null) {
				entityUser = usuarioRepository.getOne(model.getIdUsuario());
			}

			entity.setUsuario(entityUser);
			
			BeanUtils.copyProperties(model, entity);
			
		} catch (Exception e) {
			throw new WebException("error al convertir el modelo " + model.toString() + " a entidad");
		}

		return entity;
	}

	public List<PerroModel> entitiesToModels(List<Perro> entities) throws WebException {
		List<PerroModel> models = new ArrayList<>();
		for (Perro a : entities) {
			models.add(entityToModel(a));
		}
		return models;
	}

	@Override
	public List<Perro> modelsToEntities(List<PerroModel> m) throws WebException {
		List<Perro> entities = new ArrayList<>();
		for (PerroModel model : m) {
			entities.add(modelToEntity(model));
		}
		return entities;
	}

}

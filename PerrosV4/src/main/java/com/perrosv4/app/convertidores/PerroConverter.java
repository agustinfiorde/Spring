package com.perrosv4.app.convertidores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.perrosv4.app.entidades.Perro;
import com.perrosv4.app.modelos.PerroModel;
import com.perrosv4.app.repositorios.PerroRepository;

@Component("PerroConverter")
public class PerroConverter extends Converter<PerroModel, Perro> {

	private PerroRepository perroRepository;

	private FotoConverter fotoConverter;

	@Autowired
	public PerroConverter(PerroRepository perroRepository, FotoConverter fotoConverter) {
		this.perroRepository = perroRepository;
		this.fotoConverter = fotoConverter;
	}

	public PerroModel entityToModel(Perro entity) {
		PerroModel model = new PerroModel();
		try {

			if (entity.getFotoPerfil() != null) {
				model.setFotoPerfil(fotoConverter.entityToModel(entity.getFotoPerfil()));
			}

			BeanUtils.copyProperties(entity, model);

		} catch (Exception e) {
			log.error("Error al convertir la entidad en el modelo del Perro", e);
		}

		return model;
	}

	public Perro modelToEntity(PerroModel model) {

		Perro entity;

		if (model.getId() != null && !model.getId().isEmpty()) {
			entity = perroRepository.getOne(model.getId());
		} else {
			entity = new Perro();
		}

		try {

			if (model.getFotoPerfil() != null) {
				entity.setFotoPerfil(fotoConverter.modelToEntity(model.getFotoPerfil()));
			}

			BeanUtils.copyProperties(model, entity);
		} catch (Exception e) {
			log.error("Error al convertir el modelo del Perro en entidad", e);
		}

		return entity;
	}

	public List<PerroModel> entitiesToModels(List<Perro> entities) {
		List<PerroModel> models = new ArrayList<>();
		for (Perro a : entities) {
			models.add(entityToModel(a));
		}
		return models;
	}

	@Override
	public List<Perro> modelsToEntities(List<PerroModel> m) {
		List<Perro> entities = new ArrayList<>();
		for (PerroModel model : m) {
			entities.add(modelToEntity(model));
		}
		return entities;
	}

}

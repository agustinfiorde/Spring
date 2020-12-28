package com.perrosv4.app.convertidores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.perrosv4.app.entidades.Foto;
import com.perrosv4.app.modelos.FotoModel;
import com.perrosv4.app.repositorios.FotoRepository;

@Component("FotoConverter")
public class FotoConverter extends Converter<FotoModel, Foto> {

	private FotoRepository fotoRepository;

	@Autowired
	public FotoConverter(FotoRepository fotoRepository) {
		this.fotoRepository = fotoRepository;
	}

	public FotoModel entityToModel(Foto entity) {
		FotoModel model = new FotoModel();
		try {
			BeanUtils.copyProperties(entity, model);
		} catch (Exception e) {
			log.error("Error al convertir la entidad en el modelo de la Foto", e);
		}

		return model;
	}

	public Foto modelToEntity(FotoModel model) {
		Foto entity;
		if (model.getId() != null && !model.getId().isEmpty()) {
			entity = fotoRepository.getOne(model.getId());
		} else {
			entity = new Foto();
		}

		try {
			BeanUtils.copyProperties(model, entity);
		} catch (Exception e) {
			log.error("Error al convertir el modelo de la Foto en la entidad", e);
		}

		return entity;
	}

	public List<FotoModel> entitiesToModels(List<Foto> entities) {
		List<FotoModel> models = new ArrayList<>();
		for (Foto a : entities) {
			models.add(entityToModel(a));
		}
		return models;
	}

	@Override
	public List<Foto> modelsToEntities(List<FotoModel> m) {
		List<Foto> entities = new ArrayList<>();
		for (FotoModel model : m) {
			entities.add(modelToEntity(model));
		}
		return entities;
	}

}

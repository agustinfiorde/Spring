package com.perrosv4.app.convertidores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.perrosv4.app.entidades.Perro;
import com.perrosv4.app.excepciones.ConversionError;
import com.perrosv4.app.modelos.PerroModel;
import com.perrosv4.app.repositorios.PerroRepository;

import lombok.RequiredArgsConstructor;

@Component("PerroConverter")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PerroConverter extends Converter<PerroModel, Perro> {

	private final PerroRepository perroRepository;
	private final FotoConverter fotoConverter;

	public PerroModel entityToModel(Perro entity) {
		PerroModel model = new PerroModel();
		try {

			if (entity.getFotoPerfil() != null) {
				model.setFotoPerfil(fotoConverter.entityToModel(entity.getFotoPerfil()));
			}

			BeanUtils.copyProperties(entity, model);

		} catch (Exception e) {
			throw new ConversionError("Error al convertir la entidad "+entity.toString()+" a modelo"  );
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
			throw new ConversionError("error al convertir el modelo "+model.toString()+" a entidad");
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

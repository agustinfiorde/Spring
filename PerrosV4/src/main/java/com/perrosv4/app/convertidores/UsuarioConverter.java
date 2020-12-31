package com.perrosv4.app.convertidores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.perrosv4.app.entidades.Perro;
import com.perrosv4.app.entidades.Usuario;
import com.perrosv4.app.excepciones.ConversionError;
import com.perrosv4.app.modelos.PerroModel;
import com.perrosv4.app.modelos.UsuarioModel;
import com.perrosv4.app.repositorios.PerroRepository;
import com.perrosv4.app.repositorios.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Component("UsuarioConverter")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UsuarioConverter extends Converter<UsuarioModel, Usuario> {

	private final UsuarioRepository usuarioRepository;
	private final PerroRepository perroRepository;
	private final PerroConverter perroConverter;
	private final FotoConverter fotoConverter;

	public UsuarioModel entityToModel(Usuario entity) {
		UsuarioModel model = new UsuarioModel();
		try {

			if (entity.getFotoPerfil() != null) {
				model.setFotoPerfil(fotoConverter.entityToModel(entity.getFotoPerfil()));
			}

			if (entity.getPerros() != null) {
				StringBuilder seleccionada = new StringBuilder();
				List<PerroModel> subModels = new ArrayList<>();

				for (Perro objeto : entity.getPerros()) {

					subModels.add(perroConverter.entityToModel(objeto));
					model.getIdPerros().add(objeto.getId());
					seleccionada.append(objeto.getId()).append(",");

				}

				model.setPerros(subModels);
				model.setPerrosSeleccionados(seleccionada.toString());
			}

			BeanUtils.copyProperties(entity, model);

		} catch (Exception e) {
			throw new ConversionError("Error al convertir la entidad " + entity.toString() + " a modelo");
		}

		return model;
	}

	public Usuario modelToEntity(UsuarioModel model) {

		Usuario entity;

		if (model.getId() != null && !model.getId().isEmpty()) {
			entity = usuarioRepository.getOne(model.getId());
		} else {
			entity = new Usuario();
		}

		try {

			BeanUtils.copyProperties(model, entity);
			
			if (model.getFotoPerfil() != null) {
				entity.setFotoPerfil(fotoConverter.modelToEntity(model.getFotoPerfil()));
			}

			if ( model.getIdPerros() != null && model.getIdPerros().size() > 0) {

				List<Perro> list = new ArrayList<>();
				for (String id : model.getIdPerros()) {

					Perro subEntity = perroRepository.getOne(id);
					list.add(subEntity);

				}

				entity.setPerros(list);
			}
			
		} catch (Exception e) {
			throw new ConversionError("error al convertir el modelo " + model.toString() + " a entidad");
		}

		return entity;
	}

	public List<UsuarioModel> entitiesToModels(List<Usuario> entities) {
		List<UsuarioModel> models = new ArrayList<>();
		for (Usuario a : entities) {
			models.add(entityToModel(a));
		}
		return models;
	}

	@Override
	public List<Usuario> modelsToEntities(List<UsuarioModel> m) {
		List<Usuario> entities = new ArrayList<>();
		for (UsuarioModel model : m) {
			entities.add(modelToEntity(model));
		}
		return entities;
	}

}

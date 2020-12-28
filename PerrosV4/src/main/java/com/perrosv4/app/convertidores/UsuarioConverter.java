package com.perrosv4.app.convertidores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.perrosv4.app.entidades.Perro;
import com.perrosv4.app.entidades.Usuario;
import com.perrosv4.app.modelos.PerroModel;
import com.perrosv4.app.modelos.UsuarioModel;
import com.perrosv4.app.repositorios.PerroRepository;
import com.perrosv4.app.repositorios.UsuarioRepository;

@Component("UsuarioConverter")
public class UsuarioConverter extends Converter<UsuarioModel, Usuario> {

	private UsuarioRepository usuarioRepository;
	private PerroRepository perroRepository;
	private PerroConverter perroConverter;
	private FotoConverter fotoConverter;

	@Autowired
	public UsuarioConverter(UsuarioRepository usuarioRepository, PerroConverter perroConverter,
			FotoConverter fotoConverter, PerroRepository perroRepository) {
		this.usuarioRepository = usuarioRepository;
		this.perroConverter = perroConverter;
		this.fotoConverter = fotoConverter;
		this.perroRepository = perroRepository;
	}

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
			log.error("Error al convertir la entidad en el modelo del Usuario", e);
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

			if (model.getFotoPerfil() != null) {
				entity.setFotoPerfil(fotoConverter.modelToEntity(model.getFotoPerfil()));
			}

			if (!model.getPerrosSeleccionados().equals("") && model.getIdPerros() != null
					&& model.getIdPerros().size() > 0) {

				List<Perro> list = new ArrayList<>();
				for (String id : model.getIdPerros()) {

					Perro subEntity = perroRepository.getOne(id);
					list.add(subEntity);

				}

				entity.setPerros(list);
			}

			BeanUtils.copyProperties(model, entity);
		} catch (Exception e) {
			log.error("Error al convertir el modelo del Usuario en entidad", e);
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

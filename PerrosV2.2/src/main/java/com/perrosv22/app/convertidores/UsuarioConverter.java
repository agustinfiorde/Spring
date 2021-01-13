package com.perrosv22.app.convertidores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.perrosv22.app.entidades.Usuario;
import com.perrosv22.app.errores.WebException;
import com.perrosv22.app.modelos.UsuarioModel;
import com.perrosv22.app.repositorios.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Component("UsuarioConverter")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UsuarioConverter extends Converter<UsuarioModel, Usuario> {

	private final UsuarioRepository usuarioRepository;

	public UsuarioModel entityToModel(Usuario entity) throws WebException {
		UsuarioModel model = new UsuarioModel();
		try {

			BeanUtils.copyProperties(entity, model);

		} catch (Exception e) {
			throw new WebException("Error al convertir la entidad " + entity.toString() + " a modelo");
		}
		return model;
	}

	public Usuario modelToEntity(UsuarioModel model) throws WebException {

		Usuario entity;
		String clave = null;
		
		if (model.getId() != null && !model.getId().isEmpty()) {
			entity = usuarioRepository.getOne(model.getId());
			clave = entity.getClave();
		} else {
			entity = new Usuario();
		}

		try {
			
			BeanUtils.copyProperties(model, entity);
			
			if (model.getId()==null || model.getId().isEmpty()) {
				entity.setClave(new BCryptPasswordEncoder().encode(entity.getClave()));
			}
			
			if ( model.getClave().isEmpty() && !clave.isEmpty()) {
				entity.setClave(clave);
			}else {
				entity.setClave(new BCryptPasswordEncoder().encode(entity.getClave()));
			}
			
		} catch (Exception e) {
			throw new WebException("error al convertir el modelo " + model.toString() + " a entidad");
		}

		return entity;
	}

	public List<UsuarioModel> entitiesToModels(List<Usuario> entities) throws WebException {
		List<UsuarioModel> models = new ArrayList<>();
		for (Usuario a : entities) {
			models.add(entityToModel(a));
		}
		return models;
	}

	@Override
	public List<Usuario> modelsToEntities(List<UsuarioModel> m) throws WebException {
		List<Usuario> entities = new ArrayList<>();
		for (UsuarioModel model : m) {
			entities.add(modelToEntity(model));
		}
		return entities;
	}

}

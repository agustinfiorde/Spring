package com.perrosv22.app.modelos;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PerroModel implements Serializable {

	private static final long serialVersionUID = 6522896498689132123L;

	private String id;

	private UsuarioModel usuario;
	private String idUsuario;

	private String foto;

	private String nombre;
	private String apodo;
	private String raza;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date creado;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date editado;

	private boolean activo;
}

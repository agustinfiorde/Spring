package com.perrosv22.app.modelos;

import java.io.Serializable;
import java.util.Date;

import com.perrosv22.app.enums.Rol;

import lombok.Data;

@Data
public class UsuarioModel implements Serializable {

	private static final long serialVersionUID = 6522896498689132123L;

	private String id;

	private String nombre;
	private String apellido;
	private String dni;
	private String email;
	private String clave;

	private String nacimientoString;

	private Rol rol;

	private Date creado;

	private Date editado;

	private boolean activo;
}

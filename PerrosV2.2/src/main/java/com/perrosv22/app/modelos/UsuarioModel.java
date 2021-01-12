package com.perrosv22.app.modelos;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

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

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date nacimiento;

	private Rol rol;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date creado;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date editado;

	private boolean activo;
}

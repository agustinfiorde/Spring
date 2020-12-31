package com.perrosv4.app.modelos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.perrosv4.app.enums.Rol;

import lombok.Data;

@Data
public class UsuarioModel implements Serializable {

	private static final long serialVersionUID = 6522896498689132123L;

	private String id;

	private String idFotoPerfil;
	private FotoModel fotoPerfil;

	private List<PerroModel> perros;
	private List<String> idPerros = new ArrayList<>();
	private String perrosSeleccionados = "";

	private String nombre;
	private String apellido;
	private String dni;

	private Date nacimiento;

	private Rol rol;

	private Date creado;

	private Date editado;

	private boolean activo;
}

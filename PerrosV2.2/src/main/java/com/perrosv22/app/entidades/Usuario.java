package com.perrosv22.app.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.perrosv22.app.enums.Rol;

import lombok.Data;

@Data
@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = 6522896498689132123L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
		
	private String nombre;
	private String apellido;
	private String dni;
	private String email;
	private String clave;
	
	@Temporal(TemporalType.DATE)
	private Date nacimiento;
	
	@Enumerated(EnumType.STRING)
	private Rol rol;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date creado;

	@Temporal(TemporalType.TIMESTAMP)
	private Date editado;

	private boolean activo;
}

package com.perrosv22.app.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
public class Perro implements Serializable {

	private static final long serialVersionUID = 6522893498689132123L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario usuario;
	
	private String foto;

	private String nombre;
	private String apodo;
	private String raza;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creado;

	@Temporal(TemporalType.TIMESTAMP)
	private Date editado;

	private boolean activo;
}

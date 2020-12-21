package com.perrosv2.app.entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
public class Perro {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	private String nombre;

	private String raza;

	private String apodo;

	private String foto;

	@Temporal(TemporalType.DATE)
	private Date creado;

	@Temporal(TemporalType.DATE)
	private Date editado;

	private boolean activo;

}

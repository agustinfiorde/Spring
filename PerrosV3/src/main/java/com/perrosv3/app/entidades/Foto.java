package com.perrosv3.app.entidades;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Data
public class Foto {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	private String name;
	private String mime;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] content;

	@Temporal(TemporalType.DATE)
	private Date creado;

	@Temporal(TemporalType.DATE)
	private Date editado;

	private boolean activo;

}

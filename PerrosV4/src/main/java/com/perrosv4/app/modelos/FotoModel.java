package com.perrosv4.app.modelos;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class FotoModel implements Serializable {

	private static final long serialVersionUID = 6522896498689132123L;
	
	private String id;
	
	private String uri;
	private String fileName;

	private Date creado;

	private Date editado;

	private boolean activo;
	
}

package com.perrosv22.app.controladores;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

import com.perrosv22.app.errores.WebException;
import com.perrosv22.app.servicios.UsuarioService;
import com.perrosv22.app.utils.Texts;

public abstract class Controlador {
	protected String listView;
	protected String formView;

	@Autowired
	protected UsuarioService userService;

	protected Log log;

	public Controlador(String list, String form) {
		this.listView = list;
		this.formView = form;
		this.log = LogFactory.getLog(getClass());
	}

	public String getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}

	@SuppressWarnings("rawtypes")
	public String getRol() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Iterator it = auth.getAuthorities().iterator();
		while (it.hasNext()) {
			return it.next().toString();
		}
		return "";
	}

	public boolean isAdmin() {
		return getRol().equals("ROLE_ADMIN");
	}

	public boolean isSuperAdmin() {
		return getRol().equals("ROLE_SUPERADMIN");
	}

	public void error(ModelAndView model, Exception e) {
		model.addObject(Texts.ERROR, "Ocurrio un error inesperado mientras se ejecutaba la acción.");
		log.error("Error inesperado", e);
	}

	public void error(ModelAndView model, WebException e) {
		model.addObject(Texts.ERROR, e.getMessage());
	}

	public void error(ModelMap modelo, BindingResult resultado) {
		StringBuilder msg = new StringBuilder();
		for (ObjectError o : resultado.getAllErrors()) {
			msg.append(o.getDefaultMessage() + System.getProperty("line.separator"));
		}
		log.info("Error: " + msg.toString());
		modelo.addAttribute(Texts.ERROR, msg.toString());
	}

	public void error(ModelAndView model, String e) {
		model.addObject(Texts.ERROR, e);
		model.setViewName(formView);
	}

	public void error(Model model, String e) {
		model.addAttribute(Texts.ERROR, e);
	}

}

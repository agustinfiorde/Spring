package com.perrosv22.app.controladores;

import static com.perrosv22.app.utils.Texts.ACTION_LABEL;
import static com.perrosv22.app.utils.Texts.ERROR;
import static com.perrosv22.app.utils.Texts.ERROR_UNEXPECTED;
import static com.perrosv22.app.utils.Texts.PAGE_LABEL;
import static com.perrosv22.app.utils.Texts.SAVE_LABEL;
import static com.perrosv22.app.utils.Texts.URL_LABEL;
import static com.perrosv22.app.utils.Texts.USUARIO_FORM_LABEL;
import static com.perrosv22.app.utils.Texts.USUARIO_LABEL;
import static com.perrosv22.app.utils.Texts.USUARIO_LIST_LABEL;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.perrosv22.app.convertidores.UsuarioConverter;
import com.perrosv22.app.entidades.Usuario;
import com.perrosv22.app.errores.WebException;
import com.perrosv22.app.modelos.UsuarioModel;
import com.perrosv22.app.servicios.UsuarioService;
import com.perrosv22.app.utils.Texts;

@Controller
@RequestMapping("/usuario")
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
public class UsuarioController extends Controlador {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioConverter usuarioConverter;

	public UsuarioController() {
		super(USUARIO_LIST_LABEL, USUARIO_FORM_LABEL);
	}

	@GetMapping("/list")
	public ModelAndView toList(HttpSession session, Pageable paginable, @RequestParam(required = false) String q)
			throws WebException {
		ModelAndView model = new ModelAndView(listView);

		Page<Usuario> page;
		Page<UsuarioModel> pageModel;

		if (isAdmin()) {
			if (q == null || q.isEmpty()) {
				page = usuarioService.listarActivos(paginable);
			} else {
				page = usuarioService.buscarActivosPorParametro(paginable, q);
				model.addObject(Texts.QUERY_LABEL, q);
			}
			pageModel = new PageImpl<>(usuarioConverter.entitiesToModels(page.getContent()));

			model.addObject(PAGE_LABEL, pageModel);
			log.info("METHOD: usuario.toList() -- PARAMS: " + paginable);
			model.addObject(URL_LABEL, "/usuario/list");
			model.addObject(USUARIO_LABEL, new UsuarioModel());
			return model;
		} else {
			if (q == null || q.isEmpty()) {
				page = usuarioService.listarTodos(paginable);
			} else {
				page = usuarioService.buscarPorParametro(paginable, q);
				model.addObject(Texts.QUERY_LABEL, q);
			}
			pageModel = new PageImpl<>(usuarioConverter.entitiesToModels(page.getContent()));
			model.addObject(PAGE_LABEL, pageModel);
			log.info("METHOD: usuario.toList() -- PARAMS: " + paginable);
			model.addObject(URL_LABEL, "/usuario/list");
			return model;
		}
	}

	@GetMapping("/form")
	public ModelAndView form(HttpSession session, @RequestParam(required = false) String id,
			@RequestParam(required = false) String action) {

		ModelAndView model = new ModelAndView(formView);
		UsuarioModel modelE = new UsuarioModel();
		if (action == null || action.isEmpty()) {
			action = SAVE_LABEL;
		}
		try {
			if (id != null)
				modelE = usuarioConverter.entityToModel(usuarioService.getOne(id));
		} catch (WebException e) {
			e.printStackTrace();
		}
		loadModel(model.getModelMap(), modelE, action);
		return model;
	}

	@PostMapping("/save")
	public String save(HttpSession session, @Valid @ModelAttribute(USUARIO_LABEL) UsuarioModel modelE,
			BindingResult result, ModelMap model) {
		log.info("METHOD: usuario.save -- PARAMETROS: " + modelE);

		try {
			if (result.hasErrors()) {
				error(model, result);
			} else {
				usuarioService.guardar(modelE);
				return "redirect:/usuario/list";
			}
		} catch (WebException e) {
			loadModel(model, modelE, "update");
			model.addAttribute(ERROR, "Ocurrió un error al intentar modificar el usuario. " + e.getMessage());
		} catch (Exception e) {
			loadModel(model, modelE, "update");
			model.addAttribute(ERROR, "Ocurrió un error inesperado al intentar modificar el usuario.");
			log.error(ERROR_UNEXPECTED, e);
		}
		return formView;
	}

	@PreAuthorize("hasRole('ROLE_SUPERADMIN')")
	@PostMapping("/delete")
	public String delete(@ModelAttribute(USUARIO_LABEL) UsuarioModel modelE, ModelMap model) {
		log.info("METHOD: usuario.delete() -- PARAMETROS: " + modelE);
		model.addAttribute(ACTION_LABEL, "delete");

		try {
			usuarioService.eliminar(modelE.getId());
			return "redirect:/usuario/list";
		} catch (Exception e) {
			model.addAttribute(ERROR, "Ocurrió un error inesperado al intentar eliminar la Tecnologia.");
			return formView;
		}
	}

	@PreAuthorize("hasRole('ROLE_SUPERADMIN')")
	@GetMapping("/baja/{id}")
	public String baja(@PathVariable String id) {
		try {
			usuarioService.baja(id);
			return "redirect:/usuario/list";
		} catch (Exception e) {
			return "redirect:/";
		}
	}

	@PreAuthorize("hasRole('ROLE_SUPERADMIN')")
	@GetMapping("/alta/{id}")
	public String alta(@PathVariable String id) {
		try {
			usuarioService.alta(id);
			return "redirect:/usuario/list";
		} catch (Exception e) {
			return "redirect:/";
		}
	}

	private void loadModel(ModelMap modelo, UsuarioModel modelE, String action) {
		modelo.addAttribute(USUARIO_LABEL, modelE);
		modelo.addAttribute(ACTION_LABEL, action);
	}

}

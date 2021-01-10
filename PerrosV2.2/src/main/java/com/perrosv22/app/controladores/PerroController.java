package com.perrosv22.app.controladores;

import static com.perrosv22.app.utils.Texts.ACTION_LABEL;
import static com.perrosv22.app.utils.Texts.ERROR;
import static com.perrosv22.app.utils.Texts.ERROR_UNEXPECTED;
import static com.perrosv22.app.utils.Texts.PAGE_LABEL;
import static com.perrosv22.app.utils.Texts.PERRO_FORM_LABEL;
import static com.perrosv22.app.utils.Texts.PERRO_LABEL;
import static com.perrosv22.app.utils.Texts.PERRO_LIST_LABEL;
import static com.perrosv22.app.utils.Texts.SAVE_LABEL;
import static com.perrosv22.app.utils.Texts.URL_LABEL;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.perrosv22.app.convertidores.PerroConverter;
import com.perrosv22.app.entidades.Perro;
import com.perrosv22.app.errores.WebException;
import com.perrosv22.app.modelos.PerroModel;
import com.perrosv22.app.servicios.PerroService;
import com.perrosv22.app.utils.Texts;

@Controller
@RequestMapping("/perro")
public class PerroController extends Controlador {

	@Autowired
	private PerroService perroService;

	@Autowired
	private PerroConverter perroConverter;

	public PerroController() {
		super(PERRO_LIST_LABEL, PERRO_FORM_LABEL);
	}

	@GetMapping("/list")
	public ModelAndView toList(HttpSession session, Pageable paginable, @RequestParam(required = false) String q) {
		ModelAndView model = new ModelAndView(listView);

		Page<Perro> page;

		if (q == null || q.isEmpty()) {
			page = perroService.listarActivos(paginable);
		} else {
			page = perroService.buscarPorParametro(paginable, q);
			model.addObject(Texts.QUERY_LABEL, q);
		}

		model.addObject(PAGE_LABEL, page);

		log.info("METHOD: perro.toList() -- PARAMS: " + paginable);

		model.addObject(URL_LABEL, "/perro/list");
		model.addObject(PERRO_LABEL, new PerroModel());

		return model;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/save")
	public String save(HttpSession session, @Valid @ModelAttribute(PERRO_LABEL) PerroModel modelE, BindingResult result,
			ModelMap model) {
		log.info("METHOD: perro.save -- PARAMETROS: " + modelE);

		try {
			if (result.hasErrors()) {
				error(model, result);
			} else {
				perroService.guardar(modelE);
				return "redirect:/perro/list";
			}
		} catch (WebException e) {
			loadModel(model, modelE, "update");
			model.addAttribute(ERROR, "Ocurrió un error al intentar modificar la Tecnologia. " + e.getMessage());
		} catch (Exception e) {
			loadModel(model, modelE, "update");
			model.addAttribute(ERROR, "Ocurrió un error inesperado al intentar modificar la Tecnologia.");
			log.error(ERROR_UNEXPECTED, e);
		}
		return formView;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/recover")
	public String refrescar(@RequestParam(required = false) String id) {

		try {
			perroService.alta(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/perro/list";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/delete")
	public String delete(@ModelAttribute(PERRO_LABEL) PerroModel modelE, ModelMap model) {
		log.info("METHOD: perro.delete() -- PARAMETROS: " + modelE);
		model.addAttribute(ACTION_LABEL, "delete");

		try {
			perroService.eliminar(modelE.getId());
			return "redirect:/perro/list";
		} catch (Exception e) {
			model.addAttribute(ERROR, "Ocurrió un error inesperado al intentar eliminar la Tecnologia.");
			return formView;
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/form")
	public ModelAndView form(@RequestParam(required = false) String id, @RequestParam(required = false) String action) {

		ModelAndView model = new ModelAndView(formView);

		PerroModel modelE = new PerroModel();
		if (action == null || action.isEmpty()) {
			action = SAVE_LABEL;
		}

		try {
			if (id != null)
				modelE = perroConverter.entityToModel(perroService.getOne(id));
		} catch (WebException e) {
			e.printStackTrace();
		}

		loadModel(model.getModelMap(), modelE, action);

		return model;
	}

	private void loadModel(ModelMap modelo, PerroModel modelE, String action) {

		modelo.addAttribute(PERRO_LABEL, modelE);
		modelo.addAttribute(ACTION_LABEL, action);

	}

	@GetMapping("/tabla")
	public String lista(ModelMap modelo) {

		List<Perro> todos = perroService.listarTodos();

		modelo.addAttribute("perros", todos);

		return "tabla-perros";
	}

	@GetMapping("/baja/{id}")
	public String baja(@PathVariable String id) {

		try {
			perroService.baja(id);
			return "redirect:/perro/lista";
		} catch (Exception e) {
			return "redirect:/";
		}

	}

	@GetMapping("/alta/{id}")
	public String alta(@PathVariable String id) {

		try {
			perroService.alta(id);
			return "redirect:/perro/lista";
		} catch (Exception e) {
			return "redirect:/";
		}
	}

}

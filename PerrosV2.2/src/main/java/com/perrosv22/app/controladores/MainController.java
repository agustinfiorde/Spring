package com.perrosv22.app.controladores;

import static com.perrosv22.app.utils.Texts.INDEX_LABEL;
import static com.perrosv22.app.utils.Texts.LOGIN_LABEL;
import static com.perrosv22.app.utils.Texts.USUARIO_LABEL;
import static com.perrosv22.app.utils.Texts.USUARIO_REGISTRO_LABEL;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.perrosv22.app.entidades.Perro;
import com.perrosv22.app.errores.WebException;
import com.perrosv22.app.modelos.UsuarioModel;
import com.perrosv22.app.servicios.PerroService;
import com.perrosv22.app.servicios.UsuarioService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MainController {

	private final PerroService perroService;
	private final UsuarioService usuarioService;

	@GetMapping("/lilith")
	public String lilith() {
		usuarioService.lilith();
		return INDEX_LABEL;
	}

	@GetMapping("/registro")
	public String registro(ModelMap modelo) {
		modelo.addAttribute("usuario", new UsuarioModel());
		return USUARIO_REGISTRO_LABEL;
	}

	@PostMapping("/registro")
	public String save(HttpSession session, @Valid @ModelAttribute(USUARIO_LABEL) UsuarioModel modelE,
			BindingResult result, ModelMap modelo) {
		try {
			usuarioService.guardar(modelE);
		} catch (WebException e) {
			modelo.addAttribute("usuario", modelE);
			modelo.addAttribute("error", e.getMessage());
			return USUARIO_REGISTRO_LABEL;
		}
		return INDEX_LABEL;
	}

	@GetMapping("/")
	public String index(ModelMap modelo) {
		List<Perro> perrosActivos = perroService.listarActivos();
		modelo.addAttribute("perros", perrosActivos);
		return INDEX_LABEL;
	}

	@GetMapping("/login")
	public String login(HttpSession session, Authentication usuario, ModelMap modelo,
			@RequestParam(required = false) String error) {
		try {
			if (usuario.getName() != null) {
				return "redirect:/usuario/list";
			} else {

				if (error != null && !error.isEmpty()) {
					modelo.addAttribute("error", "La dirección de mail o la contraseña que ingresó son incorrectas.");
				}
				return LOGIN_LABEL;
			}
		} catch (Exception e) {
			if (error != null && !error.isEmpty()) {
				modelo.addAttribute("error", "La dirección de mail o la contraseña que ingresó son incorrectas.");
			}
			return LOGIN_LABEL;
		}
	}

	@GetMapping("/loginsuccess")
	public String loginresolver() {
		return "redirect:/usuario/list";
	}

}

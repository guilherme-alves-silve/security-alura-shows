package br.com.alura.owasp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.alura.owasp.dao.UsuarioDao;
import br.com.alura.owasp.dto.UsuarioDTO;
import br.com.alura.owasp.model.Role;
import br.com.alura.owasp.model.Usuario;
import br.com.alura.owasp.retrofit.google.GoogleRecaptchaWebClient;
import br.com.alura.owasp.util.ImagemUtil;
import br.com.alura.owasp.validator.ImagemValidator;

@Controller
@Transactional
public class UsuarioController {

	private final UsuarioDao dao;
	private final GoogleRecaptchaWebClient googleRecaptchaWebClient;
	private final ImagemValidator imagemValidator;
	private final ImagemUtil imagemUtil;

	@Autowired
	public UsuarioController(UsuarioDao dao, GoogleRecaptchaWebClient googleWebClient, ImagemValidator imagemValidator,
			ImagemUtil imagemUtil) {
		this.dao = dao;
		this.googleRecaptchaWebClient = googleWebClient;
		this.imagemValidator = imagemValidator;
		this.imagemUtil = imagemUtil;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setAllowedFields("email", "senha", "nome", "nomeImagem");
	}

	@RequestMapping("/usuario")
	public String usuario(Model model) {
		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);
		return "usuario";
	}

	@RequestMapping("/usuarioLogado")
	public String usuarioLogado() {
		return "usuarioLogado";
	}

	@RequestMapping(value = "/registrar", method = RequestMethod.POST)
	public String registrar(MultipartFile imagem, @ModelAttribute("usuarioRegistro") UsuarioDTO usuario,
			RedirectAttributes redirect, HttpServletRequest request, Model model,
			HttpSession session) throws IllegalStateException, IOException {

		if (!imagemValidator.ehValido(imagem)) {
			redirect.addFlashAttribute("mensagem", "A imagem enviada não é válida!");
			return "redirect:/usuario";
		}
		
		Usuario usuarioRegistro = usuario.montaUsuario();
		imagemUtil.trataImagem(imagem, usuarioRegistro, request);
		usuarioRegistro.getRoles().add(new Role("ROLE_USER"));

		dao.salva(usuarioRegistro);
		session.setAttribute("usuario", usuarioRegistro);
		model.addAttribute("usuario", usuarioRegistro);
		return "usuarioLogado";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes redirect, Model model,
			HttpSession session, HttpServletRequest request) throws IOException {

		String recaptcha = request.getParameter("g-recaptcha-response");
		if (googleRecaptchaWebClient.verifica(recaptcha)) {
			return procuraUsuario(usuario, redirect, model, session);
		}

		redirect.addFlashAttribute("mensagem", "Por favor, confirme que você é humano!");

		return "redirect:/usuario";
	}

	private String procuraUsuario(Usuario usuario, RedirectAttributes redirect, Model model, HttpSession session) {
		Usuario usuarioRetornado = dao.procuraUsuario(usuario);
		model.addAttribute("usuario", usuarioRetornado);
		if (usuarioRetornado == null) {
			redirect.addFlashAttribute("mensagem", "Usuário não encontrado");
			return "redirect:/usuario";
		}

		session.setAttribute("usuario", usuarioRetornado);
		return "usuarioLogado";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("usuario");
		return "usuario";
	}
}

package br.com.alura.owasp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.alura.owasp.dao.DepoimentoDao;
import br.com.alura.owasp.model.Depoimento;
import br.com.alura.owasp.validator.DepoimentoValidator;

@Controller
@Transactional
public class DepoimentoController {

	private final DepoimentoDao dao;
	private final DepoimentoValidator validator;

	@Autowired
	public DepoimentoController(DepoimentoDao dao, DepoimentoValidator validator) {
		this.dao = dao;
		this.validator = validator;
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@RequestMapping("/depoimento")
	public String depoimento(Model model) {
		chamaPostsDoBanco(model);
		return "depoimento";
	}

	@RequestMapping(value = "/enviaMensagem", method = RequestMethod.POST)
	public String enviaMensagem(
			@Valid @ModelAttribute(value = "depoimentos") Depoimento depoimento,
			BindingResult result,
			RedirectAttributes redirect, Model model) {
		
		chamaPostsDoBanco(model);
		
		if (result.hasErrors()) {
			return "depoimento";
		}

		dao.salvaDepoimento(depoimento);
		return "redirect:/depoimento";
	}

	private void chamaPostsDoBanco(Model model) {
		Depoimento depoimento = new Depoimento();
		model.addAttribute(depoimento);
		List<Depoimento> mensagens = dao.buscaMensagens();
		model.addAttribute("lista", mensagens);
	}

}

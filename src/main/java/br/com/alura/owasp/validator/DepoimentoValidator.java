package br.com.alura.owasp.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.alura.owasp.model.Depoimento;

@Component
public class DepoimentoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Depoimento.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Depoimento depoimento = (Depoimento) target;
		String titulo = depoimento.getTitulo();
		String mensagem = depoimento.getMensagem();
		
		if (hasPossibleXSS(titulo)) {
			errors.rejectValue("titulo", "errors.titulo");
		}
		
		if (hasPossibleXSS(mensagem)) {
			errors.rejectValue("mensagem", "errors.mensagem");
		}
	}
	
	private boolean hasPossibleXSS(String value) {
		return value.contains("<") || value.contains(">");
	}
}

package br.com.alura.owasp.google;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.alura.owasp.google.response.GoogleRecaptchaResponse;
import retrofit2.Call;

@Component
public class GoogleRecaptchaWebClient {

	private static final String SECRET = "6Lfc_OEUAAAAAF1vAGfKdq-q8iVy1-IIVeiMjeL3";
	private final GoogleService service;
	
	@Autowired
	public GoogleRecaptchaWebClient(GoogleService service) {
		this.service = service;
	}
	
	public boolean verifica(String recaptcha) throws IOException {
		Call<GoogleRecaptchaResponse> token = service.enviaToken(SECRET, recaptcha);
		return token.execute().body().isSuccess();
	}
}

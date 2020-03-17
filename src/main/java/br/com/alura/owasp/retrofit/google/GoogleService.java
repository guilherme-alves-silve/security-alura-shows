package br.com.alura.owasp.retrofit.google;

import br.com.alura.owasp.retrofit.google.response.GoogleRecaptchaResponse;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GoogleService {

	@POST("siteverify")
	Call<GoogleRecaptchaResponse> enviaToken(@Query("secret") String secret, @Query("response") String response);
}

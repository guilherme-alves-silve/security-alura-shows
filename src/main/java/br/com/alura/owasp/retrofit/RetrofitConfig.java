package br.com.alura.owasp.retrofit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.alura.owasp.retrofit.google.GoogleService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitConfig {

	private static final String BASE_URL = "https://www.google.com/recaptcha/api/";
	private final Retrofit retrofit;
	
	public RetrofitConfig() {
		HttpLoggingInterceptor bodyInterceptor = new HttpLoggingInterceptor();
		bodyInterceptor.setLevel(Level.BODY);
		retrofit = new Retrofit.Builder()
								.baseUrl(BASE_URL)
								.client(new OkHttpClient.Builder()
										.addInterceptor(bodyInterceptor)
										.build())
								.addConverterFactory(GsonConverterFactory.create())
								.build();
	}
	
	@Bean
	public GoogleService getGoogleService() {
		return retrofit.create(GoogleService.class);
	}
}

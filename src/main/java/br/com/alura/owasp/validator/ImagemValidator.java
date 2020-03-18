package br.com.alura.owasp.validator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImagemValidator {

	private static final Logger LOG = Logger.getLogger(ImagemValidator.class.getSimpleName());
	
	public boolean ehValido(final MultipartFile file) {
		try {
			final ByteArrayInputStream byteImagem = new ByteArrayInputStream(file.getBytes());
			final String mime = URLConnection.guessContentTypeFromStream(byteImagem);
			return mime != null;
		} catch (IOException e) {
			LOG.severe(e.getMessage());
		}
		
		return false;
	}

}

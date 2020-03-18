package br.com.alura.owasp.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.alura.owasp.model.Usuario;

@Component
public class ImagemUtil {

	public void trataImagem(MultipartFile imagem, Usuario usuario, HttpServletRequest request)
			throws IllegalStateException, IOException {
		usuario.setNomeImagem(imagem.getOriginalFilename());
		File arquivo = new File(request.getServletContext().getRealPath("/image"), usuario.getNomeImagem());
		imagem.transferTo(arquivo);
	}
}

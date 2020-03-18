package br.com.alura.owasp.util;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

import br.com.alura.owasp.model.Usuario;

@Component
public class VerificadorHash {

	public boolean validaSenhaDoUsuarioComOHAshDoBanco(final Usuario usuario,
			final Usuario usuarioRetornado) {
		if (null == usuarioRetornado) {
			return false;
		}
		
		return BCrypt.checkpw(usuario.getSenha(), usuarioRetornado.getSenha());
	}

	public void transformaSenhaDoUsuarioEmHash(final Usuario usuario) {
		String salt = BCrypt.gensalt();
		String senhaHash = BCrypt.hashpw(usuario.getSenha(), salt);
		usuario.setSenha(senhaHash);
	}
}

package br.com.alura.owasp.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import br.com.alura.owasp.model.Usuario;

@Repository
public class UsuarioDaoImpl implements UsuarioDao {

	@PersistenceContext
	private EntityManager manager;

	public void salva(Usuario usuario) {
		transformaSenhaDoUsuarioEmHash(usuario);
		manager.persist(usuario);
	}

	public Usuario procuraUsuario(Usuario usuario) {
		TypedQuery<Usuario> query = manager.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", 
				Usuario.class);
		query.setParameter("email", usuario.getEmail());
		Usuario usuarioRetornado = query.getResultList()
				.stream()
				.findFirst()
				.orElse(null);
		
		if (validaSenhaDoUsuario(usuario, usuarioRetornado)) {
			return usuarioRetornado;
		}
		
		return null;
	}
	
	private void transformaSenhaDoUsuarioEmHash(Usuario usuario) {
		String salt = BCrypt.gensalt();
		String senhaHash = BCrypt.hashpw(usuario.getSenha(), salt);
		usuario.setSenha(senhaHash);
	}
	
	private boolean validaSenhaDoUsuario(Usuario usuario, Usuario usuarioRetornado) {
		if (null == usuarioRetornado) {
			return false;
		}
		
		return BCrypt.checkpw(usuario.getSenha(), usuarioRetornado.getSenha());
	}
}

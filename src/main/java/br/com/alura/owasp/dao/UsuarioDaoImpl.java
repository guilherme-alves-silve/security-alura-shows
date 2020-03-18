package br.com.alura.owasp.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.alura.owasp.model.Usuario;
import br.com.alura.owasp.util.VerificadorHash;

@Repository
public class UsuarioDaoImpl implements UsuarioDao {

	@PersistenceContext
	private EntityManager manager;
	private final VerificadorHash verificadorHash;
	
	@Autowired
	public UsuarioDaoImpl(VerificadorHash verificadorHash) {
		this.verificadorHash = verificadorHash;
	}

	public void salva(Usuario usuario) {
		verificadorHash.transformaSenhaDoUsuarioEmHash(usuario);
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
		
		if (verificadorHash.validaSenhaDoUsuarioComOHAshDoBanco(usuario, usuarioRetornado)) {
			return usuarioRetornado;
		}
		
		return null;
	}
}

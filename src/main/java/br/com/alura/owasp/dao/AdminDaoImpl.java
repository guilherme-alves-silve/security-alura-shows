package br.com.alura.owasp.dao;

import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.alura.owasp.model.Role;
import br.com.alura.owasp.model.Usuario;
import br.com.alura.owasp.util.VerificadorHash;

@Repository
public class AdminDaoImpl implements AdminDao {
	
	@PersistenceContext
	private EntityManager manager;
	private final VerificadorHash verificadorHash;
	
	@Autowired
	public AdminDaoImpl(VerificadorHash verificadorHash) {
		this.verificadorHash = verificadorHash;
	}
	
	@Override
	public boolean verificaSeUsuarioEhAdmin(Usuario usuario) {
		TypedQuery<Usuario> query = manager.createQuery(
				"SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class);
		query.setParameter("email", usuario.getEmail());
		Usuario usuarioRetornado = query.getResultList()
				.stream()
				.findFirst()
				.orElse(null);

		if (verificadorHash.validaSenhaDoUsuarioComOHAshDoBanco(usuario, usuarioRetornado)) {
			return verificaSeUsuarioTemRoleAdmin(usuarioRetornado);
		}

		return false;
	}

	private boolean verificaSeUsuarioTemRoleAdmin(Usuario usuarioRetornado) {
		return usuarioRetornado.getRoles()
				.stream()
				.map(Role::getName)
				.collect(Collectors.toList())
				.contains("ROLE_ADMIN");
	}
}

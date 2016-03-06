package br.com.oberziner.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.oberziner.entity.AuthUser;

public class AuthUserDAO {

	@PersistenceContext
	EntityManager em;

	public void save(AuthUser User) {
		em.merge(User);
	}

	public void update(AuthUser user) {
		em.merge(user);
	}

	public AuthUser getUser(int UserID) {
		return em.find(AuthUser.class, UserID);
	}

	@SuppressWarnings("unchecked")
	public List<AuthUser> getAllUsers() {
		return em.createQuery("Select t from " + AuthUser.class.getSimpleName() + " t").getResultList();
	}

	public void removeWithId(Integer id) {
		AuthUser toRemove = em.find(AuthUser.class, id);
		em.remove(toRemove);
	}
}

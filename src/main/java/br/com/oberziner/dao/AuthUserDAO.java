package br.com.oberziner.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
		return em.createQuery(
				"Select t from " + AuthUser.class.getSimpleName() + " t")
				.getResultList();
	}

	public void removeWithId(Integer id) {
		AuthUser toRemove = em.find(AuthUser.class, id);
		em.remove(toRemove);
	}

	public AuthUser getUserByUserName(String username) {
		Query query = em.createQuery("Select t from " + AuthUser.class.getSimpleName() + 
				" t where t.name = :username");
		query.setParameter("username", username);

		@SuppressWarnings("unchecked")
		List<AuthUser> result = query.getResultList();
		if (result.size() > 0){
			return result.get(0);
		}
		return null;
	}
}

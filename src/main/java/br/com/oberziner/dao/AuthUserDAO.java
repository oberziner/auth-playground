package br.com.oberziner.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.oberziner.entity.AuthUser;
import br.com.oberziner.jpa.JPAUtil;

public class AuthUserDAO extends AbstractDAO {

	private static final long serialVersionUID = 7870614140424308272L;

	public static void save(AuthUser User) {
		AbstractDAO.save(User);
	}

	public static void update(AuthUser User) {
		AbstractDAO.update(User);
	}

	public static void remove(AuthUser User) {
		AbstractDAO.remove(AuthUser.class, User.getId());
	}

	public static AuthUser getUser(int UserID) {
		EntityManager sessao = JPAUtil.newEntityManager();
		try {
			return sessao.find(AuthUser.class, UserID);
		} catch (Exception e) {
			System.out.println("Erro getUser: " + e.getMessage());
			throw e;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<AuthUser> getAllUsers() {
		EntityManager sessao = JPAUtil.newEntityManager();
		try {
			return sessao.createQuery("Select t from " + AuthUser.class.getSimpleName() + " t").getResultList();

		} catch (Exception e) {
			System.out.println("Erro getAllUsers: " + e.getMessage());
			throw e;
		} finally {
			sessao.close();
		}
	}
}

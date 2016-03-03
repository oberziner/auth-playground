package br.com.oberziner.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	
	private static EntityManagerFactory emf;
	
	public static EntityManagerFactory getEntityManagerFactory() {
		if (emf == null){
			emf = Persistence.createEntityManagerFactory("authPU");
		}
		return emf;
	}
	

	public static EntityManager newEntityManager() {
		return getEntityManagerFactory().createEntityManager();
	}

}

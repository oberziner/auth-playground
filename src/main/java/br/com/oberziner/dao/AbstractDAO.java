package br.com.oberziner.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.oberziner.jpa.JPAUtil;

public abstract class AbstractDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	protected static void save(Object object) {
		EntityTransaction transaction;
		EntityManager sessao = JPAUtil.newEntityManager();
		try {
			transaction = sessao.getTransaction();
			transaction.begin();
			try {
				if (sessao.contains(object))
					sessao.merge(object);
				else
					sessao.persist(object);
				transaction.commit();
			} catch (Exception e) {
				transaction.rollback();
				throw e;
			}
		} finally {
			sessao.close();
		}
	}

	protected static void update(Object object) {
		EntityManager sessao;
		EntityTransaction transacao;
		sessao = JPAUtil.newEntityManager();
		try {
			transacao = sessao.getTransaction();
			transacao.begin();
			try {
				sessao.merge(object);
				transacao.commit();
			} catch (Exception e) {
				transacao.rollback();
				System.out.println("Problema na atualizacao. ERRO: " + e.getMessage());
				throw e;
			}
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("rawtypes")
	protected static void remove(Class clazz, int id) {
		EntityManager sessao;
		EntityTransaction transacao;
		sessao = JPAUtil.newEntityManager();
		try {
			transacao = sessao.getTransaction();
			transacao.begin();
			try {
				sessao.remove(sessao.find(clazz, id));
				transacao.commit();
			} catch (Exception e) {
				transacao.rollback();
				System.out.println("Erro ao excluir: " + e.getMessage());
			}
		} finally {
			sessao.close();
		}
	}

}

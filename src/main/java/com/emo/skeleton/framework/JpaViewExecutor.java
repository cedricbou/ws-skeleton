package com.emo.skeleton.framework;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

@Component
public class JpaViewExecutor {
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public <T> List<T> queryView(final String jpql, T viewObject, final Map<String, String> props) {
		final Query query = em.createQuery(jpql);
		for(final String name : props.keySet()) {
			query.setParameter(name, props.get(name));
		}
		
		return (List<T>)query.getResultList();
	}
}

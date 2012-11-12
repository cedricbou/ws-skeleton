package com.emo.skeleton.framework;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

@Component
public class JpaViewExecutor {
	
	@PersistenceContext
	private EntityManager em;
	
	public Object queryView(final String jpql, final Map<String, Object> props) {
		final Query query = em.createQuery(jpql);
		for(final String name : props.keySet()) {
			query.setParameter(name, props.get(name));
		}
		
		return query.getResultList();
	}
}

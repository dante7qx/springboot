package org.dante.springboot.data.pub.service.impl;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.dante.springboot.data.pub.CustomSpecs;
import org.dante.springboot.data.pub.service.CustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class CustomRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
		implements CustomRepository<T, ID> {

	private final EntityManager entityManager;
	
	public CustomRepositoryImpl(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
		this.entityManager = em;
	}

	@Override
	public Page<T> findByAuto(T domain, Pageable pageable) {
		// TODO Auto-generated method stub
		return findAll(CustomSpecs.searchAuto(entityManager, domain), pageable);
	}

}

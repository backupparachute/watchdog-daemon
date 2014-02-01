package com.kylemiller.watchdogd.model.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.kylemiller.watchdogd.model.Identifiable;


public interface BaseDAO<T extends Identifiable> {

	@Transactional
	public abstract void save(T entity);
	
	public abstract void refresh(T entity);

	@Transactional
	public abstract void delete(T entity);
	
//	@Transactional
//	public void delete(final Integer id);

	@Transactional
	public abstract T update(T entity);
	public void updateAll(List<T> entities);
}
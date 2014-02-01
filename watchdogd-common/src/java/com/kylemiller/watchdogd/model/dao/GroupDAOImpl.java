package com.kylemiller.watchdogd.model.dao;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.kylemiller.watchdogd.model.Group;

@Repository("groupDAO")
public class GroupDAOImpl extends BaseDAOImpl<Group> implements GroupDAO {
	
	public Group findByName(final String name) {
		
		try{
			return (Group) getEntityManager().createNamedQuery("Group.findByName")
					.setParameter("name", StringUtils.lowerCase(name))
					.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("Group find by name: %s failed", name), re);
			throw re;
		}
	}
}

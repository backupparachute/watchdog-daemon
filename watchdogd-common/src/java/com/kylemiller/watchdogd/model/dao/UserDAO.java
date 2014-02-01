package com.kylemiller.watchdogd.model.dao;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.kylemiller.watchdogd.model.User;

public interface UserDAO extends BaseDAO<User>, UserDetailsService {
	public User findByUsername(final String username);
	public User findByToken(final String token);
	public void saveAndEncode(User user);
	public void updateAndEncode(User user);
	public User findById(final Integer userId);
	public void delete(Integer id);
	public List<User> findByGroup(final String group, final Integer accountId);
	public boolean updateLastAccessed(final String username);
}

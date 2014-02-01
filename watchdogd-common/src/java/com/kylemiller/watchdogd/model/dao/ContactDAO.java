package com.kylemiller.watchdogd.model.dao;

import java.util.List;

import com.kylemiller.watchdogd.model.Account;
import com.kylemiller.watchdogd.model.Contact;


public interface ContactDAO extends BaseDAO<Contact> {
	public List<Contact> findAllContacts(final Account account);
	public Contact findById(final Integer id);
	public void delete(Integer id);
	public Integer authorize(final Integer id, final Integer accountId);
}

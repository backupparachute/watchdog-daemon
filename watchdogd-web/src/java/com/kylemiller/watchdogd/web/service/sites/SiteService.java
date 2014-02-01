package com.kylemiller.watchdogd.web.service.sites;

import java.util.Map;

import com.kylemiller.watchdogd.model.Site;

public interface SiteService {

	public void save( Site site, String[] contacts) throws Exception;
	public void addContacts(Site site, String[] contacts);
	public abstract Site create(String type) throws Exception;
	public abstract Map load(Integer id) throws Exception;
	public Map loadContacts(Site site);
	public void delete(Integer id);

}
package com.kylemiller.watchdogd.web.service.sites;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kylemiller.watchdogd.model.Contact;
import com.kylemiller.watchdogd.model.Site;
import com.kylemiller.watchdogd.model.SiteAttribute;
import com.kylemiller.watchdogd.model.SiteAttributeKey;
import com.kylemiller.watchdogd.model.SiteStatus;
import com.kylemiller.watchdogd.model.dao.ContactDAO;
import com.kylemiller.watchdogd.model.dao.SiteDAO;
import com.kylemiller.watchdogd.model.dao.SiteReportDAO;
import com.kylemiller.watchdogd.model.dao.SiteStatusDAO;
import com.kylemiller.watchdogd.web.util.SecurityHelper;

@Service
public class SiteServiceImpl implements SiteService {

	@Resource
	private SiteDAO siteDAO;
	@Resource
	private ContactDAO contactDAO;
	@Resource
	private SiteReportDAO siteReportDAO;
	@Resource
	private SiteStatusDAO siteStatusDAO;
	

	public void save( Site site, String[] contacts) throws Exception {
		
		if (null == site.getId()) {
			getSiteDAO().save(site);
			SiteStatus ss = new SiteStatus(site);
			getSiteStatusDAO().save(ss);
		} 
		addContacts(site, contacts);
		getSiteDAO().update(site);
	}
	
	@Transactional
	public void delete(Integer id) {
		getSiteReportDAO().deleteBySite(id);
		getSiteStatusDAO().deleteBySite(id);
		getSiteDAO().delete(id);
	}
	
	public void addContacts(Site site, String[] contacts) {
		
		site.getContacts().clear();
		
		if (null == contacts || contacts.length == 0) return;
		
		List<Contact> list = getContactDAO().findAllContacts(site.getAccount());
			
		for (String id : contacts) {
			if (StringUtils.isNumeric(id)) {
				Contact contact = (Contact) CollectionUtils.find(list, new BeanPropertyValueEqualsPredicate("id", new Integer(id)));
				if (null != contact) site.addContact(contact);
			}
		}
		
	}

	private Map findSelectedContacts(Set<Contact> sel, List<Contact> contacts) {
		Map map = new HashMap();
		for(Contact c : contacts) {
			Contact con = (Contact) CollectionUtils.find(sel, new BeanPropertyValueEqualsPredicate("id", c.getId()));
			if (null != con) map.put(con.getId(), true);
		}
		return map;
	}
	
	
	/* (non-Javadoc)
	 * @see com.kylemiller.watchdogd.service.sites.SiteService#load()
	 */
	public Site create(String type) throws Exception {
		
		Site site = new Site();
		site.setAccount(SecurityHelper.getLoggedInUser().getAccount());
		site.setType(type);
		
		//TODO: (kyle) refactor into a sitefactory
		if (StringUtils.equalsIgnoreCase("http", type)) {
			SiteAttribute attr = new SiteAttribute();
			attr.setName(SiteAttributeKey.KEYWORD);
			attr.setType(SiteAttributeKey.KEYWORD.getType());
			site.addSiteAttribute(attr);
		} else if (StringUtils.equalsIgnoreCase("ssh", type)) {
			SiteAttribute attr = new SiteAttribute();
			attr.setName(SiteAttributeKey.HOST_KEY);
			attr.setType(SiteAttributeKey.HOST_KEY.getType());
			site.addSiteAttribute(attr);
		
			attr = new SiteAttribute();
			attr.setName(SiteAttributeKey.USERNAME);
			attr.setType(SiteAttributeKey.USERNAME.getType());
			site.addSiteAttribute(attr);
			
			attr = new SiteAttribute();
			attr.setName(SiteAttributeKey.PASSWORD);
			attr.setType(SiteAttributeKey.PASSWORD.getType());
			site.addSiteAttribute(attr);
		} else if (StringUtils.equalsIgnoreCase("ftp", type)) {
			SiteAttribute attr = new SiteAttribute();
			attr.setName(SiteAttributeKey.FTP_USERNAME);
			attr.setType(SiteAttributeKey.FTP_USERNAME.getType());
			site.addSiteAttribute(attr);
			
			attr = new SiteAttribute();
			attr.setName(SiteAttributeKey.FTP_PASSWORD);
			attr.setType(SiteAttributeKey.FTP_PASSWORD.getType());
			site.addSiteAttribute(attr);
		} else if (StringUtils.equalsIgnoreCase("dns", type)) {
			SiteAttribute attr = new SiteAttribute();
			attr.setName(SiteAttributeKey.IP_ADDRESS);
			attr.setType(SiteAttributeKey.IP_ADDRESS.getType());
			site.addSiteAttribute(attr);
		}
		
		//return loadContacts(site);
		return site;
	}
	
	/* (non-Javadoc)
	 * @see com.kylemiller.watchdogd.service.sites.SiteService#load(java.lang.String)
	 */
	public Map load(Integer id) throws Exception {
		Site site = getSiteDAO().findById(id);
		
		return loadContacts(site);
	}

	public Map loadContacts(Site site) {
		Map map = new HashMap();
		
		List<Contact> contacts = getContactDAO().findAllContacts(site.getAccount());
		
		map.put("selectedContacts", findSelectedContacts(site.getContacts(), contacts));
		
		map.put("contacts", contacts);
		
		map.put("command", site);
		
		return map;
	}
	
	public SiteDAO getSiteDAO() {
		return siteDAO;
	}
	
	public void setSiteDAO(SiteDAO siteDAO) {
		this.siteDAO = siteDAO;
	}

	public ContactDAO getContactDAO() {
		return contactDAO;
	}

	public void setContactDAO(ContactDAO contactDAO) {
		this.contactDAO = contactDAO;
	}

	public SiteReportDAO getSiteReportDAO() {
		return siteReportDAO;
	}

	public void setSiteReportDAO(SiteReportDAO siteReportDAO) {
		this.siteReportDAO = siteReportDAO;
	}

	public SiteStatusDAO getSiteStatusDAO() {
		return siteStatusDAO;
	}

	public void setSiteStatusDAO(SiteStatusDAO siteStatusDAO) {
		this.siteStatusDAO = siteStatusDAO;
	}
}

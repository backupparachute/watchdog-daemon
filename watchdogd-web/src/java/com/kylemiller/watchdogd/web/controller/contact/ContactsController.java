package com.kylemiller.watchdogd.web.controller.contact;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kylemiller.watchdogd.model.Contact;
import com.kylemiller.watchdogd.model.dao.ContactDAO;
import com.kylemiller.watchdogd.web.util.SecurityHelper;


@Controller
public class ContactsController {

	@Resource
	private ContactDAO contactDAO;
	
	@RequestMapping(value="/contacts")
	public ModelAndView view() throws Exception {
		
		List<Contact> contacts = getContactDAO().findAllContacts(SecurityHelper.getLoggedInUser().getAccount());
		
		Map map = new HashMap();
		map.put("contacts", contacts);
		
		return new ModelAndView("contact/contacts",map);
	}
	
	public ContactDAO getContactDAO() {
		return contactDAO;
	}

	public void setContactDAO(ContactDAO contactDAO) {
		this.contactDAO = contactDAO;
	}

}

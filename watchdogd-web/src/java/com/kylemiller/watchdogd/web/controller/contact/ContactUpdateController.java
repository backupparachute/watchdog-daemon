package com.kylemiller.watchdogd.web.controller.contact;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kylemiller.watchdogd.model.Contact;
import com.kylemiller.watchdogd.model.dao.ContactDAO;

@Controller
@RequestMapping(value="/contact/{id}")
public class ContactUpdateController {

	@Resource
	private ContactDAO contactDAO;
	
	@RequestMapping( method=RequestMethod.POST)
	public ModelAndView save(@ModelAttribute Contact command) throws Exception {
		
		getContactDAO().update(command);
		
		return new ModelAndView("redirect:/contacts");
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView view(@ModelAttribute Contact command) throws Exception {
		
		ModelAndView model = new ModelAndView(String.format("contact/contact-%s", command.getType().toString()));
		
		//model.addObject("command", createModel(id));
		model.addObject("command", command);
		
		return model;
	}
	
	
	@ModelAttribute
	public Contact createModel(@PathVariable("id") String id) {
		
		return getContactDAO().findById(new Integer(id));
	}
	

	public ContactDAO getContactDAO() {
		return contactDAO;
	}

	public void setContactDAO(ContactDAO contactDAO) {
		this.contactDAO = contactDAO;
	}

}

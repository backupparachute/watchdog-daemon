package com.kylemiller.watchdogd.web.controller.contact;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kylemiller.watchdogd.model.dao.ContactDAO;


@Controller
@RequestMapping(value="/contact/{id}/delete")
public class ContactDeleteController {
	
	private final Logger log = LoggerFactory.getLogger(ContactDeleteController.class);
	
	@Resource
	ContactDAO contactDAO;
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView delete(@PathVariable("id") Integer id, @RequestParam(value="_delete",required=false) String delete) throws Exception {
		
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/contacts");
		
		if (StringUtils.isNotBlank(delete)) getContactDAO().delete(id);
		
		return mv;
	}
		
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView view(@PathVariable("id") Integer id) throws Exception {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("contact/contact-delete");
		
		mv.addObject("contact", getContactDAO().findById(id));
		
		return mv;
	}
	public ContactDAO getContactDAO() {
		return contactDAO;
	}

	public void setContactDAO(ContactDAO contactDAO) {
		this.contactDAO = contactDAO;
	}
	
}

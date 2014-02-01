package com.kylemiller.watchdogd.web.controller.contact;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kylemiller.watchdogd.model.Contact;
import com.kylemiller.watchdogd.model.dao.ContactDAO;
import com.kylemiller.watchdogd.web.util.SecurityHelper;
import com.kylemiller.watchdogd.web.util.UiMessageHelper;
import com.kylemiller.watchdogd.web.validators.ContactValidator;

@Controller
@RequestMapping(value="/contact/{type}/new")
public class ContactCreateController {

	@Resource
	private ContactDAO contactDAO;
	@Resource
	private UiMessageHelper uiMessageHelper;
	
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    	binder.setValidator(new ContactValidator());
    }
	
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView save(@Valid Contact command, BindingResult results, HttpServletRequest request, @PathVariable("type") String type) throws Exception {
		
		if (results.hasErrors()) {
			getUiMessageHelper().errors(request, results.getAllErrors());
			return new ModelAndView(String.format("redirect:/contact/%s/new", type));
		}
		
		command.setType(type);
		command.setAccount(SecurityHelper.getLoggedInUser().getAccount());	
		command.setEnabled(true);
		
		getContactDAO().update(command);
		
		return new ModelAndView("redirect:/contacts");
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView view(@PathVariable("type") String type) throws Exception {
		
		Contact contact = new Contact();

		
		ModelAndView model = new ModelAndView(String.format("contact/contact-%s", type));
		
		model.addObject("command", contact);
		
		return model;
	}
	

	public ContactDAO getContactDAO() {
		return contactDAO;
	}

	public void setContactDAO(ContactDAO contactDAO) {
		this.contactDAO = contactDAO;
	}


	public UiMessageHelper getUiMessageHelper() {
		return uiMessageHelper;
	}


	public void setUiMessageHelper(UiMessageHelper uiMessageHelper) {
		this.uiMessageHelper = uiMessageHelper;
	}
}

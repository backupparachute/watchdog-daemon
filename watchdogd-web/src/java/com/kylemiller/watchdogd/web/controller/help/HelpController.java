package com.kylemiller.watchdogd.web.controller.help;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kylemiller.watchdogd.model.dao.HelpDAO;
import com.kylemiller.watchdogd.web.util.UiMessageHelper;

@Controller
@RequestMapping(value="/help/{name}")
public class HelpController {

	@Resource
	private UiMessageHelper uiMessageHelper;
	@Resource
	private HelpDAO helpDAO;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView view(@PathVariable("name") String name, HttpServletRequest request) throws Exception {
		
//		Help help = getHelpDAO().findByUrl(name);
//		
//		if (null == help) {
//			getUiMessageHelper().warning(request, "error.help.not.found", name);
//			return new ModelAndView("redirect:/help");
//		}
		
		ModelAndView model = new ModelAndView("support/help");
		model.addObject("help", getHelpDAO().findByUrl(name));
		model.addObject("_name", name);
		
		return model;
	}
	
	public HelpDAO getHelpDAO() {
		return helpDAO;
	}

	public void setHelpDAO(HelpDAO helpDAO) {
		this.helpDAO = helpDAO;
	}

	public UiMessageHelper getUiMessageHelper() {
		return uiMessageHelper;
	}

	public void setUiMessageHelper(UiMessageHelper uiMessageHelper) {
		this.uiMessageHelper = uiMessageHelper;
	}

}

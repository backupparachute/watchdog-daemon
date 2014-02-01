package com.kylemiller.watchdogd.web.controller.site;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kylemiller.watchdogd.model.Site;
import com.kylemiller.watchdogd.model.dao.SiteDAO;
import com.kylemiller.watchdogd.web.service.sites.SiteService;
import com.kylemiller.watchdogd.web.util.UiMessageHelper;
import com.kylemiller.watchdogd.web.validators.SiteValidator;


@Controller
public class SiteUpdateController {
	
	@Resource
	private SiteService siteService;
	@Resource
	private SiteDAO siteDAO;
	@Resource
	private UiMessageHelper uiMessageHelper;
	
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    	binder.setValidator(new SiteValidator());
    }
	
	@RequestMapping(value="/site/{id}/edit",method=RequestMethod.POST)
	protected ModelAndView update(@Valid @ModelAttribute("command") Site command, BindingResult bind, @RequestParam(value="_contact",required=false) String[] contacts, HttpServletRequest request, @PathVariable("id") Integer id) throws Exception {
		
		if (bind.hasErrors()) {
			getUiMessageHelper().errors(request, bind.getAllErrors());
			return new ModelAndView(String.format("redirect:/site/%s/edit", id));
		}
		
		getSiteService().save(command, contacts);
		
		return new ModelAndView("redirect:/home");
	}	
	
	@RequestMapping(value="/site/{id}/edit",method=RequestMethod.GET)
	public ModelAndView view(@PathVariable("id") Integer id) throws Exception {
		
		Map map = getSiteService().load(id);
		
		ModelAndView mv = new ModelAndView();
		
		Site site = (Site) map.get("command");
		mv.setViewName(String.format("site/site-%s", site.getType()));
		mv.addAllObjects(map);
		
		return mv;
	}
	
	@ModelAttribute("command")
	public Site createModel(@PathVariable("id") Integer id) {
		
		 Site site = getSiteDAO().findById(id);
		return site;
	}

	public SiteService getSiteService() {
		return siteService;
	}

	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	public SiteDAO getSiteDAO() {
		return siteDAO;
	}

	public void setSiteDAO(SiteDAO siteDAO) {
		this.siteDAO = siteDAO;
	}

	public UiMessageHelper getUiMessageHelper() {
		return uiMessageHelper;
	}

	public void setUiMessageHelper(UiMessageHelper uiMessageHelper) {
		this.uiMessageHelper = uiMessageHelper;
	}
	
}

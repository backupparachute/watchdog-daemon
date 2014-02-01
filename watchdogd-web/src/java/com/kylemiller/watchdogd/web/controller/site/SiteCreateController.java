package com.kylemiller.watchdogd.web.controller.site;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
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
import com.kylemiller.watchdogd.model.SiteAttribute;
import com.kylemiller.watchdogd.web.service.sites.SiteService;
import com.kylemiller.watchdogd.web.util.UiMessageHelper;
import com.kylemiller.watchdogd.web.validators.SiteValidator;


@Controller
public class SiteCreateController {
	
	@Resource
	private SiteService siteService;
	@Resource
	private UiMessageHelper uiMessageHelper;
	
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    	binder.setValidator(new SiteValidator());
    }

	
	@RequestMapping(value="/site/{type}/new",method=RequestMethod.POST)
	protected ModelAndView create(@Valid @ModelAttribute("command") Site command, BindingResult results, @RequestParam(value="_contact",required=false) String[] contacts, @PathVariable("type") String type, HttpServletRequest request) throws Exception {
		
		if (results.hasErrors()) {
			getUiMessageHelper().errors(request, results.getAllErrors());
			return new ModelAndView(String.format("redirect:/site/%s/new", type));
		}
		
//		bindKeyword(command, request);
		
		//command.setAccount(SecurityHelper.getLoggedInUser().getAccount());
		//command.setType(type);
		getSiteService().save(command, contacts);
		
		getUiMessageHelper().success(request, "success.site.created");
		
		return new ModelAndView("redirect:/home");
	}	
	
//	private void bindKeyword(Site command, HttpServletRequest request) {
//		String b = request.getParameter("_keyword_enabled");
//		
//		if (StringUtils.isNotBlank(b)) {
//			
//			boolean enabled = Boolean.parseBoolean(b);
//			String value = request.getParameter("_keyword");
//			
//			SiteAttribute param = command.findParam("keyword");
//			if (null != param) {
//				param.setEnabled(enabled);
//				param.setValue(value);
//			} else if (null == param && enabled) {
//				param = new SiteAttribute();
//				command.addSiteParam(param);
//				param.setValue(value);
//				param.setName("keyword");
//				param.setType("regex");
//			}
//		}
//	}


	@RequestMapping(value="/site/{type}/new",method=RequestMethod.GET)
	public ModelAndView view(@ModelAttribute("command") Site command, @PathVariable("type") String type) throws Exception {
		
		Map map = getSiteService().loadContacts(command);
		
		ModelAndView mv = new ModelAndView(determineView(type), map);
		//mv.setViewName(determineView(type));
		//mv.addAllObjects(map);
		
		return mv;
	}
	
	@ModelAttribute("command")
	public Site createModel(@PathVariable("type") String type) throws Exception {
		return getSiteService().create(type);
	}

	protected String determineView(String type) {
		return String.format("site/site-%s", type);
	}

	public SiteService getSiteService() {
		return siteService;
	}

	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	public UiMessageHelper getUiMessageHelper() {
		return uiMessageHelper;
	}

	public void setUiMessageHelper(UiMessageHelper uiMessageHelper) {
		this.uiMessageHelper = uiMessageHelper;
	}
	
}

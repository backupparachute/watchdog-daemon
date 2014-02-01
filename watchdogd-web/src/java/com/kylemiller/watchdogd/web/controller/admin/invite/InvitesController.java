package com.kylemiller.watchdogd.web.controller.admin.invite;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kylemiller.watchdogd.model.Invite;
import com.kylemiller.watchdogd.model.dao.InviteDAO;

@Controller 
public class InvitesController {

	//@Autowired
	@Resource
	private InviteDAO inviteDAO;
	
	@RequestMapping(value="/admin/invites")
	public ModelAndView handle() {
		
		List<Invite> invites = getInviteDAO().findAll();
		
		Map map = new HashMap();
		map.put("invites", invites);
		
		return new ModelAndView("admin/support/invites", map);
	}
	public InviteDAO getInviteDAO() {
		return inviteDAO;
	}
	public void setInviteDAO(InviteDAO inviteDAO) {
		this.inviteDAO = inviteDAO;
	}
}

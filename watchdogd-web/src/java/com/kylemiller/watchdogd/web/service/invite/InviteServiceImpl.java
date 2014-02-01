package com.kylemiller.watchdogd.web.service.invite;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kylemiller.watchdogd.model.User;
import com.kylemiller.watchdogd.model.UserAttribute;
import com.kylemiller.watchdogd.model.dao.InviteDAO;
import com.kylemiller.watchdogd.model.dao.UserDAO;
import com.kylemiller.watchdogd.web.util.SecurityHelper;

//@Service("inviteService")
@Deprecated
public class InviteServiceImpl implements InviteService {
	
	//@Resource
	private UserDAO userDAO;
	//@Resource
	private InviteDAO inviteDAO;
	
	
	public boolean addInvitesRemaining(Map map) {
		
		User user = SecurityHelper.getLoggedInUser();
		return addInvitesRemaining(map, user.getId());
	}
	
	@Override
	public boolean addInvitesRemaining(Map map, Integer userId) {
		
		User user = getUserDAO().findById(userId);
		
		UserAttribute attr = user.findAttribute("invites");
		
		if (null != attr) {
		
			Long count = getInviteDAO().numberOfInvites(user.getAccount().getId());
			
			count = new Integer(attr.getValue()) - count;
			
			if (count <= 0) return false;
			
			map.put("invitesRemaining", count.toString());
			
			return true;
		}
		
		return false;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public InviteDAO getInviteDAO() {
		return inviteDAO;
	}

	public void setInviteDAO(InviteDAO inviteDAO) {
		this.inviteDAO = inviteDAO;
	}

}

package com.kylemiller.watchdogd.model.interceptor;

import java.util.Date;

import javax.persistence.PrePersist;

import com.kylemiller.watchdogd.model.Auditable;

public class AuditInterceptor {
	
	@PrePersist
	public void prePersist(Auditable a) {
		a.setCreatedOn(new Date());
	}
	
}

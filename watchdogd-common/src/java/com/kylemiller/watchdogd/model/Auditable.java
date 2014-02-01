package com.kylemiller.watchdogd.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.kylemiller.watchdogd.model.interceptor.AuditInterceptor;

@MappedSuperclass
@EntityListeners(AuditInterceptor.class)
public abstract class Auditable implements Serializable {
	private Date createdOn;
	
	@OrderBy
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_ON")
	public Date getCreatedOn() {
		return createdOn;
	}
	
	public void setCreatedOn(Date date) {
		this.createdOn = date;
	}
}

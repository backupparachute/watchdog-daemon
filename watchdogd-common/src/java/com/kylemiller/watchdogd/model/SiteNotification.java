package com.kylemiller.watchdogd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * User entity.
 * 
 */
@Entity
@Table(name = "SITE_NOTIFICATIONS")
@NamedQueries({
	@NamedQuery(name="SiteNotification.findMostRecent",
			query="SELECT sn " +
					" from SiteNotification sn " +
					" join fetch sn.siteReport r " +
					" join fetch r.site " +
					" where " +
					" sn.siteReport.site.id = :id " +
					" order by sn.createdOn DESC " +
					" ")	
	,@NamedQuery(name="SiteNotification.findAllForSite",
			query="SELECT sn " +
			" from SiteNotification sn " +
			//" join fetch sn.contact " +
			//" join fetch r.site " +
			" where " +
			" sn.site.id = :id " +
			" AND sn.contentReady = true " +
			" AND sn.createdOn > :date " +
			" order by sn.createdOn DESC " +
	" ")	
	,@NamedQuery(name="SiteNotification.findUnsent",
			query="select sn " +
				" from SiteNotification sn " +
				" join fetch sn.siteReport r " +
				" join fetch r.site " +
				//" join fetch sn.contact " +
				" where " +
				" (sn.success is null OR sn.success = false) " +
				" AND (sn.attempts is null OR sn.attempts < 3) " +
				" order by sn.createdOn" +
				"  ")
})
public class SiteNotification extends Auditable implements java.io.Serializable, Identifiable {
	// Fields
	private Integer id;
	private String type;
	private SiteReport siteReport;
	private String name;
	private String contact;
	private Site site;
	private String json;
	private String subject;
	private String body;
	private boolean contentReady;
	private Integer errorCode;
	private String errorMessage;
	private boolean success;
	private Integer attempts;
	
	// Constructors
	/** default constructor */
	public SiteNotification() {
	}
	
	// Property accessors
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="TYPE")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SITE_REPORT_ID")
	public SiteReport getSiteReport() {
		return siteReport;
	}

	public void setSiteReport(SiteReport siteReport) {
		this.siteReport = siteReport;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SITE_ID")
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Column(name="ERROR_CODE")
	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	@Column(name="ERROR_MESSAGE")
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Integer getAttempts() {
		return attempts;
	}

	public void setAttempts(Integer attempts) {
		this.attempts = attempts;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Column(name="CONTENT_READY")
	public boolean isContentReady() {
		return contentReady;
	}

	public void setContentReady(boolean contentReady) {
		this.contentReady = contentReady;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
}
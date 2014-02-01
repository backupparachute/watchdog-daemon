package com.kylemiller.watchdogd.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * User entity.
 * 
 */
@Entity
@Table(name = "SITE_ATTRIBUTES")
public class SiteAttribute extends Auditable implements java.io.Serializable, Identifiable {
	
	// Fields
	private Integer id;
	//private String type;
	private String type;
	private SiteAttributeKey name;
	private String value;
	private boolean enabled = true;
	private Site site;
	
	// Constructors
	/** default constructor */
	public SiteAttribute() {
	}
	
	public SiteAttribute(Site site) {
		setSite(site);
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

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "SITE_ID")
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
	
	@Column(name="NAME")
	@Enumerated(EnumType.STRING)
	public SiteAttributeKey getName() {
		return name;
	}

	public void setName(SiteAttributeKey name) {
		this.name = name;
	}

	@Column(name="VALUE")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}

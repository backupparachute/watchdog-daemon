package com.kylemiller.watchdogd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * User entity.
 * 
 */
@Entity
@Table(name = "SERVICES")
@NamedQueries({
	@NamedQuery(name="Services.findByName",
			query="SELECT distinct s " +
					" from Service s " +
					" where " +
					" s.name = :name " +
					" ")
})
public class Service extends Auditable implements java.io.Serializable, Identifiable {

	// Fields
	private Integer id;
	private String params;
	private String type;
	private String query;
	private boolean enabled;
	private String name;
	
	// Constructors
	/** default constructor */
	public Service() {
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

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
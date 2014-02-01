package com.kylemiller.watchdogd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

/**
 * User entity.
 * 
 */
@Entity
@Table(name = "ROLES")
public class Role extends Auditable implements java.io.Serializable, GrantedAuthority, Identifiable {

	// Fields
	private Integer id;
	private String authority;
	
	// Constructors
	/** default constructor */
	public Role() {
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

	@Column(name="AUTHORITY")
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public int compareTo(Object o) {
	    if(o != null && (o instanceof GrantedAuthority))
        {
	    	String rhsRole = ((GrantedAuthority)o).getAuthority();

            if(rhsRole == null) return -1;
            else return getAuthority().toString().compareTo(rhsRole);
            
        } else {
        	return -1;
        }
	}

}
package com.kylemiller.watchdogd.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * User entity.
 * 
 */
@Entity
@Table(name = "GROUPS")
@NamedQueries({
	@NamedQuery(name="Group.findByName",
			query="SELECT g " +
					"from Group g " +
					"join fetch g.roles r " +
					"where g.name = :name")
})
public class Group extends Auditable implements java.io.Serializable, Identifiable {

	// Fields
	private Integer id;
	private String name;
	private Set<Role> roles = new HashSet();
	
	// Constructors
	/** default constructor */
	public Group() {
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

	@Column(name="NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany
	@JoinTable(name="GROUPS_ROLES_XREF",
		joinColumns=@JoinColumn(name="GROUP_ID"),
		inverseJoinColumns=@JoinColumn(name="ROLE_ID"))
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
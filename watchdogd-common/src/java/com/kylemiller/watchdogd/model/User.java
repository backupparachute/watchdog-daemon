package com.kylemiller.watchdogd.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * User entity.
 * 
 */
@Entity
@Table(name = "USERS", uniqueConstraints = @UniqueConstraint(columnNames = "USERNAME"))
@NamedQueries({
	@NamedQuery(name="User.findByUsername",
			query="SELECT u " +
					"from User u " +
					"join fetch u.groups g " +
					"join fetch g.roles r " +
					"join fetch u.account a " +
					"left outer join fetch u.attributes aa " +
					"where u.username = :username")
	,@NamedQuery(name="User.findByToken",
			query="SELECT u " +
					"from User u " +
					"join fetch u.groups g " +
					"join fetch g.roles r " +
					"join fetch u.account a " +
					"left outer join fetch u.attributes aa " +
					"where a.token = :token")
	,@NamedQuery(name="User.findById",
			query="SELECT u " +
			"from User u " +
			"join fetch u.groups g " +
			"join fetch g.roles r " +
			"join fetch u.account a " +
			"left outer join fetch u.attributes aa " +
			"where u.id = :id")
	,@NamedQuery(name="User.findByGroup",
		query="SELECT u " +
		"from User u " +
		"join fetch u.groups g " +
		"join fetch g.roles r " +
		"join fetch u.account a " +
		"left outer join fetch u.attributes aa " +
		"where g.name = :name " +
		"and u.account.id = :id " +
		"group by u.id " +
		"")
	,@NamedQuery(name="User.lastAccessed",
		query="update User u set u.lastAccessed = :date where u.username = :username" +
		"")	
})
public class User extends Auditable implements java.io.Serializable, UserDetails, Identifiable {

	// Fields
	private Integer id;
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private boolean enabled = true;
	private Account account;
	private Set<Group> groups = new HashSet();
	private List<UserAttribute> attributes = new ArrayList();
	private Date lastAccessed;
	
	// Constructors
	/** default constructor */
	public User() {
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
	
	@Column(name = "USERNAME", unique = true)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = StringUtils.lowerCase(StringUtils.trim(username));
	}

	@Column(name = "PASSWORD")
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Transient
	public Collection<GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> roles = new HashSet();
		for (Group group : getGroups()) roles.addAll(group.getRoles());
		
		return roles;
	}
	
	@Column(name="ENABLED")
	public boolean isEnabled() {
		return enabled;
	}

	@Transient
	public boolean isAccountNonExpired() {
		return isEnabled();
	}

	@Transient
	public boolean isAccountNonLocked() {
		return isEnabled();
	}

	@Transient
	public boolean isCredentialsNonExpired() {
		return isEnabled();
	}
	
	@Column(name="LAST_ACCESSED")
	public Date getLastAccessed() {
		return lastAccessed;
	}

	public void setLastAccessed(Date lastAccessed) {
		this.lastAccessed = lastAccessed;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCOUNT_ID", unique = true)
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@ManyToMany
	@JoinTable(name="USERS_GROUPS_XREF",
		joinColumns=@JoinColumn(name="USER_ID"),
		inverseJoinColumns=@JoinColumn(name="GROUP_ID"))
	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public List<UserAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<UserAttribute> attributes) {
		this.attributes = attributes;
	}
	
	public void addAttribute(UserAttribute attr) {
		attr.setUser(this);
		getAttributes().add(attr);
	}
	
	public UserAttribute findAttribute(String name) {
		
		for(UserAttribute attr : getAttributes()) {
			if (StringUtils.equalsIgnoreCase(name,attr.getName())) return attr;
		}
		
		return null;
	}
	
	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		
		json.put("id", getId());
		json.put("username", getUsername());
		
		return json.toJSONString();
	}
}
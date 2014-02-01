package com.kylemiller.watchdogd.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * User entity.
 * 
 */
@Entity
@Table(name = "ACCOUNTS")
@NamedQueries({
@NamedQuery(name="Account.updateEnabled",
		query="update Account a set a.enabled = :enabled where a.token = :token" +
		"")	
})
public class Account extends Auditable implements java.io.Serializable, Identifiable {

	private Integer id;
	private boolean enabled = true;
	private Set<User> users = new HashSet();
	private Subscription subscription;
	private Set<Contact> contacts = new HashSet();
	private String token;
	
	// Constructors
	/** default constructor */
	public Account() {
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
	@Column(name="ENABLED")
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void addUser(User user) {
		user.setAccount(this);
		getUsers().add(user);
	}

	@OneToMany( fetch = FetchType.LAZY, mappedBy = "account")
	//@Sort(comparator = IdentifiableComparator.class, type = SortType.COMPARATOR)
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUBSCRIPTION_ID")
	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	public void addContact(Contact contact) {
		contact.setAccount(this);
		getContacts().add(contact);
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
	public Set<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}
}
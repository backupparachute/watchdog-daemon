package com.kylemiller.watchdogd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "INVITES")
@NamedQueries({
	@NamedQuery(name="Invite.findAll",
			query="SELECT i from Invite i order by i.createdOn" +
	" ")
	,@NamedQuery(name="Invite.findByUuid",
			query="SELECT i from Invite i where i.uuid = :uuid" +
	" ")
	,@NamedQuery(name="Invite.findByEmail",
			query="SELECT i from Invite i where i.email = :email" +
	" ")
	,@NamedQuery(name="Invite.numInvites",
			query="SELECT count(i) from Invite i where i.invitorAccountId = :id" +
	" ")
})
public class Invite extends Auditable implements java.io.Serializable, Identifiable {

	// Fields
	private Integer id;
	private String email;
	private String uuid;
	private Integer inviteeAccountId;
	private Integer invitorAccountId;
	private String subscription;
	private String message;
	
	// Constructors
	/** default constructor */
	public Invite() {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name="INVITEE_ACCOUNT_ID")
	public Integer getInviteeAccountId() {
		return inviteeAccountId;
	}

	public void setInviteeAccountId(Integer inviteeAccountId) {
		this.inviteeAccountId = inviteeAccountId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name="INVITOR_ACCOUNT_ID")
	public Integer getInvitorAccountId() {
		return invitorAccountId;
	}

	public void setInvitorAccountId(Integer invitorAccountId) {
		this.invitorAccountId = invitorAccountId;
	}

	public String getSubscription() {
		return subscription;
	}

	public void setSubscription(String subscription) {
		this.subscription = subscription;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
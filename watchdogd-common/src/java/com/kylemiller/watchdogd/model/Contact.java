package com.kylemiller.watchdogd.model;

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
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.json.simple.JSONObject;

@Entity
@Table(name = "CONTACTS")
//@DiscriminatorColumn(name="TYPE")
//@MappedSuperclass
@NamedQueries({
	@NamedQuery(name="findAllContactsForAccount",
			query=" SELECT distinct c " +
					" from Contact c" +
					" join fetch c.account " +
					" where " +
					" c.account.id = :accountId " +
					" "),
	@NamedQuery(name="findContactById",
			query=" SELECT distinct c " +
					" from Contact c" +
					" join fetch c.account " +
					" where " +
					" c.id = :id " +
					" ")
	,@NamedQuery(name="Contact.authorize",
		query=" SELECT c.id " +
				" from Contact c" +
				" where " +
				" c.account.enabled = true " +
				" AND c.account.id = :accountId" +
				" AND c.id = :contactId " +
				" ")
})
public class Contact extends Auditable implements java.io.Serializable, Identifiable, JsonSerializable {

	// Fields
	private Integer id;
	private Account account;
	private String name;
	private String value;
	private boolean enabled;
	private String type;
	
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
	
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "ACCOUNT_ID", unique = true)
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	@Column(name="NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="VALUE")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	//@Enumerated(EnumType.STRING)
	@Column(name="TYPE")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public boolean equals(Object obj)
	{
		if (obj instanceof Contact == false) return false;
		
		if (this == obj)  return true;
		
		Contact con = (Contact) obj;
		
		return new EqualsBuilder()
					.append(this.id, con.getId())
					.append(this.name, con.getName())
					.append(this.type, con.getType())
					.append(this.value, con.getValue())
					.isEquals();
	}

	@Override
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		
		json.put("id", getId());
		json.put("name", getName());
		json.put("type", getType().toString());
		json.put("value", getValue());
		
		return json;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
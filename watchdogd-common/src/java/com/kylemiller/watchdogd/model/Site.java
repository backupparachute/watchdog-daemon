package com.kylemiller.watchdogd.model;

import java.util.ArrayList;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.json.simple.JSONObject;

@Entity
@Table(name = "SITES")
@NamedQueries({
	@NamedQuery(name="Site.findSitesToMonitor",
			query="SELECT distinct s " +
					" from Site s " +
					" join fetch s.account " +
//					" join fetch s.account.subscription sub " +
					" left outer join fetch s.status " +
					" left outer join fetch s.contacts " +
					" left outer join fetch s.attributes " +
					" where " +
					" s.account.enabled = true " +
					" AND s.enabled = true " +
					" AND s.doNotQueue = false " +
					" AND (s.status is null  OR (s.status.lastConnected < :interval OR s.status.lastConnected is null))" +
					//" AND sub.id = :subId " +
					" AND s.id not in (select sq.site.id from SiteQueue sq) " +
					" order by s.status.lastConnected " +
					" "),
		@NamedQuery(name="Site.findSiteById",
				query="SELECT distinct s " +
				" from Site s " +
				" join fetch s.account " +
				//" join fetch s.account.subscription sub " +
				" left outer join fetch s.contacts " +
				" left outer join fetch s.attributes " +
				" left outer join fetch s.status " +
				" where " +
				" s.id = :id " +
		" "),
	@NamedQuery(name="Site.findAllSitesForAccount",
			query=" SELECT distinct s " +
					" from Site s " +
					" join fetch s.account " +
					" left outer join fetch s.status " +
					" where " +
					" s.account.enabled = true " +
					" AND s.account.id = :accountId " +
					//" AND s.enabled = true " +
					//" AND (s.lastConnected < :interval OR s.lastConnected is null)" +
					//" AND sub.id = :subId " +
					//" order by s.lastConnected " +
					" ")
	,@NamedQuery(name="Site.findDownSites",
			query=" SELECT s " +
			" from Site s " +
			" join fetch s.status " +
			" join fetch s.account a " +
//			" join fetch s.account.subscription sub " +
			" left outer join fetch s.contacts " +
			" left outer join fetch s.attributes p " +
			" where " +
			" s.account.enabled = true " +
			" AND s.enabled = true " +
			//" AND sub.id = :subId " +
			" AND ( " +
			"		(s.status.lastConnected < :date OR s.status.lastConnected = null) " +
			" 		AND s.type = 'rest' " +
		//  " 		OR (p.name = 'threshold' AND s.status.value >= p.value) " +
			" ) " +
	" ")
//TODO: (Kyle) probably remove type specific from query
		,@NamedQuery(name="Site.authorize",
			query=" SELECT s.id " +
			" from Site s " +
			" where " +
			" s.account.enabled = true " +
			" AND s.id = :siteId " +
			" AND s.account.id = :accountId " +
	" ")
})
public class Site extends Auditable implements Identifiable, JsonSerializable {

	// Fields
	private Integer id;
	private String name;
	private boolean enabled = true;
	private boolean publicAccess = false;
	private Account account;
	private String uri;
	private String type;
	private Integer port;
	private List<SiteAttribute> attributes = new ArrayList();
	private Set<Contact> contacts = new HashSet();
	private SiteStatus status;
	private boolean doNotQueue;
	
	// Constructors
	/** default constructor */
	public Site() {
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCOUNT_ID", unique = true)
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

//	@NotEmpty
	@Column(name="NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="PUBLIC")
	public boolean isPublicAccess() {
		return publicAccess;
	}

	public void setPublicAccess(boolean publicAccess) {
		this.publicAccess = publicAccess;
	}

//	@NotEmpty
	@Column(name="URI")
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	@Column(name="TYPE")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name="PORT")
	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
	
	public void addSiteAttribute(SiteAttribute attr) {
		attr.setSite(this);
		getAttributes().add(attr);
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "site")
	//@OrderColumn() //JPA 2
	@OrderBy("id")
	public List<SiteAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<SiteAttribute> attrs) {
		this.attributes = attrs;
	}
	
	@Override
	public String toString() {
		return toJSON().toString();
	}
	
	public SiteAttribute findAttribute(SiteAttributeKey key) {
//		return findAttribute(key.toString());
		for (SiteAttribute attr : getAttributes()) {
			if (key.equals(attr.getName())) return attr;
		}
		
		return null;
	}
	
//	public SiteAttribute findAttribute(String name) {
//		
//		for(SiteAttribute attr : getAttributes()) {
//			if (StringUtils.equalsIgnoreCase(name,attr.getName().toString())) return attr;
//		}
//		
//		return null;
//	}
	
//	public Set<SiteAttribute> findParamsByType(String type) {
//		Set<SiteAttribute> set = new HashSet<SiteAttribute>();
//		
//		CollectionUtils.select(getParams(), new BeanPropertyValueEqualsPredicate("type", type), set);
//		
//		return set;
//	}
	
	public void addContact(Contact contact) {
		getContacts().add(contact);
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade= {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(name="SITES_CONTACTS_XREF",
		joinColumns=@JoinColumn(name="SITE_ID"),
		inverseJoinColumns=@JoinColumn(name="CONTACT_ID"))
	public Set<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.REMOVE)
	@JoinColumn(name = "SITE_STATUS_ID")
	public SiteStatus getStatus() {
		return status;
	}

	public void setStatus(SiteStatus status) {
		this.status = status;
	}
	
	@Override
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("id", getId());
		json.put("name", getName());
		json.put("type", getType());
		json.put("uri", (null == getUri()) ? "" : getUri().toString());
		
		return json;
	}

	@Column(name="DO_NOT_QUEUE")
	public boolean isDoNotQueue() {
		return doNotQueue;
	}

	public void setDoNotQueue(boolean doNotQueue) {
		this.doNotQueue = doNotQueue;
	}
}
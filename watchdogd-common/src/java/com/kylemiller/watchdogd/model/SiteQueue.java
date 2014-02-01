package com.kylemiller.watchdogd.model;

import java.util.Date;

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

@Entity
@Table(name = "SITE_QUEUE")
@NamedQueries({
	@NamedQuery(name="SiteQueue.findAll",
			query="SELECT q " +
					"from SiteQueue q " +
					"join fetch q.site s " +
					" left outer join fetch s.contacts " +
					" left outer join fetch s.attributes " +
					" left outer join fetch s.status " +
					" where " +
					" q.processing is null " +
					" order by q.createdOn " +
	" ")
	,@NamedQuery(name="SiteQueue.findById",
			query="SELECT q " +
			"from SiteQueue q " +
			"join fetch q.site s " +
			" left outer join fetch s.contacts " +
			" left outer join fetch s.attributes " +
			" left outer join fetch s.status " +
			" where " +
			" q.id = :id " +
	" ")
	,@NamedQuery(name="SiteQueue.deleteBySite",
			query="DELETE " +
			"SiteQueue " +
			" where " +
			" site.id = :id " +
	" ")
	,@NamedQuery(name="SiteQueue.updateProcessing",
				query="update SiteQueue set processing = :date where id = :id " +
			"")
})
public class SiteQueue extends Auditable implements java.io.Serializable, Identifiable {

	// Fields
	private Integer id;
	private Site site;
	private Date processing;
	
	// Constructors
	/** default constructor */
	public SiteQueue() {
	}
	
	public SiteQueue(Site site) {
		this.site = site;
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

	public Date getProcessing() {
		return processing;
	}

	public void setProcessing(Date processed) {
		this.processing = processed;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SITE_ID")
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
}
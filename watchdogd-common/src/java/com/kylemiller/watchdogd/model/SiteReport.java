package com.kylemiller.watchdogd.model;

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

import org.json.simple.JSONObject;

@Entity
@Table(name = "SITE_REPORTS")
@NamedQueries({
	@NamedQuery(name="SiteReport.findSiteStatusCountPerDay",
			query="SELECT new map( " +
			" count(*) as count " +
			" , s.type as status " +
			", date_format(s.createdOn,'%m.%d') as monthday " +
			", date(s.createdOn) as date " +
			" ) from SiteReport s " +
			" where " +
			" s.site.id = :siteId " +
			" AND s.createdOn > :date " +
			" group by date(s.createdOn), type " +
			" order by s.createdOn" +
	" "),
	@NamedQuery(name="findSiteReportsByType",
			query="SELECT new map( " +
			" count(*) as count " +
			" , s.type as status " +
			//" , :date as date " +
			" ) from SiteReport s " +
			" where " +
			" s.site.id = :siteId " +
			" AND s.createdOn > :date " +
			" group by type " +
	" "),
	@NamedQuery(name="findSiteResponseTimeByHour",
			query="SELECT new map( " +
			"avg(sr.responseTime) as responseTime" +
			", hour(sr.createdOn) as hour" +
			") from SiteReport sr " +
			" where " +
			" sr.site.id = :id " +
			" AND sr.createdOn > :date " +
			" group by hour(sr.createdOn) " +
			" order by hour(sr.createdOn) " +
	" ")
		,@NamedQuery(name="SiteReport.findSiteValueByHour",
			query="SELECT new map( " +
			"avg(sr.responseTime) as responseTime" +
			", avg(sr.value) as customValue" +
			", hour(sr.createdOn) as hour" +
			") from SiteReport sr " +
			" where " +
			" sr.site.id = :id " +
			" AND sr.createdOn > :date " +
			" AND sr.value is not null " +
			" group by hour(sr.createdOn) " +
			" order by hour(sr.createdOn) " +
	" ")
	,@NamedQuery(name="findSiteReportsForTimePeriod",
			query="SELECT " +
			" sr " +
			" from SiteReport sr " +
			" where " +
			" sr.site.id = :id " +
			" AND sr.createdOn > :date " +
			" order by sr.createdOn DESC" +
	" ")
	,@NamedQuery(name="findSiteReportsForTimePeriodCount",
			query="SELECT " +
			" count(sr) " +
			" from SiteReport sr " +
			" where " +
			" sr.site.id = :id " +
			" AND sr.createdOn > :date " +
			" order by sr.createdOn DESC" +
	" ")
	,@NamedQuery(name="deleteSiteReportsOlderThan",
		query="delete " +
		" from SiteReport sr " +
		" where " +
		" sr.createdOn < :date " +
	" ")
})
public class SiteReport extends Auditable implements java.io.Serializable, Identifiable, JsonSerializable {

	public static final String SUCCESS_TYPE = "UP";
	public static final String FAILURE_TYPE = "DOWN";
	
	// Fields
	private Integer id;
	private String type;
	private String message;
	private Integer statusCode;
	private Site site;
	private Long responseTime;
	private Integer value;
	private String location;
	
	// Constructors
	/** default constructor */
	public SiteReport() {
	}
	
	public SiteReport(Site site) {
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

	@Column(name="MESSAGE")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name="STATUS_CODE")
	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SITE_ID")
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@Column(name="RESPONSE_TIME")
	public Long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Long responseTime) {
		this.responseTime = responseTime;
	}

	@Column(name="LOCATION")
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("id", getId());
		json.put("type", getType());
		json.put("statusCode", getStatusCode());
		json.put("message", getMessage());
		json.put("responseTime", getResponseTime());
		json.put("location", getLocation());
		
		return json;
	}
	
	@Override
	public String toString() {
		return toJSON().toString();
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer customValue) {
		this.value = customValue;
	}
}
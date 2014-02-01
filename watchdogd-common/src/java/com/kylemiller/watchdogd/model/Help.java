package com.kylemiller.watchdogd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.json.simple.JSONObject;

@Entity
@Table(name = "HELP")
//@DiscriminatorColumn(name="TYPE")
//@MappedSuperclass
@NamedQueries({
	@NamedQuery(name="Help.findAll",
			query=" SELECT distinct h " +
					" from Help h " +
					" where h.enabled = true " +
					" order by h.weight desc" +
					" "),
	@NamedQuery(name="Help.findByUrl",
			query=" SELECT distinct h " +
					" from Help h" +
					" where " +
					" h.url = :url " +
					" "),
	@NamedQuery(name="Help.findById",
			query=" SELECT distinct h " +
					" from Help h" +
					" where " +
					" h.id = :id " +
					" ")
})
public class Help extends Auditable implements java.io.Serializable, Identifiable, JsonSerializable {

	// Fields
	private Integer id;
	private String title;
	private String description;
	private String tags;
	private String url;
	private String data;
	private Integer weight = 1000;
	private boolean enabled = true;
	
	public Help() { 
	}
	
	public Help(String name) {
		this.url = name;
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
	
	public boolean equals(Object obj)
	{
		if (obj instanceof Help == false) return false;
		
		if (this == obj)  return true;
		
		Help con = (Help) obj;
		
		return new EqualsBuilder()
					.append(this.id, con.getId())
					.append(this.title, con.getTitle())
					.append(this.url, con.getUrl())
					.append(this.data, con.getData())
					.isEquals();
	}

	@Override
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		
		json.put("id", getId());
		json.put("title", getTitle());
		json.put("url", getUrl());
		json.put("data", getData());
		
		return json;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
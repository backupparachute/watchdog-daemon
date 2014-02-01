package com.kylemiller.watchdogd.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * User entity.
 * 
 */
@Entity
@Table(name = "SUBSCRIPTIONS")
//@NamedQueries({
//	@NamedQuery(name="findSubscriptions",
//			query="SELECT s " +
//					"from Subscription s " +
//					" order by s.weight DESC " +
//					" ")
//	,@NamedQuery(name="Subscription.findByName",
//			query="SELECT s " +
//					"from Subscription s " +
//					" where s.name = :name " +
//					" ")
//})
@Deprecated
public class Subscription extends Auditable implements java.io.Serializable, Identifiable {

	private Integer id;
	private String name;
	private BigDecimal amount;
	private Integer weight;
	private Integer interval;
	
	// Constructors
	/** default constructor */
	public Subscription() {
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

	@Column(name="AMOUNT")
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(name="WEIGHT")
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@Column(name="PROBE_INTERVAL")
	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}

}